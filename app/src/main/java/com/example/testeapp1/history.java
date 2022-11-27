package com.example.testeapp1;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;


public class history extends AppCompatActivity {
    private static final int permission_request_read = 1;

    ArrayList<String> arrayList;
    ListView listView;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedinstancestate) {
        super.onCreate(savedinstancestate);
        setContentView(R.layout.activity_history);

        // Rodar Permissões

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    showList();
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

    // Função para exibir a lista de consultas realizadas
    public void showList() {
        // Definição do elemento visual listView no código
        listView = (ListView) findViewById(R.id.listView);
        // Criação de um array para armazenar o nome dos arquivos
        ArrayList<String> inFiles = new ArrayList<String>();
        // Caminho da pasta para a realização da listagem dos arquivos presentes
        File[] files = getExternalFilesDir("/Download/app_farmlabscan").listFiles();
        // Verifica a pasta está vazia
        if (files !=null) {
            // Se a pasta não está vazia
            for (File file : files) {
                // Adiciona o nome dos arquivos da pasta no array
                // inFiles.add(file.getName().replaceFirst("[.][^.]+$", ""));
                inFiles.add(file.getName().split("[_.]")[2]);
            }
            // Cria e exibe o listView com o nome dos arquivos
            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, inFiles);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                // No evento de click em um elemento da lista
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Armazena em uma string o nome do arquivo selecionado
                    String clickedfilename = inFiles.get(position);
                    // Define a chamada da atividade offlineviewfile
                    Intent intent= new Intent(getApplicationContext(), offlineviewfile.class);
                    // Insere o ".pdf" após o nome do arquivo para a localização na pasta
                    intent.putExtra("clickedfilename", "Plantas_Medicinais_" + clickedfilename +".pdf");
                    // Inicia a atividade
                    startActivity(intent);

                }
            });

        }


    }
}
