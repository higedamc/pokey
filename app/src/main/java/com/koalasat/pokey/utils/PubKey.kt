package com.koalasat.pokey.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

private val HEX_PUBKEY_REGEX = Regex("^[0-9a-fA-F]{64}$")

@OptIn(ExperimentalContracts::class)
fun isValidHexPubKey(value: String?): Boolean {
    contract { returns(true) implies (value != null) }
    return value != null && HEX_PUBKEY_REGEX.matches(value)
}
