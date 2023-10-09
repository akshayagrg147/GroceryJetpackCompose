package com.grocery.mandixpress.data.network

import android.util.Log
import com.grocery.mandixpress.Utils.Constants
import com.grocery.mandixpress.data.modal.*
import com.grocery.mandixpress.features.Home.domain.modal.CouponResponse
import com.grocery.mandixpress.features.Home.domain.modal.getProductCategory
import com.grocery.mandixpress.notification.model.NotificationModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    companion object {
        private const val CONTENT_TYPE = "application/json"
    }

    @POST(Constants.ApiEnd_register)
    suspend fun RegisterUser(
        @Body addUser: RegisterLoginRequest,
    ): Response<RegisterLoginResponse>

    //    @POST(Constants.getuserdetails)
//    suspend fun getUserDetails():Response<>

    @GET(Constants.exclusive_collectionProducts)
    suspend fun getExclusiveProducts(@Query("pincode") pincode: String): Response<HomeAllProductsResponse>

    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: NotificationModel
    ): Response<ResponseBody>
    @GET(Constants.BestSelling_collectionProducts)
    suspend fun getBestSellingProducts(@Query("pincode") pincode: String): Response<HomeAllProductsResponse>

    @GET(Constants.registertoken)
    suspend fun registerToken(@Query("token") token: String,@Query("mobile") mobile: String): Response<commonResponse>


    @GET(Constants.HomeAllProducts)
    suspend fun getHomeAllProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int,
        @Query("category") gettingData: String
    ): Response<HomeAllProductsResponse>

    @GET(Constants.SearchAllProductsSearch)
    suspend fun getHomeAllProductsSearch(
        @Query("query") character: String,
        @Query("pincode")  pincode: String,
    ): Response<HomeAllProductsResponse>

    @GET(Constants.allCoupons)
    suspend fun getAllCoupons(@Query("pincode") pincode: String): Response<CouponResponse>


    @GET(Constants.categorywise_collectionProducts)
    suspend fun categorywise_collectionProducts( @Query("pincode") pincode: String): Response<CategoryWiseDashboardResponse>

    @POST(Constants.availibilityCheck)
    suspend fun availibilityCheck(@Body pincode:String):Response<commonResponse>

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
    @POST(Constants.getAdminDetails)
    suspend fun getAdminDetails(): Response<AdminResponse>
    @POST(Constants.checkMobileNumberExist)
    suspend fun checkMobileNumberExist(@Body registerLoginRequest: RegisterLoginRequest): Response<CheckNumberExistResponse>
    @GET(Constants.callBannerImage)
    suspend fun callBannerImage(@Query("pincode") pincode: String): Response<BannerImageResponse>

    @POST(Constants.CreateOrderId)
    suspend fun CreateOrderId(@Body registerLoginRequest: OrderIdCreateRequest): Response<OrderIdResponse>

    @POST(Constants.AllOrders)
    suspend fun AllOrders(@Body value: String): Response<AllOrdersHistoryList>

    @POST(Constants.GetRelatedSearch)
    suspend fun GetRelatedSearch(@Body obj: RelatedSearchRequest): Response<HomeAllProductsResponse>

    @GET(Constants.getProductCategory)
    suspend fun getProductCategory(@Query("pincode") pincode: String): Response<getProductCategory>


}

class CallingCategoryWiseData {
    var data = ""
    var bannerItemData:BannerImageResponse.ItemData=BannerImageResponse.ItemData()
    fun settingData(ss: String,postalCode:String) {
        data = ss+"__"+postalCode
    }
    fun setItemDataClass(
        bannerItemDat: BannerImageResponse.ItemData,
        index: Int,
        postalCode: String
    ){
        bannerItemData=bannerItemDat
            if(bannerItemDat.bannercategory1?.isNotEmpty()==true){
                if(index==0){
                settingData(bannerItemData.bannercategory1?:"",postalCode)
            }
                else if(index==1){
                    settingData(bannerItemData.bannercategory2?:"",postalCode)
                }
                else{
                    settingData(bannerItemData.bannercategory3?:"",postalCode)
                }


        }
        else{
                 if(index==0){
                    settingData(bannerItemData.bannercategory2?:"",postalCode)
                }
                else{
                    settingData(bannerItemData.bannercategory3?:"",postalCode)
                }
            }


    }
    fun getItemDataClass():BannerImageResponse.ItemData{
        return bannerItemData
    }

    fun gettingData(): String {
        return data
    }
}