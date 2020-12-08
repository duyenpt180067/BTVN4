package com.example.btvn4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.btvn4.R;

class Details extends AppCompatActivity {
    TextView sender;
    TextView name;
    TextView title;
    TextView content;
    TextView time;
    CheckBox favorite;
    Button btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        sender = (TextView) findViewById(R.id.sender);
        name = (TextView)findViewById(R.id.name);
        title = (TextView) findViewById(R.id.title);
        content= (TextView)findViewById(R.id.content);
        time = (TextView) findViewById(R.id.time);
        favorite = (CheckBox) findViewById(R.id.favorite);

        Intent mylocalIntent = getIntent();

        Bundle mybundle = mylocalIntent.getExtras();
        name.setText(mybundle.getString("name"));
        title.setText(mybundle.getString("title"));
        content.setText(mybundle.getString("content"));
        time.setText(mybundle.getString("time"));
        if(mybundle.getBoolean("favorite")) favorite.setButtonDrawable(  R.drawable.ic_baseline_star_24);
        else favorite.setButtonDrawable(  R.drawable.ic_baseline_star_border_24);

        setResult(Activity.RESULT_OK,mylocalIntent);

        btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
