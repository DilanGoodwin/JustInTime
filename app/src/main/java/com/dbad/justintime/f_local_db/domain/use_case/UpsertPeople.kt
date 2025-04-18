package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository

class UpsertPeople(private val repo: LocalDatabaseRepository) {
    suspend operator fun invoke(person: Person) {
        repo.upsertPerson(person = person)
    }
}