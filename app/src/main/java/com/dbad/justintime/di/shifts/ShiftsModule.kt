package com.dbad.justintime.di.shifts

import com.dbad.justintime.f_shifts.data.data_source.EventDatabase
import com.dbad.justintime.f_shifts.domain.repository.ShiftRepository
import com.dbad.justintime.f_shifts.domain.use_case.ShiftUseCases

interface ShiftsModule {
    val eventsDatabase: EventDatabase
    val shiftsRepository: ShiftRepository
    val useCases: ShiftUseCases
}