package com.dbad.justintime.f_local_db.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.repository.LocalDatabaseRepository

class UpsertEmergencyContact(private val repo: LocalDatabaseRepository) {
    suspend operator fun invoke(emergencyContact: EmergencyContact) {
        repo.upsertEmergencyContact(emergencyContact = emergencyContact)
    }
}