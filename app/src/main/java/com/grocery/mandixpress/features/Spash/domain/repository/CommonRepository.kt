package com.grocery.mandixpress.features.Spash.domain.repository

import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.data.modal.*
import com.grocery.mandixpress.data.network.ApiService
import com.grocery.mandixpress.toResultFlow
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val apiService: ApiService,  var sharedPreferences: sharedpreferenceCommon
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

    fun getAppCoupons(
    ) = toResultFlow {
        apiService.getAllCoupons()

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

    fun getUserResponse( mobileNumber:String) = toResultFlow { apiService.getuserdetails(RegisterLoginRequest(null,null,mobileNumber)) }

    fun callBestProductById(productIdIdModal: ProductIdIdModal
    ) = toResultFlow {
        apiService.getBestProductById(productIdIdModal)
    }
    fun ItemsCollections(productIdIdModal: ProductIdIdModal
    ) = toResultFlow {
        apiService.getItemsCollections(productIdIdModal)
    }
    fun OrderIdRequest(productIdIdModal: OrderIdCreateRequest
    ) = toResultFlow {
        apiService.CreateOrderId(productIdIdModal)
    }
    fun getAllOrders(string: String
    ) = toResultFlow {
        apiService.AllOrders(string+"@"+sharedPreferences.getMobileNumber())
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


}