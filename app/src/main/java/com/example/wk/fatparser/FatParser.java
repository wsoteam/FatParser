package com.example.wk.fatparser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.wk.fatparser.POJOs.AllOwner;
import com.example.wk.fatparser.POJOs.Food;
import com.example.wk.fatparser.POJOs.Owner;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FatParser extends AppCompatActivity {

    static String USER_AGENT = "Mozilla";
    static String[] ABC = new String[]{"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П", "Р", "С",
            "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я"};
    static int size = 0;
    static ArrayList<String> allTitles = new ArrayList<>();
    final static String FLAG_NULL_BREND = "-ъ0";
    final static String FLAG_NOT_NULL_BREND = "+ъ0";
    final static String CURRENT_LETTER = "С"; //C,


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_parser);
        new AsyncLoad().execute();
    }


    public static class AsyncLoad extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AllOwner allOwner = new AllOwner();
            allOwner.setName("OWNERS");
            ArrayList<Owner> owners = new ArrayList<>();
            int count = 0;
            ArrayList<String> array = getURLsOneLetter(CURRENT_LETTER);
            ArrayList<String> urlsOwners = fromTitleToUrls(getTitlesOneLetter(array));
            Log.e("LOL", String.valueOf(urlsOwners.size()));
            ArrayList<String> titlesOwners = getTitlesOneLetter(array);
            urlsOwners.remove(0);
            titlesOwners.remove(0);
            titlesOwners.remove(0);
            urlsOwners.remove(0);
            for (int j = 0; j < 101; j++) {
                try {
                    Owner owner = new Owner();
                    owner.setName(titlesOwners.get(j));
                    owner.setUrl(urlsOwners.get(j));
                    ArrayList<Food> foods = new ArrayList<>();
                    ArrayList<String> allDetailUrl = getProducts(getUrlsPagesListProducts(urlsOwners.get(j)), titlesOwners.get(j));
                    Log.e("LOL", "start");
                    for (int i = 0; i < allDetailUrl.size(); i++) {
                        try {
                            if (getDetailProduct(allDetailUrl.get(i)) != null) {
                                foods.add(getDetailProduct(allDetailUrl.get(i)));
                                count += 1;
                            }
                        } catch (Exception e) {
                            Log.e("LOL", "KEK");
                            Log.e("LOL1", "KEK");
                        }
                    }
                    Log.e("LOL", "fin - " + String.valueOf(j));
                    owner.setFoods(foods);
                    owners.add(owner);
                } catch (Exception e) {
                    Log.e("Empty", String.valueOf(j) + titlesOwners.get(j));
                }
            }

            allOwner.setOwners(owners);


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(CURRENT_LETTER );

            myRef.setValue(allOwner);


            //Log.e("LOL", String.valueOf(count));
            //Log.e("LOL", "d");

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

        private ArrayList<String> getProducts(ArrayList<String> urlsPageProducts, String brend) {
            String BAG_LINK = "Diary.aspx?";
            int sizeWithBrend = 15;
            ArrayList<String> urlDetailProduct = new ArrayList<>();
            String firstPartUrl = "https://www.fatsecret.ru";
            try {
                for (int j = 0; j < urlsPageProducts.size(); j++) {
                    Document doc = Jsoup.connect(urlsPageProducts.get(j)).userAgent(USER_AGENT).get();
                    Elements elements = doc.select("td.borderBottom");
                    for (int i = 0; i < elements.size(); i++) {
                        if (!elements.get(i).html().contains(BAG_LINK)) {
                            if (elements.get(i).html().contains(brend)) {
                                urlDetailProduct.add(FLAG_NOT_NULL_BREND + firstPartUrl + elements.get(i).html().split("\"")[3]);
                                Log.e("LOL", firstPartUrl + elements.get(i).html().split("\"")[3]);
                            } else {
                                if (elements.get(i).html().split("\"").length < sizeWithBrend) {
                                    urlDetailProduct.add(FLAG_NULL_BREND + firstPartUrl + elements.get(i).html().split("\"")[3]);
                                    Log.e("LOL", firstPartUrl + elements.get(i).html().split("\"")[3]);
                                }
                            }
                        } else {
                            Log.e("BAG", firstPartUrl + elements.get(i).html().split("\"")[3]);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return urlDetailProduct;
        }

        private Food getDetailProduct(String urlPageDetailProducts) {
            String brandFlag = urlPageDetailProducts.substring(0, 3);
            String urlWithOutFlag = urlPageDetailProducts.substring(3);
            boolean isHaveBrand = true;

            if (brandFlag.equals(FLAG_NULL_BREND)) {
                isHaveBrand = false;
            }

            Food food = null;
            ArrayList<String> urlDetailProduct = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(urlWithOutFlag).userAgent(USER_AGENT).get();
                if (isTypicalProduct(doc)) {
                    food = getFood(doc, isHaveBrand);
                } else {
                    if (isContainsWeightOrVolume(doc)) {
                        food = getFood(doc, isHaveBrand);
                    } else {
                        if (getCorrectDocument100gram(doc) != null) {
                            food = getFood(getCorrectDocument100gram(doc), isHaveBrand);
                        }

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return food;
        }

        private boolean isContainsWeightOrVolume(Document doc) {
            String rightBracket = "(", leftBracket = ")", weight = "г", volume = "мл";
            Elements elements = doc.select("td.label");
            if (elements.get(0).html().contains(rightBracket) && elements.get(0).html().contains(leftBracket)
                    && (elements.get(0).html().contains(weight) || elements.get(0).html().contains(volume))) {
                return true;
            } else {
                return false;
            }
        }

        private Food getFood(Document doc, boolean isHaveBrand) {
            Food food = new Food();
            Log.e("LOL", "pick");
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


            Elements titles = doc.select("h1");
            food.setName(titles.html());
            if (isHaveBrand) {
                Elements manufacture = doc.select("h2").select("a");
                food.setBrend(manufacture.html());
            } else {
                food.setBrend("");
            }
            Elements percent = doc.select("div.factPanel");
            Elements percents = percent.select("table");

            food.setPercentCarbohydrates(percents.get(1).select("td").get(1).html());


            Elements elements = doc.select("div.nutpanel");
            Elements elementRows = elements.get(0).select("tr");

            food.setPortion(elementRows.get(1).select("td").html());
            food.setKilojoules(elementRows.get(4).select("td").get(1).select("b").html());
            food.setCalories(elementRows.get(5).select("td").get(1).select("b").html());

            for (int i = 6; i < elementRows.size(); i++) {
                if (elementRows.get(i).html().contains(proteins)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setProteins(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(carbohydrates)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setCarbohydrates(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(sugar)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setSugar(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(fats)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setFats(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(saturatedFats)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setSaturatedFats(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(monoUnSaturatedFats)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setMonoUnSaturatedFats(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(polyUnSaturatedFats)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setPolyUnSaturatedFats(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(cholesterol)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setCholesterol(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(cellulose)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setCellulose(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(sodium)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setSodium(elementRows.get(i).select("td").get(countTd).html());
                }

                if (elementRows.get(i).html().contains(pottassium)) {
                    int countTd = elementRows.get(i).select("td").size() - 1;
                    food.setPottassium(elementRows.get(i).select("td").get(countTd).html());
                }
            }

            return food;
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

        private Document getCorrectDocument100gram(Document doc) {
            Document correctDoc = null;
            String firstPartUrl = "https://www.fatsecret.ru";
            String weight = "100 г", volume = "100 мл";
            Elements elements = doc.select("td.borderBottom");
            int positionElementWithCorrectURL = -1;
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).html().contains(weight) || elements.get(i).html().contains(volume)) {
                    positionElementWithCorrectURL = i;
                }
            }
            String correctUrl = firstPartUrl + elements.get(positionElementWithCorrectURL).html().split("\"")[9];
            try {
                correctDoc = Jsoup.connect(correctUrl).userAgent(USER_AGENT).get();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("KEK", "PICK");
                Log.e("KEK", e.getMessage());
            }
            return correctDoc;
        }


    }
}
