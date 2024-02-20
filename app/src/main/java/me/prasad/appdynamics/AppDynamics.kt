package me.prasad.appdynamics

import com.appdynamics.eumagent.runtime.ErrorSeverityLevel
import com.appdynamics.eumagent.runtime.Instrumentation

/**
 * Created by Prasada Rao on 20/02/24 12:39 pm
 **/
object AppDynamics {

  fun reportError(throwable: Throwable) {
    //Instrumentation.reportMetric("main", 1001)
    Instrumentation.leaveBreadcrumb("main")
    Instrumentation.reportError(throwable, ErrorSeverityLevel.CRITICAL)
  }
}