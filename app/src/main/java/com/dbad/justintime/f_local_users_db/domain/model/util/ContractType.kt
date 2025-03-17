package com.dbad.justintime.f_local_users_db.domain.model.util

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class ContractType(@StringRes val stringVal: Int) {
    OTHER(R.string.other),
    PART_TIME(R.string.part_time),
    SALARY(R.string.salary),
    CONTRACTOR(R.string.contractor)
}

fun Long.toContractType(): ContractType {
    return ContractType.entries[this.toInt()]
}

fun ContractType.toLong(): Long {
    return this.ordinal.toLong()
}