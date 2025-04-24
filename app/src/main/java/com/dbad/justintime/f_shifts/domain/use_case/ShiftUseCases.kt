package com.dbad.justintime.f_shifts.domain.use_case

data class ShiftUseCases(
    val getUser: GetUser,
    val getEmployee: GetEmployee,
    val getEvents: GetEvents,
    val upsertEvents: UpsertEvent,
    val deleteEvent: RemoveEvent,
    val getPeople: GetPeople,
    val validateDate: ValidateDate = ValidateDate()
)