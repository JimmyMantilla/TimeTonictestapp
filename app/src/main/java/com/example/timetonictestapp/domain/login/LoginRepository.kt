package com.example.timetonictestapp.domain.login

import com.example.timetonictestapp.data.datamodels.loginmodels.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(username: String, password: String): Flow<LoginResponse>
}


