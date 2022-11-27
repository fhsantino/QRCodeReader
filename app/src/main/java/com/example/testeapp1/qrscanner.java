package com.example.testeapp1;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

// Definição da função contém a implementação da API ZXingScannerView, responsável por realizar a
// leitura e interpretação das informações contidas no código QR.
public class qrscanner extends AppCompatActivity implements ZXingScannerView.ResultHandler  {
    // Declaração dos parâmetros
    ZXingScannerView scannerView;
    DatabaseReference dbref; // Será usado para conexão com o Firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Inicialização do Scanner
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);
        // Conexão com o Firebase
        dbref= FirebaseDatabase.getInstance().getReference("qrdata");

        // Solicita permissão para salvar arquivos
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        // Se aprovado, também solicita permissão para acessar a câmera
                        Dexter.withContext(getApplicationContext())
                                .withPermission(Manifest.permission.CAMERA)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                        Dexter.withContext(getApplicationContext())
                                                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                                .withListener(new PermissionListener() {
                                                    @Override
                                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                                        // Se aprovado, inicializa scanner
                                                        scannerView.startCamera();
                                                    }
                                                    @Override
                                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                                    }
                                                    @Override
                                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                                        permissionToken.continuePermissionRequest();
                                                    }

                                                }).check();

                                    }
                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                    }
                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                        permissionToken.continuePermissionRequest();
                                    }
                                }).check();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    @Override
    // Função que recebe os resultados da leitura
    public void handleResult(Result rawResult) {
        // Declaração da variável e armazenamento dos resultados da leitura das informações
        String data= rawResult.getText().toString();
        Runtime runtime = Runtime.getRuntime();
        try{
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();

            dbref.push().setValue(data) // Envia a informação para o Firebase
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        // Realiza o encaminhamento para a atividade View
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent= new Intent(getApplicationContext(), view.class);
                            intent.putExtra("url", data); // Inclui a variável data
                            Toast.makeText(getApplicationContext(), "Código capturado, por favor aguarde", Toast.LENGTH_LONG).show();
                            startActivity(intent); // Segue para a próxima etapa
                        }
                    });
        }catch (IOException e)          { e.printStackTrace(); Toast.makeText(getApplicationContext(), "Falha na conexão", Toast.LENGTH_LONG).show();}
        catch (InterruptedException e) { e.printStackTrace(); Toast.makeText(getApplicationContext(), "Falha na conexão", Toast.LENGTH_LONG).show();}


        }


    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    public void onBackPressed() {
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}