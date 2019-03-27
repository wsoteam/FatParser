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
            getURLsOneLetter("А");
            return null;

        }

        @Override
        protected void onPostExecute(ArrayList<ItemOfGlobalBase> globalBases) {
            super.onPostExecute(globalBases);
        }

        private ArrayList<String> getTitles(String url) {
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

        private ArrayList<String> getURLsOneLetter(String letter) {
            String firstPartUrl = "https://www.fatsecret.ru/Default.aspx?pa=brands&pg=";
            String secondPartUrl = "&f=" + letter + "&t=1";
            String enterUrl = firstPartUrl + "0" + secondPartUrl;
            ArrayList<String> urlsPages = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(enterUrl).userAgent(USER_AGENT).get();
                Elements elements = doc.select("div.searchResultSummary");
                int countRow = Integer.parseInt(elements.get(0).html().split(" ")[5]);
                int countPageCurrentLetter = countRow / 20;
                if (countRow % 20 > 0) {
                    countPageCurrentLetter += 1;
                }
                for (int i = 0; i < countPageCurrentLetter; i++) {
                    urlsPages.add(firstPartUrl + String.valueOf(i) + secondPartUrl);
                    Log.e("LOL", urlsPages.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return urlsPages;
        }

    }
}
