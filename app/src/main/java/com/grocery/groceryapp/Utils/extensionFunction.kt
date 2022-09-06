package com.grocery.groceryapp.Utils

import android.content.Context
import android.content.Intent
import android.widget.Toast


fun Context.showMsg(
    msg:String,
    duration:Int = Toast.LENGTH_SHORT
) = Toast.makeText(this,msg,duration).show()


inline fun<reified T:Any> createIntent(
    context:Context
) = Intent(context,T::class.java)


inline fun<reified T:Any> Context.launchActivity(
    noinline bundle: Intent.()->Unit = {}
){

    val intent = createIntent<T>(this)
    intent.bundle()
    startActivity(intent)

}