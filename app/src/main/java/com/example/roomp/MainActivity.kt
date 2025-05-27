package com.example.roomp

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter

var base = Color(0xFF4F9F9C)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MyApp()
        }
        val db = Appdatabase.getInstance(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
//            db!!.tododao().
        }
        val dao = db!!.tododao()
    }
}


@Entity( tableName = "todolist")
data class ToDoList(
    val task: String,
    val isDone: Boolean
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

@Dao
interface ToDoDao {
    @Insert
    suspend fun insert(todo: ToDoList)

    @Update
    suspend fun update(todo: ToDoList)

    @Delete
    suspend fun delete(todo: ToDoList)

    @Query("SELECT * FROM todolist")
    suspend fun getAll(): List<ToDoList>

    @Query("DELETE FROM todolist")
    suspend fun deleteAll()

}

@Database(entities = [ToDoList::class], version = 1)
abstract class Appdatabase: RoomDatabase() {
    abstract fun tododao(): ToDoDao

    companion object {
        private var instance: Appdatabase? = null

        @Synchronized
        fun getInstance(context: Context): Appdatabase? {
            if (instance == null) {
                synchronized(Appdatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Appdatabase::class.java,
                        "App-database"
                    ).build()
                }
            }
            return instance
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(navController: NavController,
         base: Color,
         onBaseColorChange: (Color) -> Unit){
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
                .fillMaxWidth()
                .height(1000.dp)
                .padding(
                    top = innerPadding.calculateTopPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Text(
                text="Creat to do list",
                modifier = Modifier
                .fillMaxWidth()
                    .padding(top = 40.dp),
                textAlign=TextAlign.Center,

                fontSize = 40.sp
            )
            Text(
                text="Choose your to do list color theme:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign=TextAlign.Center,

                fontSize = 15.sp
            )
            Image(
                painter = painterResource(id = R.drawable.basic),
                contentDescription = "Theme",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .size(400.dp)
                    .clickable { onBaseColorChange(Color(0xFF4F9F9C)) }
                    .offset(y =30.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.black),
                contentDescription = "Theme",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .size(400.dp)
                    .clickable { onBaseColorChange(Color(0xFF1b1c1f)) }
                    .offset(y = 30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.orange),
                contentDescription = "Theme",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .size(400.dp)
                    .clickable { onBaseColorChange(Color(0xFFd85040)) }
                    .offset(y = 30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.bluee),
                contentDescription = "Theme",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .size(400.dp)
                    .clickable { onBaseColorChange(Color(0xFF3875ea)) }
                    .offset(y = 30.dp)
            )
            Button(
                modifier = Modifier
                    .offset(y = 50.dp)
                    .padding(30.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = base, // 버튼 배경색
                    contentColor = Color.White          // 텍스트 색상
                ),
                shape=RoundedCornerShape(10.dp),
                onClick = {
                    navController.navigate("menu")
                }
            ) {
                Text("Open Todyapp")
            }
        }
    }
}

//@Preview
@Composable
fun MyApp() {
    val navController = rememberNavController()

    var showSplash by remember { mutableStateOf(true) }

    var baseColor by remember { mutableStateOf(Color(0xFF4F9F9C)) }


    LaunchedEffect(Unit) {
        delay(2000) // 2초 딜레이
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                Main(
                    navController,
                    base = baseColor,
                    onBaseColorChange = { baseColor = it }
                )
            }
            composable("menu") { Menu(base = baseColor) }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(base: Color) {
    var showInput by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
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
                .fillMaxWidth()
                .height(1000.dp)
                .padding(
                    top = innerPadding.calculateTopPadding()
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            val today = Date()
            val formatter = SimpleDateFormat("EEE d MMMM YYYY", Locale.ENGLISH)
            val formattedDate = formatter.format(today)

            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Today",
                fontSize = 30.sp
            )
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "Best platform for creating to-do list",
                fontSize = 15.sp,
                color = Color.Gray
            )
            Box(modifier = Modifier.clickable { showInput = true}) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(
                            top = 20.dp
                        )
                        .width(360.dp)
                        .height(200.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp),
                            clip = true // 둥근 그림자와 함께 모양을 잘라줌
                        )
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .width(360.dp)
                        .padding(
                            top = 20.dp
                        )
                        .height(50.dp)
                        .background(
                            base, shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )

                )
                Box(
                    modifier = Modifier
                        .offset(y = 50.dp)
                        .align(Alignment.Center)
                        .width(340.dp)
                        .height(1.dp)
                        .background(Color.Gray)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(start = 56.dp, end = 65.dp)
                        .fillMaxWidth()
                        .offset(y = 90.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "plus",
                        Modifier.size(35.dp)
                    )
                    Text(
                        text = "Tap plus to creat a new task",
                        textAlign = TextAlign.Center,
                        fontSize = 23.sp,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(300.dp)
                        .offset(x = 60.dp, y = 180.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Add your task",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )
                    Text(
                        modifier = Modifier,
                        text = formattedDate,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )

                }

            }
            if (showInput) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(200.dp),
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("eg : Meeting with client") },
                )

                Text(
                    text="$text",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(200.dp),
                    )
            }
        }
    }
}




