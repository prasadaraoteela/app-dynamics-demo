package me.prasad.appdynamics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.appdynamics.eumagent.runtime.AgentConfiguration
import com.appdynamics.eumagent.runtime.CrashReportSummary
import com.appdynamics.eumagent.runtime.ErrorSeverityLevel
import com.appdynamics.eumagent.runtime.Instrumentation
import me.prasad.appdynamics.ui.theme.DemoAppDynamicsTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    startAppDynamics()
    super.onCreate(savedInstanceState)
    setContent {
      DemoAppDynamicsTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Greeting("Android")
        }
      }
    }
  }

  private fun startAppDynamics() {
    Instrumentation.start(
      AgentConfiguration.builder()
        .withAppKey("AD-AAB-ACR-SJJ")
        .withContext(applicationContext)
        // Configure the iOS Agent to report the metrics and screenshots to
        // the SaaS EUM Server in APAC.
        .build()
    )
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Column {
    Button(
      onClick = {
        Log.i("AppDynamics", "Reporting error")
        AppDynamics.reportError(RuntimeException("New crash with Breadcrumb!"))
      }) {
      Text(text = "Crash")
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  DemoAppDynamicsTheme {
    Greeting("Android")
  }
}