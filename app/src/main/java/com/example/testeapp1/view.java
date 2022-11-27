package com.example.testeapp1;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.URLUtil;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
public class view extends AppCompatActivity {
    File file;
    PDFView pdfView;
    String filetitle;
    public static String clickedfilename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inicialização do código
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        // Definição de variáveis
        String firebaseappstorage = "https://firebasestorage.googleapis.com/v0/b/qr-code---plantas-medicinais.appspot.com/o/";
        String url = firebaseappstorage + getIntent().getStringExtra("url");  // URL escaneada que remete às informações para serem consultadas no Firebase
        // Cria pasta no diretório para salvar arquivos baixados
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "app_farmlabscan");
        if (!f.exists()) {
            f.mkdirs();
        }
        // Configuração do Download Manager para realizar o download do arquivo
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        filetitle = URLUtil.guessFileName(url, null, null);
        request.setTitle(filetitle);
        boolean start_check = filetitle.startsWith("Plantas_Medicinais_");

        if (start_check) {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setDestinationInExternalFilesDir(getApplicationContext(),"/Download/app_farmlabscan", filetitle);
            // Verifica se o arquivo já existe no local
            File oldV = new File(f, filetitle);
            if (oldV.exists()) {
                boolean delete = oldV.delete();
            }
            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            // "Salva" o nome do arquivo escaneado na variável clickedfilename (Global, para ser acessado por outras Activities)
            clickedfilename = filetitle;
            // Quando o download terminar, o DownloadManager chamará automaticamente DownloadBroadcastReceiver
        }
        else {
             Intent intent = new Intent(getApplicationContext(), Error.class);
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}