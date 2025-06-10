@file:Suppress("DEPRECATION")

package com.example.roomp
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
var base = Color(0xFF4F9F9C)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MyApp()
        }
    }
}
@Composable
fun SetNavigationBarColor(color: Color, darkIcons: Boolean = true) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = color,
            darkIcons = darkIcons
        )
    }
}

@Composable
fun SetStatusBarColor(
    color: Color,
    darkIcons: Boolean
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen() {
    SetNavigationBarColor(color = Color.Black, darkIcons = false)
    SetStatusBarColor(color = base, darkIcons = false)
    Scaffold(
        containerColor = base,
        topBar = {
            TopAppBar(
                title = { Text("Register/Sign in") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = base,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 230.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.checklist),
                    contentDescription = "todolist",
                    modifier = Modifier.size(160.dp)
                )
                Text(
                    color = Color.White,
                    text = "Todyapp",
                    fontWeight = FontWeight(900),
                    fontSize = 30.sp
                )
                Text(
                    color = Color.White,
                    text = "The best todo list application for you",
                    fontSize = 14.sp
                )
            }
        }
    }
}


//@Preview
@Composable
fun MyApp() {
    val context = LocalContext.current

    var showSplash by remember { mutableStateOf(true) }

    val baseColor by remember { mutableStateOf(Color(0xFF4F9F9C)) }


    LaunchedEffect(Unit) {
        delay(2000) // 2초 딜레이
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        val intent = Intent(context, ThemeActivity::class.java).apply {
            putExtra("Bcolor", baseColor.value.toLong())
        }
        context.startActivity(intent)
    }
}