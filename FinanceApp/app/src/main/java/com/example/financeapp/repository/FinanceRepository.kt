package com.example.financeapp.repository

import com.example.financeapp.api.ApiService
import com.example.financeapp.models.Finance
import com.example.financeapp.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FinanceRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getFinanceByDateReference(dateReferenceId: String): Flow<ResponseState<List<Finance>>> {
        return flow {
            try {
                val response = api.getFinancesByDateReference(dateReferenceId)
                emit(ResponseState.Success(response.data))
            } catch (e: Exception) {
                emit(ResponseState.Error("Não foi possível completar a operação."))
            }
        }
    }

    suspend fun newFinance(finance: Finance): Flow<ResponseState<String>> {
        return flow {
//            try {
                val response = api.createFinance(finance)
                emit(ResponseState.Success(response.message))
//            } catch (e: Exception) {
//                emit(ResponseState.Error("Não foi possível completar a operação."))
//            }
        }
    }
}
