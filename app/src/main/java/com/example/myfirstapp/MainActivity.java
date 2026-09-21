package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myfirstapp.compose.TaskListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_linear).setOnClickListener(v ->
                startActivity(new Intent(this, LinearLayoutActivity.class)));
        findViewById(R.id.btn_table).setOnClickListener(v ->
                startActivity(new Intent(this, TableLayoutActivity.class)));
        findViewById(R.id.btn_calculator).setOnClickListener(v ->
                startActivity(new Intent(this, CalculatorActivity.class)));
        findViewById(R.id.btn_space).setOnClickListener(v ->
                startActivity(new Intent(this, SpaceTravelActivity.class)));
        findViewById(R.id.btn_compose).setOnClickListener(v ->
                startActivity(new Intent(this, TaskListActivity.class)));
    }
}
