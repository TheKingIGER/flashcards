package com.TheKing.flashcard;

import android.app.Application;
import android.content.Context;

import java.io.File;

/**
 * Created by Lars on 10.02.2018.
 */

public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static File getListRootDir() {
        return new File(context.getExternalCacheDir(), "lists");
    }

    public static File getJsonRootDir() {
        return new File(context.getExternalCacheDir(), "json");
    }

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
        File file = new File(context.getExternalCacheDir(),"Json");
        file.mkdir();
    }
}
