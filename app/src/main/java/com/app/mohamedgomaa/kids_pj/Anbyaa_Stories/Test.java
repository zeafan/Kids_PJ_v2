package com.app.mohamedgomaa.kids_pj.Anbyaa_Stories;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import com.app.mohamedgomaa.kids_pj.R;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void Action(View view) {
        String url_string="https://zeafancom.000webhostapp.com/kids_story_pdf/file_10.pdf";
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url_string));
        request.setDescription("يتم الأن تنزيل القصة ..أنتظر قليلاَ ");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("التحميل..");
        request.allowScanningByMediaScanner();
        String file_name= URLUtil.guessFileName(url_string,null, MimeTypeMap.getFileExtensionFromUrl(url_string));
        request.setDestinationInExternalPublicDir((Environment.DIRECTORY_DOWNLOADS),file_name);
        DownloadManager Down_mng=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Down_mng.enqueue(request);
    }
}
