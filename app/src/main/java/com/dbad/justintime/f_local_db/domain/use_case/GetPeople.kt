package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow

class GetPeople(private val repo: LocalDatabaseRepository) {
    operator fun invoke(): Flow<List<Person>> {
        return repo.getPeople()
    }
}