package me.prasad.appdynamics

import android.util.Log
import com.appdynamics.eumagent.runtime.Instrumentation
import com.appdynamics.eumagent.runtime.SessionFrame

/**
 * Created by Prasada Rao on 20/02/24 12:39 pm
 **/
object AppDynamics {

  private const val TAG = "AppDynamics"

  private var sessionFrame: SessionFrame? = null

  fun startSessionFrame(sessionName: String) {
    Log.d(TAG, "startSessionFrame() called with: sessionName = $sessionName")
    sessionFrame = Instrumentation.startSessionFrame(sessionName)
  }

  fun endSessionFrame() {
    Log.d(TAG, "endSessionFrame() called")
    sessionFrame?.end()
    sessionFrame = null
  }

  fun breadcrumb(breadcrumb: String) {
    Log.d(TAG, "breadcrumb() called with: breadcrumb = $breadcrumb")
    Instrumentation.leaveBreadcrumb(breadcrumb)
  }

  fun crash(message: String) {
    Log.d(TAG, "crash() called with: message = $message")
    throw RuntimeException(message)
  }

  fun startModule(value: String) {
    Log.d(TAG, "startModule() called with: value = $value")
    Instrumentation.setUserData("Module", value)
  }

  fun stopModule() {
    Log.d(TAG, "stopModule() called")
    Instrumentation.clearAllUserData()
  }
}