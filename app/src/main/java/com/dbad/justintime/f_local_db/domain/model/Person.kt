package com.dbad.justintime.f_local_db.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class Person(
    @PrimaryKey(autoGenerate = false) val employeeUid: String = "",
    val name: String = ""
) {
    override fun toString(): String {
        return name
    }
}