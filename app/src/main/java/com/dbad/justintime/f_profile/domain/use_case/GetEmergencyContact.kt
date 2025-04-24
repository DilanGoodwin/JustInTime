package com.dbad.justintime.f_profile.domain.use_case

import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_profile.domain.repository.ProfileRepository

class GetEmergencyContact(private val repository: ProfileRepository) {
    suspend operator fun invoke(emergencyContact: EmergencyContact): EmergencyContact {
        return repository.getEmergencyContact(contact = emergencyContact)
    }
}