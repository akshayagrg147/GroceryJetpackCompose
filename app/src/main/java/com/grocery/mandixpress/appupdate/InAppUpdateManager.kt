package com.grocery.mandixpress.appupdate

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class InAppUpdateManager(private val activity: Activity) {
    private val appUpdateManager: AppUpdateManager =
        AppUpdateManagerFactory.create(activity)

    suspend fun checkForUpdates(): Boolean {
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo

        return suspendCancellableCoroutine { continuation ->
            appUpdateInfoTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val appUpdateInfo = task.result
                    val updateAvailable =
                        appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)

                    continuation.resume(updateAvailable)
                } else {
                    continuation.resume(false)
                }
            }
        }
    }

    fun startUpdateFlow() {

        // Fetch the appUpdateInfo from the AppUpdateManager
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val appUpdateInfo = task.result
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    // Start the update flow
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE, // Or AppUpdateType.IMMEDIATE if you prefer immediate updates
                        activity,
                        REQUEST_CODE_UPDATE
                    )
                }
            }
        }
    }


    companion object {
        private const val REQUEST_CODE_UPDATE = 1001
    }
}