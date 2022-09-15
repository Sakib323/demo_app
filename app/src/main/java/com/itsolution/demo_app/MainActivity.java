package com.itsolution.demo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    CameraManager cameraManager;
    String camID;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn=findViewById(R.id.turn_on);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

        textView=findViewById(R.id.txt);

        cameraManager= (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            camID=cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


    }


    private static final int SPEECH_REQUEST_CODE = 0;
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            textView.setText(spokenText);

            if(spokenText.contains("turn on")){
                turn_on();
            }
            if(spokenText.contains("turn off")){
                turn_off();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void turn_on(){
        Toast.makeText(MainActivity.this, "function working", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Toast.makeText(MainActivity.this, "working", Toast.LENGTH_SHORT).show();
                cameraManager.setTorchMode(camID,true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void turn_off(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Toast.makeText(MainActivity.this, "working", Toast.LENGTH_SHORT).show();
                cameraManager.setTorchMode(camID,false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

    }
}