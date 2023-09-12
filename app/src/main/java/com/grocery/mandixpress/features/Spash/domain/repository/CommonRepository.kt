package com.grocery.mandixpress.features.Spash.domain.repository

import android.content.SharedPreferences
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
    fun LoginUser(
        addUser: String
    ) = toResultFlow {
        apiService.LoginUser(addUser)
    }
    fun HomeAllProducts(ss:String
    ) = toResultFlow {
        apiService.getHomeAllProductsSearch(ss)

    }

    fun getAppCoupons(
    ) = toResultFlow {
        apiService.getAllCoupons()

    }


    fun ExclusiveProducts(city: String) = toResultFlow {
        apiService.getExclusiveProducts(ExclusiveOfferRequest(city))
    }
    fun BestSellingProducts() = toResultFlow {
        apiService.getBestSellingProducts()
    }
    fun callingDasboardProducts(
    ) = toResultFlow {
        apiService.categorywise_collectionProducts()
    }
     fun availibilityCheck(pincode:String)= toResultFlow { apiService.availibilityCheck(pincode = pincode) }

    fun getUserResponse( mobileNumber:String) = toResultFlow { apiService.getuserdetails(RegisterLoginRequest(null,null,mobileNumber)) }
    fun callPendingProductById(productIdIdModal: ProductIdIdModal
    ) = toResultFlow {
        apiService.GetPendingProductById(productIdIdModal)
    }
    fun callBestProductById(productIdIdModal: ProductIdIdModal
    ) = toResultFlow {
        apiService.getBestProductById(productIdIdModal)
    }
    fun callEclusiveById(productIdIdModal: ProductIdIdModal
    ) = toResultFlow {
        apiService.getExclusiveProductById(productIdIdModal)
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
    fun getProductCategory()= toResultFlow { apiService.getProductCategory() }
    fun gettingJwt()= toResultFlow {apiService.gettingJwtToken()  }
    fun checkMobileNumberExist(registerLoginRequest: RegisterLoginRequest) = toResultFlow { apiService.checkMobileNumberExist(registerLoginRequest) }



}