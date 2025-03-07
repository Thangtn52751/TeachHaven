package dev.nes24.android.techhaven_ph52634_ph52751;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            switchActivity();
        });

    }

    public void switchActivity(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

}