package com.example.myfirstapp.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TaskListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskListScreen()
        }
    }
}

/**
 * 任务数据类
 */
data class TaskItem(
    val id: Int,
    val text: String,
    val completed: Boolean = false
)

@Composable
fun TaskListScreen() {
    // 初始化任务列表：3项，完成1项
    val tasks = remember {
        mutableStateListOf(
            TaskItem(1, "学习 Column 和 Row", completed = true),
            TaskItem(2, "学习状态管理", completed = false),
            TaskItem(3, "完成 Compose 实验", completed = false)
        )
    }
    var inputText by remember { mutableStateOf("") }

    // 计算已完成数量
    val completedCount = tasks.count { it.completed }
    val totalCount = tasks.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // 标题
        Text(
            text = "课程学习任务",
            color = Color(0xFFE53935),
            fontSize = 24.sp,
            style = TextStyle(textDecoration = TextDecoration.None)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 输入框 + 添加按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("请输入学习任务") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        tasks.add(TaskItem(tasks.size + System.currentTimeMillis().toInt(), inputText.trim()))
                        inputText = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
            ) {
                Text("添加", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 状态文字
        Text(
            text = "已完成：$completedCount / $totalCount",
            color = Color(0xFFE53935),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 任务列表
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                TaskRow(
                    task = task,
                    onToggle = {
                        val idx = tasks.indexOfFirst { it.id == task.id }
                        if (idx >= 0) {
                            val t = tasks[idx]
                            tasks[idx] = t.copy(completed = !t.completed)
                        }
                    },
                    onDelete = {
                        tasks.removeAll { it.id == task.id }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskRow(
    task: TaskItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 复选框
        IconButton(onClick = onToggle) {
            Icon(
                imageVector = if (task.completed) Icons.Filled.CheckBox else Icons.Filled.CheckBoxOutlineBlank,
                contentDescription = if (task.completed) "已完成" else "未完成",
                tint = if (task.completed) Color(0xFFE53935) else Color.Gray
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // 任务文字
        Text(
            text = task.text,
            modifier = Modifier.weight(1f),
            color = if (task.completed) Color.Gray else Color.Black,
            fontSize = 16.sp,
            style = TextStyle(
                textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None
            )
        )

        // 删除按钮
        TextButton(onClick = onDelete) {
            Text("删除", color = Color(0xFFE53935))
        }
    }
}
