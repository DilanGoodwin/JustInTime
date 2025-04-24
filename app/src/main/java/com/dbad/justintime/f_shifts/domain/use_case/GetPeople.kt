package com.dbad.justintime.f_shifts.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.Person
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository
import kotlinx.coroutines.flow.Flow

class GetPeople(private val repo: ShiftRepository) {
    operator fun invoke(): Flow<List<Person>> {
        return repo.getPeople()
    }
}