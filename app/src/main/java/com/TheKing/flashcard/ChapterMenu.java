package com.TheKing.flashcard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChapterMenu extends AppCompatActivity {

    private JSONObject Chapter;
     ArrayList<String> ModuleList;

    private class MenuAdapter extends ArrayAdapter<String> {

        public MenuAdapter(Context context) {
            super(context, 0);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item = this.getItem(position);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.menu_item_view, parent, false);
            }

            TextView label = convertView.findViewById(R.id.label);
            label.setText(item);

            return convertView;
        }
    }

    private MenuAdapter MenuAdapter;

    public ChapterMenu(JSONObject Chapter) {
        this.Chapter = Chapter;
    }
    public ChapterMenu() {

        File file = new File(App.getJsonRootDir(), "japanese.json");
        String text = "";
        try {
            FileInputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            JSONObject json = new JSONObject(text);
            JSONArray chapterJson = json.getJSONArray("chapters");
            for (int i = 0; i < chapterJson.length(); i++) {
                this.Chapter = chapterJson.getJSONObject(i);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void readJsonObject(){
        try {
            JSONArray modulesList = Chapter.getJSONArray("modules");
            for (int i = 0; i < modulesList.length(); i++) {
                JSONObject module = modulesList.getJSONObject(i);
                ModuleList.add(module.getString("typ"));
                String file = module.getString("file");
                JSONArray targetLanguage = module.getJSONArray("targetlanguage");
                JSONArray availableLanguages = module.getJSONArray("availablelanguages");
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ModuleList = new ArrayList<>();

        readJsonObject();

        MenuAdapter = new MenuAdapter(this);
        MenuAdapter.addAll(ModuleList);

        ListView listView = findViewById(R.id.ChapterList);
        listView.setAdapter(MenuAdapter);

        TextView title = findViewById(R.id.ChapterMenuTitle);
        title.setText("Japanese");
    }
}
