package com.example.wk.parserdbproducts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FatParser extends AppCompatActivity {

    static String USER_AGENT = "Mozilla";
    static String[] ABC = new String[]{"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П", "Р", "С",
            "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я", "*"};
    static int size = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_parser);
        new AsyncLoad().execute();
    }


    public static class AsyncLoad extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            getProducts("https://www.fatsecret.ru/калории-питание/search?q=Zatecky%20Gus&pg=0");
            return null;

        }


        //get titles owners from all pages with choised one letter
        // First page of letter A - ABC, Agriko ...
        private ArrayList<String> getTitlesOneLetter(ArrayList<String> urls) {
            ArrayList<String> titlesOwners = new ArrayList<>();
            try {
                for (int i = 0; i < urls.size(); i++) {
                    Document doc = Jsoup.connect(urls.get(i)).userAgent(USER_AGENT).get();
                    Elements elements = doc.select("h2");
                    for (int j = 0; j < elements.size(); j++) {
                        titlesOwners.add(elements.get(j).html().split("\"")[3]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return titlesOwners;
        }


        //get all urls to pages with one choised letter
        // A - url of first page of A, url of second page of A ...
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return urlsPages;
        }


        private ArrayList<String> fromTitleToUrls(ArrayList<String> titles) {
            String firstPartUrl = "https://www.fatsecret.ru/калории-питание/search?q=";
            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0; i < titles.size(); i++) {
                String url = firstPartUrl + titles.get(i);
                url.replace(" ", "+");
                urls.add(firstPartUrl + titles.get(i));
                Log.e("LOL", url);
            }
            return urls;
        }

        //get all pages with 10 items on one page for choice owner
        private ArrayList<String> getUrlsPagesListProducts(String urlOwner) {
            String secondPartUrl = "&pg=";
            ArrayList<String> urlsPages = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(urlOwner).userAgent(USER_AGENT).get();
                Elements elements = doc.select("div.searchResultSummary");
                int countRow = Integer.parseInt(elements.get(0).html().split(" ")[4]);
                int countPageCurrentLetter = countRow / 10;
                if (countRow % 10 > 0) {
                    countPageCurrentLetter += 1;
                }
                for (int i = 0; i < countPageCurrentLetter; i++) {
                    urlsPages.add(urlOwner + secondPartUrl + String.valueOf(i));
                    Log.e("LOL", urlsPages.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return urlsPages;
        }

        private ArrayList<String> getProducts(String urlPageProducts) {
            ArrayList<String> urlDetailProduct = new ArrayList<>();
            String firstPartUrl = "https://www.fatsecret.ru";
            try {
                Document doc = Jsoup.connect(urlPageProducts).userAgent(USER_AGENT).get();
                Elements elements = doc.select("td.borderBottom");
                for (int i = 0; i < elements.size(); i++) {
                    urlDetailProduct.add(firstPartUrl+ elements.get(i).html().split("\"")[3]);
                    Log.e("LOL", firstPartUrl+ elements.get(i).html().split("\"")[3]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
