package com.example.testeapp1;

import static com.example.testeapp1.view.clickedfilename;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

public class DownloadBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, @NonNull Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            //Show a notification
            Intent intentopen = new Intent(context.getApplicationContext(), offlineviewfile.class);
            intentopen.putExtra("clickedfilename", clickedfilename);
            intentopen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentopen);





        }
    }

}