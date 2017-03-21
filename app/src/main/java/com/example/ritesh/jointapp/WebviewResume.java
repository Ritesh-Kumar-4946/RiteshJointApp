package com.example.ritesh.jointapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by ritesh on 26/8/16.
 */
public class WebviewResume extends AppCompatActivity {

    WebView webresume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_resume);

        webresume = (WebView) findViewById(R.id.webresume);

        Bundle extra = getIntent().getExtras();
        String Url = extra.get("URL").toString();

        Log.e(" xxxxxxxxxxxxxxxx Resume URL xxxxxxxxxxxxxxxx :", "" + Url);
        Log.e(" xxxxxxxxxxxxxxxx Resume URL xxxxxxxxxxxxxxxx :", "" + Url);
        Log.e(" xxxxxxxxxxxxxxxx Resume URL xxxxxxxxxxxxxxxx :", "" + Url);

        Log.e(" xxxxxxxxxxxxxxxx Resume URL xxxxxxxxxxxxxxxx \n :", "" +
                "https://docs.google.com/gview?embedded=true&url=" + Url);

        Log.e(" xxxxxxxxxxxxxxxx Resume URL xxxxxxxxxxxxxxxx \n :", "" +
                "https://docs.google.com/gview?embedded=true&url=" + Url);

        Log.e(" xxxxxxxxxxxxxxxx Resume URL xxxxxxxxxxxxxxxx \n :", "" +
                "https://docs.google.com/gview?embedded=true&url=" + Url);

        webresume.getSettings().setJavaScriptEnabled(true);
        webresume.getSettings().setBuiltInZoomControls(true);
        webresume.getSettings().setSupportZoom(true);
        webresume.loadUrl("https://docs.google.com/gview?embedded=true&url=" + Url);

    }
}
