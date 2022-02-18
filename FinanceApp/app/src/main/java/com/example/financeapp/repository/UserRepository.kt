package com.example.financeapp.repository

import com.example.financeapp.api.ApiService
import com.example.financeapp.api.responses.ErrorResponse
import com.example.financeapp.api.responses.LoginResponse
import com.example.financeapp.models.User
import com.example.financeapp.utils.ResponseState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService
) {

    private val gson = Gson()

    suspend fun register(user: User): Flow<ResponseState<String>> {
        return flow {
            val response = api.register(user)
            if (response.isSuccessful) {
                emit(ResponseState.Success(response.body()!!.message))
            } else {
                val errorResponse: ErrorResponse = gson.fromJson(
                    response.errorBody()!!.string(),
                    ErrorResponse::class.java
                )
                emit(ResponseState.Error(errorResponse.error))
            }
        }
    }

    suspend fun login(email: String, password: String): Flow<ResponseState<LoginResponse>> {
        return flow {
            val response = api.login(email, password)
            if (response.isSuccessful) {
                emit(ResponseState.Success(response.body()!!))
            } else {
                val errorResponse: ErrorResponse = gson.fromJson(
                    response.errorBody()!!.string(),
                    ErrorResponse::class.java
                )
                emit(ResponseState.Error(errorResponse.error))
            }
        }
    }
}
