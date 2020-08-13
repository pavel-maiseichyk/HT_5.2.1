package com.example.ht_521;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText loginET;
    private EditText passwordET;
    private Button loginButton;
    private Button registrationButton;
    private static final String fileName = "EnterData.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    checkData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveData() throws IOException {
        String loginInput = loginET.getText().toString();
        String passwordInput = passwordET.getText().toString();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE)));
        bufferedWriter.append(loginInput).append(";").append(passwordInput);
        bufferedWriter.close();
        if (loginInput.equals("") || passwordInput.equals(""))
            Toast.makeText(this, "Неполные данные :(", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Данные сохранены)", Toast.LENGTH_SHORT).show();
    }

    private void checkData() throws IOException {
        String loginInput = loginET.getText().toString();
        String passwordInput = passwordET.getText().toString();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
        String line = bufferedReader.readLine();
        if (line.equals(""))
            Toast.makeText(this, "Сначала зарегистируйтесь)", Toast.LENGTH_SHORT).show();
        else {
            if (loginInput.equals("") || passwordInput.equals(""))
                Toast.makeText(this, "Неполные данные :(", Toast.LENGTH_SHORT).show();
            else {
                String[] data = line.split(";");
                String savedLogin = data[0];
                String savedPassword = data[1];
                if (loginInput.equals(savedLogin) && passwordInput.equals(savedPassword)) {
                    Toast.makeText(this, "Вход успешен)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, NotReallyAnEasterEgg.class));
                } else Toast.makeText(this, "Данные не совпадают :(", Toast.LENGTH_SHORT).show();
            }
        }
        bufferedReader.close();
    }

    public void initViews() {
        loginET = findViewById(R.id.loginET);
        passwordET = findViewById(R.id.passwordET);
        loginButton = findViewById(R.id.loginButton);
        registrationButton = findViewById(R.id.registrationButton);
    }
}