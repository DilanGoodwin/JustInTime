package com.dbad.justintime.f_login_register.domain.use_case

import com.dbad.justintime.f_login_register.domain.model.EmergencyContact
import com.dbad.justintime.f_login_register.domain.repository.UserRepository

class UpsertEmergencyContact(private val repository: UserRepository) {
    suspend operator fun invoke(emergencyContact: EmergencyContact) {
        repository.upsertEmergencyContact(contact = emergencyContact)
    }
}