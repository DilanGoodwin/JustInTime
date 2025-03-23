package com.dbad.justintime.f_local_users_db.domain.use_case

import com.dbad.justintime.f_local_users_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_users_db.domain.repository.LocalUsersRepository

class GetEmergencyContact(private val repo: LocalUsersRepository) {
    suspend operator fun invoke(emergencyContact: EmergencyContact): EmergencyContact {
        return repo.getEmergencyContact(emergencyContact = emergencyContact)
    }
}