package com.dbad.justintime.f_login_register.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    foreignKeys = [ForeignKey(
        entity = Employee::class,
        parentColumns = ["uid"],
        childColumns = ["employee"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val email: String = "",
    val password: String = "",
    @ColumnInfo(name = "employee", index = true)
    val employee: Int = 0
)