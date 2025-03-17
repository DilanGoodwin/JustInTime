package com.dbad.justintime

import android.app.Application
import com.dbad.justintime.di.LocalDatabaseModule
import com.dbad.justintime.di.LocalDatabaseModuleImplementation
import com.dbad.justintime.di.LoginRegisterModule
import com.dbad.justintime.di.LoginRegisterModuleImplementation

class App : Application() {
    companion object {
        lateinit var localDatabase: LocalDatabaseModule
        lateinit var loginRegister: LoginRegisterModule
    }

    /**
     * onCreate
     */
    override fun onCreate() {
        super.onCreate()
        localDatabase = LocalDatabaseModuleImplementation(context = this)
        loginRegister = LoginRegisterModuleImplementation(localDatabase = localDatabase.useCases)
    }
}