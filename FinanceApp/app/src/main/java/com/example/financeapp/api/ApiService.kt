package com.example.financeapp.api

import com.example.financeapp.api.responses.DateReferenceResponse
import com.example.financeapp.api.responses.FinanceResponse
import com.example.financeapp.api.responses.GenericResponse
import com.example.financeapp.api.responses.LoginResponse
import com.example.financeapp.models.Finance
import com.example.financeapp.models.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // USER ROUTES
    @POST("users/register")
    suspend fun register(@Body user: User): Response<GenericResponse>

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    // FINANCES ROUTES
    @POST("finances")
    suspend fun createFinance(@Body finance: Finance): GenericResponse

    @GET("finances/{dateReferenceId}")
    suspend fun getFinancesByDateReference(@Path("dateReferenceId") dateReferenceId: String): FinanceResponse

    @GET("finances/filterfinances/{startMonth}/{startYear}/{endMonth}/{endYear}")
    suspend fun getFilteredFinances(
        @Path("startMonth") startMonth: Int,
        @Path("startYear") startYear: Int,
        @Path("endMonth") endMonth: Int,
        @Path("endYear") endYear: Int
    ): FinanceResponse

    // DATEREFERENCES ROUTES
    @GET("datereferences")
    suspend fun getAllDateReferences(): DateReferenceResponse

    @GET("datereferences/filterdatereferences/{startMonth}/{startYear}/{endMonth}/{endYear}")
    suspend fun getFilteredDateReferences(
        @Path("startMonth") startMonth: Int,
        @Path("startYear") startYear: Int,
        @Path("endMonth") endMonth: Int,
        @Path("endYear") endYear: Int
    ): DateReferenceResponse
}
