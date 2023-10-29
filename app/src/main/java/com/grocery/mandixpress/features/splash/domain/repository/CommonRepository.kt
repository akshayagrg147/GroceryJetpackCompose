package com.grocery.mandixpress.features.splash.domain.repository

import com.grocery.mandixpress.FCMApiService
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.data.modal.*
import com.grocery.mandixpress.data.network.ApiService
import com.grocery.mandixpress.notification.model.NotificationModel
import com.grocery.mandixpress.common.base.toResultFlow
import javax.inject.Inject

class CommonRepository @Inject constructor(
    @FCMApiService private val fcmService: ApiService,  private val apiService: ApiService,  var sharedPreferences: sharedpreferenceCommon
) {

    fun registerUser(
        addUser: RegisterLoginRequest
    ) = toResultFlow {
        apiService.RegisterUser(addUser)
    }

    fun HomeAllProducts(searchCharacter:String,pincode: String
    ) = toResultFlow {
            apiService.getHomeAllProductsSearch(searchCharacter,pincode)
    }

    fun getAppCoupons(postalCode: String) = toResultFlow {
        apiService.getAllCoupons(postalCode)

    }


    fun ExclusiveProducts(city: String,pincode: String) = toResultFlow {
        apiService.getExclusiveProducts(pincode)
    }
    fun BestSellingProducts(postalCode: String) = toResultFlow {
        apiService.getBestSellingProducts(postalCode)
    }
    fun callingDasboardProducts(postalCode: String) = toResultFlow {
        apiService.categorywise_collectionProducts(postalCode)
    }
     fun availibilityCheck(pincode:String)= toResultFlow { apiService.availibilityCheck(pincode = pincode) }

    fun getUserResponse( mobileNumber:String,pincode: String) = toResultFlow { apiService.getuserdetails(RegisterLoginRequest( phone = mobileNumber,pincode = pincode)) }

    fun callBestProductById(productIdIdModal: ProductIdIdModal
    ) = toResultFlow {
        apiService.getBestProductById(productIdIdModal)
    }
    fun ItemsCollections(productIdIdModal: ProductIdIdModal,postalCode: String
    ) = toResultFlow {
        apiService.getItemsCollections(ProductIdIdModal(productIdIdModal.productId,postalCode))
    }
    fun OrderIdRequest(productIdIdModal: OrderIdCreateRequest
    ) = toResultFlow {
        apiService.CreateOrderId(productIdIdModal)
    }
    fun getAllOrders(string: String
    ) = toResultFlow {
        apiService.AllOrders(string+"@"+sharedPreferences.getMobileNumber()+"@"+sharedPreferences.getPostalCode())
    }
    fun GetRelatedSearch(obj:RelatedSearchRequest
    ) = toResultFlow {
        apiService.GetRelatedSearch(obj)
    }
    fun getProductCategory(postalCode: String) = toResultFlow { apiService.getProductCategory(postalCode) }
    fun gettingJwt()= toResultFlow {apiService.gettingJwtToken()  }
    fun checkMobileNumberExist(registerLoginRequest: RegisterLoginRequest) = toResultFlow { apiService.checkMobileNumberExist(registerLoginRequest) }
    fun getAdminDetails() = toResultFlow { apiService.getAdminDetails() }
    fun bannerImageApiCall(postalCode: String) = toResultFlow {
        apiService.callBannerImage(postalCode)
    }

    suspend fun postNotification(notificationModel: NotificationModel)= toResultFlow {
        fcmService.postNotification(notificationModel)
    }

    fun registerUserToken(newToken: String?,mobile: String?) = toResultFlow{
         apiService.registerToken(newToken?:"",mobile?:"")
    }

    fun cancelOrder(data: OrderStatusRequest) = toResultFlow{
        apiService.cancelOrder(data)
    }


}