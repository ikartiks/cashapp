package com.kartik.grevocab.api.utility

enum class APIResponse(val endpointUrl: String) {
    SUCCESS("portfolio.json"),
    FAILURE("portfolio_ malformed.json"),
    EMPTY("portfolio_empty.json")
}
