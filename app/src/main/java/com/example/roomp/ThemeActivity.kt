    package com.example.roomp

    import android.content.Intent
    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.material3.TopAppBar
    import androidx.compose.material3.TopAppBarDefaults
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.shadow
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp

    class ThemeActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val colorValue = intent.getLongExtra("Bcolor", 0xFF4F9F9C)
            val baseColor = Color(colorValue.toULong())
            enableEdgeToEdge()
            setContent {

                var currentBaseColor by remember { mutableStateOf(baseColor) }
                Main(base = currentBaseColor, onBaseColorChange = { newColor ->
                    currentBaseColor = newColor
                })
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Main(
        base: Color,
        onBaseColorChange: (Color) -> Unit) {
        val context = LocalContext.current
        val scrollState = rememberScrollState()
        Scaffold(
            containerColor = Color.White,
            topBar = {
                TopAppBar(
                    title = { Text("Menu Homepage") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = base,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text="Creat to do list",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    textAlign= TextAlign.Center,

                    fontSize = 40.sp
                )
                Text(
                    text="Choose your to do list color theme:",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    textAlign= TextAlign.Center,

                    fontSize = 15.sp
                )

                //테마 선택
                color(Color(0xFF4F9F9C)) {
                    onBaseColorChange(it)
                }

                color(Color(0xFF1b1c1f)) {
                    onBaseColorChange(it)
                }

                color(Color(0xFFd85040)) {
                    onBaseColorChange(it)
                }

                color(Color(0xFF3875ea)) {
                    onBaseColorChange(it)
                }

                Button(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .padding(30.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = base, // 버튼 배경색
                        contentColor = Color.White          // 텍스트 색상
                    ),
                    shape= RoundedCornerShape(10.dp),
                    onClick = {
                        val intent2 = Intent(context, CreateActivity::class.java).apply {
                            putExtra("Bcolor", base.value.toLong())
                        }
                        context.startActivity(intent2)
                    }
                ) {
                    Text("Open Todyapp")
                }
            }
        }
    }


    @Composable
    fun color(base: Color, onClick:(Color)->Unit){
        Box(
            modifier = Modifier
                .padding(
                    top = 20.dp
                )
                .width(360.dp)
                .height(100.dp)
                .clickable {onClick(base)}) {
            // 흰 부분
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .width(360.dp)
                    .height(100.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = true // 둥근 그림자와 함께 모양을 잘라줌
                    )
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            )

            //색 부분
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .width(360.dp)
                    .height(30.dp)
                    .background(
                        base, shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )

            )

        }
    }


