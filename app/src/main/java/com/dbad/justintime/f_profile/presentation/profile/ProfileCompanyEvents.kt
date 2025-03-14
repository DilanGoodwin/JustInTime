package com.dbad.justintime.f_profile.presentation.profile

import com.dbad.justintime.core.domain.model.util.ContractType

interface ProfileCompanyEvents {

    // Company Input Events
    data class SetCompanyName(val companyName: String) : ProfileCompanyEvents
    data class SetManagerName(val managerName: String) : ProfileCompanyEvents
    data class SetContractType(val contractType: ContractType) : ProfileCompanyEvents
    data class SetRole(val role: String) : ProfileCompanyEvents

    // Company Toggle Events
    data object ToggleExpandedArea : ProfileCompanyEvents
    data object ToggleContractTypeDropDown : ProfileCompanyEvents
}