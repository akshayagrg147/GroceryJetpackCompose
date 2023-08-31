package com.grocery.mandixpress.Utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.widget.Toast


fun Context.showMsg(
    msg:String,
    duration:Int = Toast.LENGTH_SHORT
) = Toast.makeText(this,msg,duration).show()

fun Context.getActivity(): Activity?= when(this){
is Activity->this
    is ContextWrapper->baseContext.getActivity()
    else->null
}


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