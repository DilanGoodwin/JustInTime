package com.dbad.justintime.f_profile.presentation.profile

import com.dbad.justintime.core.domain.model.util.PreferredContactMethod
import com.dbad.justintime.core.domain.model.util.Relation

interface ProfileEmergencyContactEvents {

    // Emergency Contact Events
    data class SetEmergencyContactName(val name: String) : ProfileEmergencyContactEvents
    data class SetEmergencyContactPrefName(val name: String) : ProfileEmergencyContactEvents
    data class SetEmergencyContactPhone(val phone: String) : ProfileEmergencyContactEvents
    data class SetEmergencyContactEmail(val email: String) : ProfileEmergencyContactEvents
    data class SetEmergencyContactPrefContactMethod(val contactMethod: PreferredContactMethod) :
        ProfileEmergencyContactEvents

    data class SetEmergencyContactRelation(val relation: Relation) : ProfileEmergencyContactEvents

    // Emergency Contact Toggle Events
    data object ToggleExpandableArea : ProfileEmergencyContactEvents
    data object ToggleEmergencyContactPrefContactDropDown : ProfileEmergencyContactEvents
    data object ToggleEmergencyContactRelationDropDown : ProfileEmergencyContactEvents
}