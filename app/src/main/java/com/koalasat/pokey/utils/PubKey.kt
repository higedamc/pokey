package com.koalasat.pokey.utils

private val HEX_PUBKEY_REGEX = Regex("^[0-9a-fA-F]{64}$")

fun isValidHexPubKey(value: String?): Boolean = value != null && HEX_PUBKEY_REGEX.matches(value)
