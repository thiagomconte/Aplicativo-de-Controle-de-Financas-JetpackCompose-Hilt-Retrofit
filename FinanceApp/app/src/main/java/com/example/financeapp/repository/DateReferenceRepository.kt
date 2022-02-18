package com.example.financeapp.repository

import com.example.financeapp.api.ApiService
import com.example.financeapp.models.DateReference
import com.example.financeapp.models.Finance
import com.example.financeapp.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DateReferenceRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getAllDateReferences(): Flow<ResponseState<List<DateReference>>> {
        return flow {
            try {
                val response = api.getAllDateReferences()
                emit(ResponseState.Success(response.data))
            } catch (e: Exception) {
                emit(ResponseState.Error("Não foi possível completar a operação."))
            }
        }
    }
}
