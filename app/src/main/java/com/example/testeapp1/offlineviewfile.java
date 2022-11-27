package com.example.testeapp1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.Objects;

public class offlineviewfile extends AppCompatActivity  {
    File file;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlineviewfile);
        // Recebe o nome do arquivo selecionado pelo usuário
        String clickedfilename = getIntent().getStringExtra("clickedfilename");
        // Verifica se o arquivo é o da planta

        // Definição do local de leitura
        String path = getExternalFilesDir("/Download/app_farmlabscan").getPath();
        file = new File(path+"/"+clickedfilename);

        Uri fileUri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()), BuildConfig.APPLICATION_ID + ".provider", file);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromUri(fileUri)
                .defaultPage(1)
                .swipeHorizontal(true)
                .enableSwipe(true)
                .load();
    }
    public void onBackPressed() {
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}