package com.practiceapp.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.practiceapp.App
import com.practiceapp.R
import com.practiceapp.ui.controllers.MainController
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
