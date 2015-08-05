package com.taobao.thunderloadingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.taobao.library.ThunderLoadingView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create from xml

        ThunderLoadingView view = (ThunderLoadingView) findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click..", Toast.LENGTH_SHORT).show();
            }
        });


//        create in code
//        ThunderLoadingView thunderView = new ThunderLoadingView(this);
//        thunderView.setSize(ThunderLoadingView.Size.MEDIUM);
//        rootView.addView(thunderView);
    }

}
