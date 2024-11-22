package demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import ivanpg93.demo.R;

public class AuxiliaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxiliary);

        Button goMainButton = findViewById(R.id.goMainButton);
        goMainButton.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(AuxiliaryActivity.this, MainActivity.class));
        });
    }

}
