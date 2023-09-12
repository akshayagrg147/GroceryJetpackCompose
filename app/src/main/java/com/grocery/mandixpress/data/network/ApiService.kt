package com.grocery.mandixpress.data.network

import com.grocery.mandixpress.Utils.Constants
import com.grocery.mandixpress.data.modal.*
import com.grocery.mandixpress.features.Home.domain.modal.CouponResponse
import com.grocery.mandixpress.features.Home.domain.modal.getProductCategory
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
    suspend fun getExclusiveProducts(@Body red:ExclusiveOfferRequest): Response<HomeAllProductsResponse>


    @POST(Constants.BestSelling_collectionProducts)
    suspend fun getBestSellingProducts(): Response<HomeAllProductsResponse>


    @GET(Constants.HomeAllProducts)
    suspend fun getHomeAllProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
        @Query("category") gettingData: String
    ): Response<HomeAllProductsResponse>

    @GET(Constants.SearchAllProductsSearch)
    suspend fun getHomeAllProductsSearch(
        @Query("query") character: String,
    ): Response<HomeAllProductsResponse>

    @POST(Constants.allCoupons)
    suspend fun getAllCoupons(

    ): Response<CouponResponse>


    @GET(Constants.categorywise_collectionProducts)
    suspend fun categorywise_collectionProducts(
    ): Response<CategoryWiseDashboardResponse>

    @POST(Constants.availibilityCheck)
    suspend fun availibilityCheck(@Body pincode:String):Response<commonResponse>

    @POST(Constants.GetPendingProductById)
    suspend fun GetPendingProductById(@Body productIdIdModal: ProductIdIdModal): Response<ProductByIdResponseModal>

    @POST(Constants.GetBestProductById)
    suspend fun getBestProductById(@Body productIdIdModal: ProductIdIdModal): Response<ProductByIdResponseModal>

    @POST(Constants.GetExclusiveProductById)
    suspend fun getExclusiveProductById(@Body productIdIdModal: ProductIdIdModal): Response<ProductByIdResponseModal>

    @POST(Constants.ApiEnd_login)
    suspend fun LoginUser(
        @Body addUser: String,
    ): Response<String>

    @POST(Constants.getuserdetails)
    suspend fun getuserdetails(@Body user: RegisterLoginRequest): Response<UserResponse>

    @POST(Constants.gettingjwt)
    suspend fun gettingJwtToken(
    ): Response<String>

    @POST(Constants.ItemsCollections)
    suspend fun getItemsCollections(@Body productIdIdModal: ProductIdIdModal): Response<ItemsCollectionsResponse>

    @POST(Constants.checkMobileNumberExist)
    suspend fun checkMobileNumberExist(@Body registerLoginRequest: RegisterLoginRequest): Response<CheckNumberExistResponse>

    @POST(Constants.CreateOrderId)
    suspend fun CreateOrderId(@Body registerLoginRequest: OrderIdCreateRequest): Response<OrderIdResponse>

    @POST(Constants.AllOrders)
    suspend fun AllOrders(@Body value: String): Response<AllOrdersHistoryList>

    @POST(Constants.GetRelatedSearch)
    suspend fun GetRelatedSearch(@Body obj: RelatedSearchRequest): Response<HomeAllProductsResponse>

    @GET(Constants.getProductCategory)
    suspend fun getProductCategory(): Response<getProductCategory>


}

class CallingCategoryWiseData {
    var data = ""
    fun settingData(ss: String) {
        data = ss
    }

    fun gettingData(): String {
        return data
    }
}