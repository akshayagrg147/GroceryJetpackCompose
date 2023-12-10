package com.grocery.mandixpress.Utils

class Constants {
    companion object{
     const val AppUrl = "https://fa66-103-101-101-152.ngrok-free.app/"
        const val BASE_URLFCM = "https://fcm.googleapis.com"
     const val ApiEnd_register ="Customers/register"
       const val NETWORK_PAGE_SIZE = 10
        const val HomeAllProducts ="Customers/HomeAllProducts"
        const val SearchAllProductsSearch ="Customers/SearchAllProducts"
        const val BestSelling_collectionProducts ="Customers/BestSelling"
        const val registertoken="Customers/registerCustomertoken"
        const val cancelOrder="Admin/OrderStatus"
        const val categorywise_collectionProducts ="Customers/HomeCategoryWiseProducts"
        const val availibilityCheck="Customers/AvailibilityCheck"
        const val GetPendingProductById="Customers/GetPendingProductById"
        const val GetExclusiveProductById="Customers/GetExclusiveProductById"
        const val GetBestProductById="Customers/GetBestProductById"
        const val exclusive_collectionProducts ="Customers/ExclusiveOffers"
        const val ItemsCollections ="Customers/ItemsCollections"
        const val ACTION_CUSTOM_BROADCAST = "com.grocery.mandixpress.Utils.localBroadcast"
     const val ApiEnd_login ="login"
        const val gettingjwt ="gettingJwt"
        const val getuserdetails="Customers/getUserDetails"
        const val getAdminDetails="Customers/getAdminDetails"
        const val checkMobileNumberExist="Customers/checkMobileNumberExist"
        const val  callBannerImage="Customers/getBannerCategory"
        const val CreateOrderId="Customers/CreateOrderId"
        const val AllOrders="Customers/AllOrders"
        const val GetRelatedSearch="Customers/GetRelatedSearch"
        const val getProductCategory="Customers/getProductCategory"
        const val allCoupons="Customers/allCoupons"

        const val NOTIFICATION_CHANNEL_ID = "gravity_fcm_channel"
        const val NOTIFICATION_CHANNEL_NAME = "FCM Notification"
        const val TOPIC = "/topics/Raheem"
         var sellerIdCommon:String?=null


    }
}