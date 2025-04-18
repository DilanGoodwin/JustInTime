package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetEmergencyContact(private val repository: UserRepository) {
    suspend operator fun invoke(emergencyContact: EmergencyContact): Flow<EmergencyContact> {
        return repository.getEmergencyContact(emergencyContact = emergencyContact)
    }
}