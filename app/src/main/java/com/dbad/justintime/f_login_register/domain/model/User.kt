package com.dbad.justintime.f_login_register.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    foreignKeys = [ForeignKey(
        entity = Employee::class,
        parentColumns = ["uid"],
        childColumns = ["employee"]
    )]
)
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val email: String = "",
    val password: String = "",
    val employee: Int = 0
)