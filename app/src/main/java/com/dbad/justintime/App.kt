package com.dbad.justintime

import android.app.Application
import com.dbad.justintime.di.LoginRegisterModule
import com.dbad.justintime.di.LoginRegisterModuleImplementation

class App : Application() {
    companion object {
        lateinit var loginRegister: LoginRegisterModule
    }

    /**
     * onCreate
     */
    override fun onCreate() {
        super.onCreate()
        loginRegister = LoginRegisterModuleImplementation(this)
    }
}