package com.bigpicture.hje.scan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //context 액티비티랑 같은 거라고 보면됨
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Button button_shop = (Button) findViewById(R.id.button_shop);
        button_shop.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapsActivity.class);
                startActivity(intent);
            } });

        Button button_item = (Button) findViewById(R.id.button_item);
        button_item.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "ALL");
                    startActivityForResult(intent, 0);
                }else{
                    ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CAMERA},0);
                }
            } });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 0) {

            if(resultCode == Activity.RESULT_OK)
            {
                String contents = data.getStringExtra("SCAN_RESULT");
                Toast.makeText (context, contents, Toast.LENGTH_LONG).show();

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
