package com.dbad.justintime

import android.app.Application
import com.dbad.justintime.di.local_database.LocalDatabaseModule
import com.dbad.justintime.di.local_database.LocalDatabaseModuleImplementation
import com.dbad.justintime.di.login_register.LoginRegisterModule
import com.dbad.justintime.di.login_register.LoginRegisterModuleImplementation
import com.dbad.justintime.di.profile.ProfileModule
import com.dbad.justintime.di.profile.ProfileModuleImplementation
import com.dbad.justintime.di.shifts.ShiftsModule
import com.dbad.justintime.di.shifts.ShiftsModuleImplementation

class App : Application() {
    companion object {
        lateinit var localDatabase: LocalDatabaseModule
        lateinit var loginRegister: LoginRegisterModule
        lateinit var shifts: ShiftsModule
        lateinit var profile: ProfileModule
    }

    /**
     * onCreate
     */
    override fun onCreate() {
        super.onCreate()
        localDatabase = LocalDatabaseModuleImplementation(context = this)
        loginRegister = LoginRegisterModuleImplementation(localDatabase = localDatabase.useCases)
        shifts = ShiftsModuleImplementation(localDatabase = localDatabase.useCases)
        profile = ProfileModuleImplementation(localDatabase = localDatabase.useCases)
    }
}