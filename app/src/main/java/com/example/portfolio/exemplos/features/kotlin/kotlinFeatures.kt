package com.example.portfolio.exemplos.features.kotlin

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.ExperimentalExtendedContracts
import kotlin.contracts.contract

enum class UserRole {
    ADMIN,
    MANAGER,
    USER
}

fun getSecurityClearanceForUserRole(userRole: UserRole): Int {
    if (userRole == UserRole.USER) return 99
    return when (userRole) {
        UserRole.ADMIN -> 10
        UserRole.MANAGER -> 1
    }
}


fun getDisplayName(string: String) : String {
    return "Display: $string"
}

fun getDisplayNameOrDefault(string: String?) : String {
    return getDisplayName(string ?: "Default")
}



//@OptIn(ExperimentalContracts::class, ExperimentalExtendedContracts::class)
//fun decode(str: String?) : String? {
//    contract {
//        returnsNotNull() implies (str != null)
//    }
//    if (str == null) return null
//    return java.net.URLDecoder.decode(str, "UTF-8")
//}
//
//fun usageDecode() {
//    val decode =decode("some text")
//    println(decode.length)
//}