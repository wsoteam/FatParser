package com.example.wk.parserdbproducts;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.wk.parserdbproducts.POJOS.ItemOfGlobalBase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FatParser extends AppCompatActivity {

    static String USER_AGENT = "Mozilla";
    static String url = "https://www.fatsecret.ru/Default.aspx?pa=brands&pg=0&f=%D0%90&t=1";
    static int firstElement = 0;
    static int lastElement = 70;
    static String TAG_DIV = "</div>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_parser);
        new AsyncLoad().execute();
    }



    public static class AsyncLoad extends AsyncTask<Void, Void, ArrayList<ItemOfGlobalBase>> {
        @Override
        protected ArrayList<ItemOfGlobalBase> doInBackground(Void... voids) {
            getTitles(url);
            return null;

        }

        @Override
        protected void onPostExecute(ArrayList<ItemOfGlobalBase> globalBases) {
            super.onPostExecute(globalBases);
        }
        private ArrayList<String> getTitles(String url){
            ArrayList<String> titlesOwners = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
                Elements elements = doc.select("h2");
                for (int i = 0; i < elements.size(); i++) {
                    titlesOwners.add(elements.get(i).html().split("\"")[3]);
                    Log.e("LOL", titlesOwners.get(i));
                }

                Log.e("LOL", "fin");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return titlesOwners;
        }

    }
}
