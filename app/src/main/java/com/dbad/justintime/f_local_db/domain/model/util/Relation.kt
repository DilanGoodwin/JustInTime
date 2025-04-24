package com.dbad.justintime.f_local_db.domain.model.util

import androidx.annotation.StringRes
import com.dbad.justintime.R

enum class Relation(@StringRes val stringVal: Int) {
    OTHER(R.string.other),
    MOTHER(R.string.mother),
    FATHER(R.string.father),
    SIBLING(R.string.sibling),
    SIGNIFICANT_OTHER(R.string.significationOther),
    CARER(R.string.carer),
    NONE(0)
}

fun Long.toRelation(): Relation {
    return Relation.entries[this.toInt()]
}

fun Relation.toLong(): Long {
    return this.ordinal.toLong()
}