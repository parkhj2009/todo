package com.example.roomp

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CreateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colorValue = intent.getLongExtra("Bcolor", 0xFF4F9F9C)
        val baseColor = Color(colorValue.toULong())
        enableEdgeToEdge()
        setContent {
            Menu(base = baseColor)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(base: Color) {
    var showInput by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var al by remember { mutableStateOf(0.0f) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    val formattedDate = selectedDateMillis?.let {
        val date = Date(it)
        val formatter = SimpleDateFormat("EEE d MMMM yyyy", Locale.ENGLISH)
        formatter.format(date)
    } ?: "Pick a date"

    Box(modifier = Modifier.fillMaxSize()){
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = al))
            )
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
                val formatter = SimpleDateFormat("EEE d MMMM yyyy", Locale.ENGLISH)
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
                                clip = true
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
                            Modifier
                                .size(35.dp)
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

                // 텍스트 창
                if (showInput) {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("eg : Meeting with client") },
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 설명 입력
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth(),

                        )

                    Spacer(modifier = Modifier.height(24.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {             // 전송 버튼
                        IconButton(onClick = {
                            showDatePicker = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_sent),
                                contentDescription = "Send",
                                tint = base
                            )
                        }
                        if (showDatePicker) {
                            DatePickerModal(
                                onDateSelected = {
                                    selectedDateMillis = it
                                },
                                onDismiss = {
                                    showDatePicker = false
                                }
                            )
                        }

                    }
                } }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    var showclock by remember { mutableStateOf(false) }

    Box() {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
            }
    }
    ) {
        DatePicker(state = datePickerState)
    }
}
}