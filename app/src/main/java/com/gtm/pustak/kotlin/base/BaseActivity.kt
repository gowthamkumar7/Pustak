package com.gtm.pustak.kotlin.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.gtm.pustak.R
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<B> : AppCompatActivity() {


    var toolbar: Toolbar? = null
    var binding: B? = null
    lateinit var compositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getLayoutResource()
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        compositeDisposable = CompositeDisposable()
    }

    abstract fun getLayoutResource(): B

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

}
