package com.example.goodspacesample.data.models

data class VerifyRequest(val number: String, val otp: String, val inviteId : String? = null, val utmTracking : String? = null)
