package com.grocery.groceryapp.data.network

import com.grocery.groceryapp.Utils.Constants
import com.grocery.groceryapp.data.modal.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(Constants.ApiEnd_register)
    suspend fun RegisterUser(
        @Body addUser: RegisterLoginRequest,
    ): Response<RegisterLoginResponse>

//    @POST(Constants.getuserdetails)
//    suspend fun getUserDetails():Response<>

    @POST(Constants.exclusive_collectionProducts)
    suspend fun getExclusiveProducts(
    ): Response<HomeAllProductsResponse>

    @POST(Constants.HomeAllProducts)
    suspend fun getHomeAllProducts(
    ): Response<HomeAllProductsResponse>

    @POST(Constants.BestSelling_collectionProducts)
    suspend fun getBestSellingProducts(
    ): Response<HomeAllProductsResponse>

    @POST(Constants.GetPendingProductById)
    suspend fun GetPendingProductById(@Body productIdIdModal: ProductIdIdModal):Response<ProductByIdResponseModal>

    @POST(Constants.GetBestProductById)
    suspend fun getBestProductById(@Body productIdIdModal: ProductIdIdModal):Response<ProductByIdResponseModal>
    @POST(Constants.GetExclusiveProductById)
    suspend fun getExclusiveProductById(@Body productIdIdModal: ProductIdIdModal):Response<ProductByIdResponseModal>

    @POST(Constants.ApiEnd_login)
    suspend fun LoginUser(
        @Body addUser: String,
    ): Response<String>
    @POST(Constants.getuserdetails)
    suspend fun getuserdetails(@Body user:RegisterLoginRequest):Response<UserResponse>

    @POST(Constants.gettingjwt)
    suspend fun gettingJwtToken(
    ): Response<String>

    @POST(Constants.ItemsCollections)
    suspend fun getItemsCollections(@Body productIdIdModal: ProductIdIdModal):Response<ItemsCollectionsResponse>
@POST(Constants.checkMobileNumberExist)
    suspend fun checkMobileNumberExist(registerLoginRequest: RegisterLoginRequest):Response<CheckNumberExistResponse>


}