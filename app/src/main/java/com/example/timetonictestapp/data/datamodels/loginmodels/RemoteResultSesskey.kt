package com.example.timetonictestapp.data.datamodels.loginmodels

data class RemoteResultSesskey(
    val appName: String,
    val createdVNB: String,
    val id: String,
    val req: String,
    val restrictions: Restrictions,
    val sesskey: String,
    val status: String
)