@file:Suppress("DEPRECATION")

package com.example.roomp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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