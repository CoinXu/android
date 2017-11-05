package com.example.asd.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class OpenBrowserActive extends AppCompatActivity {
    private static final String LOG_TAG = "OpenBrowserActive";

    /**
     * open browser
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Log.i(LOG_TAG, url);

        Uri uri = Uri.parse(url);
        Intent web = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(web);
    }
}
