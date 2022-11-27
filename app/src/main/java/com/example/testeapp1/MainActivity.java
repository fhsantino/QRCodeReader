package com.example.testeapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// Função principal do aplicativo
public class MainActivity extends AppCompatActivity {
    // Definiçao dos botões existentes na tela inicial
    Button scanbt;
    Button about;
    Button history;



    // Definição de uma varíavel para armazenamento das informações lidas no código QR.
    public static TextView qrtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Associação dos botões definidos anteriormente com os elementos visuais criados
        scanbt = (Button) findViewById(R.id.scanbt);
        about = (Button) findViewById(R.id.about);
        history = (Button) findViewById(R.id.historybt);
        qrtext = (TextView) findViewById(R.id.qrtext);



        scanbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), Auth.class));

            }
        });

            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(MainActivity.this, about.class);
                    startActivity(myIntent);
                }
            });

            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(MainActivity.this, history.class);
                    startActivity(myIntent);
                }
            });
    }
    public void botao1Action(View view) {
     }
}

