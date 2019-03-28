package com.example.wk.parserdbproducts;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.wk.parserdbproducts.POJOs.Food;

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
    static ArrayList<String> allTitles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_parser);
        new AsyncLoad().execute();
    }


    public static class AsyncLoad extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            /*ArrayList<String> array = getURLsOneLetter("Б");
            ArrayList<String> urlsOwners = fromTitleToUrls(getTitlesOneLetter(array));
            getProducts(getUrlsPagesListProducts(urlsOwners.get(0)));*/

            getDetailProduct("");

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

        private ArrayList<String> getProducts(ArrayList<String> urlsPageProducts) {
            ArrayList<String> urlDetailProduct = new ArrayList<>();
            String firstPartUrl = "https://www.fatsecret.ru";
            try {
                for (int j = 0; j < urlsPageProducts.size(); j++) {
                    Document doc = Jsoup.connect(urlsPageProducts.get(j)).userAgent(USER_AGENT).get();
                    Elements elements = doc.select("td.borderBottom");
                    for (int i = 0; i < elements.size(); i++) {
                        urlDetailProduct.add(firstPartUrl + elements.get(i).html().split("\"")[3]);
                        Log.e("LOL", firstPartUrl + elements.get(i).html().split("\"")[3]);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return urlDetailProduct;
        }

        private void getDetailProduct(String urlPageDetailProducts) {
            ArrayList<String> urlDetailProduct = new ArrayList<>();
            String url = "https://www.fatsecret.ru/калории-питание/общий/ПП-Панкейки";
            String url1 = "https://www.fatsecret.ru/калории-питание/zatecky-gus/Пиво/100мл";
            String url2 = "https://www.fatsecret.ru/%D0%BA%D0%B0%D0%BB%D0%BE%D1%80%D0%B8%D0%B8-%D0%BF%D0%B8%D1%82%D0%B0%D0%BD%D0%B8%D0%B5/%D0%BE%D0%B1%D1%89%D0%B8%D0%B9/%D0%A2%D0%BE%D1%80%D1%82?portionid=52579&portionamount=100,000";

            try {
                Document doc = Jsoup.connect(url1).userAgent(USER_AGENT).get();
                if (isTypicalProduct(doc)) {
                    getFood(doc);
                }

                //getCorrectUrl100gramm(doc);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void getFood(Document doc) {
            Food food = new Food();
            String proteins = "Белки";
            String carbohydrates = "Углеводы";
            String sugar = "Сахар";
            String fats = "<b>Жиры</b>";
            String saturatedFats = "Насыщенные Жиры";
            String monoUnSaturatedFats = "Мононенасыщенные Жиры";
            String polyUnSaturatedFats = "Полиненасыщенные Жиры";
            String cholesterol = "Холестерин";
            String cellulose = "Клетчатка";
            String sodium = "Натрий";
            String pottassium = "Калий";


            String percentCarbohydrates;
            String percentFats;
            String percentProteins;

            //Elements manufacture = doc.select("h2").select("a");
            Elements titles = doc.select("h1").select("a");
            //Log.e("LOL", manufacture.html());
            Log.e("LOL", titles.html());

            Elements elements = doc.select("div.nutpanel");
            Elements elementRows = elements.get(0).select("tr");

            food.setPortion(elementRows.get(1).select("td").html());
            food.setKilojoules(elementRows.get(4).select("td").get(1).select("b").html());
            food.setCalories(elementRows.get(5).select("td").get(1).select("b").html());

            for (int i = 6; i < elementRows.size(); i++) {
                if (elementRows.get(i).html().contains(proteins)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setProteins(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(carbohydrates)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setCarbohydrates(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(sugar)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setSugar(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(fats)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setFats(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(saturatedFats)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setSaturatedFats(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(monoUnSaturatedFats)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setMonoUnSaturatedFats(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(polyUnSaturatedFats)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setPolyUnSaturatedFats(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(cholesterol)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setCholesterol(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(cellulose)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setCellulose(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(sodium)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setSodium(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(pottassium)){
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setPottassium(elementRows.get(i).select("td").get(countTd).html());
                }
            }

        }

        private boolean isTypicalProduct(Document doc) {
            String typicalPhraseWeight = "100 мл", typicalPhraseVolume = "100 г";
            Elements elements = doc.select("td.label");
            if (elements.get(0).html().contains(typicalPhraseVolume) || elements.get(0).html().contains(typicalPhraseWeight)) {
                return true;
            } else {
                return false;
            }
        }

        private void getCorrectUrl100gramm(Document doc) {
            String weight = "100 г", volume = "100 мл";
            Elements elements = doc.select("td.borderBottom");
            int positionElementWithCorrectURL = -1;
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).html().contains(weight) || elements.get(i).html().contains(volume)) {
                    positionElementWithCorrectURL = i;
                }
            }
            String correctUrl = elements.get(positionElementWithCorrectURL).html().split("\"")[9];
            Log.e("LOL", correctUrl);

        }

        private String getStringInBrackets(String textInBrackets) {
            int positionFirstBracket = 0, positionSecondBracket = 0;
            positionFirstBracket = textInBrackets.indexOf("(") + 1;
            positionSecondBracket = textInBrackets.indexOf(")");

            return textInBrackets.substring(positionFirstBracket, positionSecondBracket);
        }

    }
}
