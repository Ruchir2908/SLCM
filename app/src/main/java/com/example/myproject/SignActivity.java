package com.example.myproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class SignActivity extends AppCompatActivity {

    public static Bitmap Asignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SignatureMainLayout(this));

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        if (Asignature!=null) {
            Asignature.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        } else {
            Toast.makeText(this, "Ni chala", Toast.LENGTH_SHORT).show();
        }
        byte[] byteArray = bStream.toByteArray();

        Intent intent = getIntent();
        intent.putExtra("data",byteArray);

    }
}
