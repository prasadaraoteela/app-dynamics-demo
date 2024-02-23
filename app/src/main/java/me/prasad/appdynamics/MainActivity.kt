package me.prasad.appdynamics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.appdynamics.eumagent.runtime.AgentConfiguration
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
          AppDynamicsControls()
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

  override fun onStart() {
    super.onStart()
    AppDynamics.startModule("Feature 1")
  }

  override fun onStop() {
    super.onStop()
    AppDynamics.stopModule()
  }
}

@Composable
fun AppDynamicsControls() {

  var breadcrumb by rememberSaveable { mutableStateOf("") }
  var session by rememberSaveable { mutableStateOf("") }
  var module by rememberSaveable { mutableStateOf("") }

  Column {

    TextField(
      value = module,
      onValueChange = { module = it },
      label = { Text(text = "Module Name") }
    )

    Button(
      onClick = {
        AppDynamics.startModule(module)
      }) {
      Text(text = "Start module: $module")
    }

    TextField(
      value = session,
      onValueChange = { session = it },
      label = { Text(text = "Session Name") }
    )

    Button(
      onClick = {
        AppDynamics.startSessionFrame(session)
      }) {
      Text(text = "Start session frame: $session")
    }

    TextField(
      value = breadcrumb,
      onValueChange = { breadcrumb = it },
      label = { Text(text = "Breadcrumb") }
    )

    Button(
      onClick = {
        AppDynamics.breadcrumb(breadcrumb)
      }) {
      Text(text = "Leave Bread Crumb")
    }

    Button(
      onClick = {
        try {
          AppDynamics.crash("Crash-$breadcrumb")
        } finally {
          AppDynamics.endSessionFrame()
        }
      }) {
      Text(text = "Crash")
    }

    Button(
      onClick = {
        AppDynamics.endSessionFrame()
      }) {
      Text(text = "End session frame: $session")
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  DemoAppDynamicsTheme {
    AppDynamicsControls()
  }
}