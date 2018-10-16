package fr.thatmuch.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get button
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
           public void onClick (View v) {startActivity(new Intent(MainActivity.this, PongActivity.class));
           }
        });

    }
}
