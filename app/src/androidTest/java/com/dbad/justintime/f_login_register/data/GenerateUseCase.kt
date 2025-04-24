package com.dbad.justintime.f_login_register.data

import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.f_local_db.domain.model.EmergencyContact
import com.dbad.justintime.f_local_db.domain.model.Employee
import com.dbad.justintime.f_local_db.domain.model.User
import com.dbad.justintime.f_login_register.domain.repository.UserRepository
import com.dbad.justintime.f_login_register.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_login_register.domain.use_case.GetEmployee
import com.dbad.justintime.f_login_register.domain.use_case.GetUser
import com.dbad.justintime.f_login_register.domain.use_case.UpdateLocalDatabase
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_login_register.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_login_register.domain.use_case.UpsertUser
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases

fun generateUseCase(
    users: List<User>,
    employees: List<Employee>,
    emergencyContact: List<EmergencyContact>
): UserUseCases {
    val userRepo: UserRepository = UsersRepositoryTestingImplementation(
        users = users,
        employees = employees,
        emergencyContact = emergencyContact
    )
    val useCases = UserUseCases(
        getUser = GetUser(repository = userRepo),
        upsertUser = UpsertUser(repository = userRepo),
        getEmployee = GetEmployee(repository = userRepo),
        upsertEmployee = UpsertEmployee(repository = userRepo),
        getEmergencyContact = GetEmergencyContact(repository = userRepo),
        upsertEmergencyContact = UpsertEmergencyContact(repository = userRepo),
        updateLocalDatabase = UpdateLocalDatabase(repository = userRepo),
        validateEmail = ValidateEmail(),
        validatePassword = ValidatePassword(),
        validatePhoneNumber = ValidatePhoneNumber(),
        validateDate = ValidateDate()
    )
    return useCases
}