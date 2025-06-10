package com.example.roomp.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Room : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        }
        val db = Appdatabase.getInstance(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
//            db!!.tododao().insert()
        }
        val dao = db!!.tododao()
    }
}

@Entity( tableName = "todolist")
data class ToDoList(
    val task: String,
    val time: Boolean
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
