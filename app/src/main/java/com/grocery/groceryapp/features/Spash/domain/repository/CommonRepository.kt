package com.grocery.groceryapp.features.Spash.domain.repository

import com.grocery.groceryapp.data.modal.*
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
    fun HomeAllProducts(ss:String
    ) = toResultFlow {
        apiService.getHomeAllProductsSearch(ss)
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
    fun Allorders(
    ) = toResultFlow {
        apiService.AllOrders()
    }
    fun GetRelatedSearch(obj:RelatedSearchRequest
    ) = toResultFlow {
        apiService.GetRelatedSearch(obj)
    }
    fun getProductCategory()= toResultFlow { apiService.getProductCategory() }
    fun gettingJwt()= toResultFlow {apiService.gettingJwtToken()  }
    fun checkMobileNumberExist(registerLoginRequest: RegisterLoginRequest) = toResultFlow { apiService.checkMobileNumberExist(registerLoginRequest) }



}