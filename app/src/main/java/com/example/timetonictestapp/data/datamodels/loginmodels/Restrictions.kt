package com.example.timetonictestapp.data.datamodels.loginmodels
data class Restrictions(
    val carnet_code: Any,
    val carnet_owner: Any,
    val hideEvents: Boolean,
    val hideMessages: Boolean,
    val hideTables: Boolean,
    val `internal`: Boolean,
    val readonly: Boolean
)