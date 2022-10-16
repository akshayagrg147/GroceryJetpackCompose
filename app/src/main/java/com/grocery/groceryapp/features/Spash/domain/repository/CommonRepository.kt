package com.grocery.groceryapp.features.Spash.domain.repository

import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.data.modal.RegisterLoginRequest
import com.grocery.groceryapp.data.network.ApiService
import com.grocery.groceryapp.toResultFlow
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val apiService: ApiService
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
    fun HomeAllProducts(
    ) = toResultFlow {
        apiService.getHomeAllProducts()
    }
    fun ExclusiveProducts(
    ) = toResultFlow {
        apiService.getExclusiveProducts()
    }
    fun BestSellingProducts(
    ) = toResultFlow {
        apiService.getBestSellingProducts()
    }
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
    fun gettingJwt()= toResultFlow {apiService.gettingJwtToken()  }
    fun checkMobileNumberExist(registerLoginRequest: RegisterLoginRequest) = toResultFlow { apiService.checkMobileNumberExist(registerLoginRequest) }



}