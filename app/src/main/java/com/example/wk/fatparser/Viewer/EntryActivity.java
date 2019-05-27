package com.example.wk.fatparser.Viewer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wk.fatparser.POJOs.AllOwner;
import com.example.wk.fatparser.POJOs.Food;
import com.example.wk.fatparser.POJOs.Global;
import com.example.wk.fatparser.POJOs.Owner;
import com.example.wk.fatparser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class EntryActivity extends AppCompatActivity {
    RecyclerView rvMainList;
    final String FILENAME_SD = "fileSD";
    int index = 0;
    Button btnLoad, btnFin;
    static String[] ABC = new String[]{"А", "Б", "Б1", "В", "В1", "Г", "Ё", "Ж", "З",
            "И", "Й", "К", "К1", "К2", "Л",
            "М", "М1", "М2", "Н", "О", "П", "П1", "П2", "Р", "Р1", "С", "С1", "С2", "С3", "С4", "С5", "С6", "С7", "С8",
            "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    Global global = new Global();
    List<AllOwner> letters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        global.setName("sdf");
        rvMainList = findViewById(R.id.rvMainList);
        rvMainList.setLayoutManager(new LinearLayoutManager(this));
        rvMainList.setAdapter(new ItemAdapter(ABC));

        btnLoad = findViewById(R.id.btnLoad);
        btnFin = findViewById(R.id.btnFin);
        Log.e("LOL", String.valueOf(ABC.length));

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global = createGlobal(readFile());
                replacePercents(global);
            }
        });


        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadToFB();
            }
        });


    }

    private void replacePercents(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food.getPercentProteins() != null) {
                        String[] arr = food.getPercentProteins().split("%");
                        String newPP;
                        newPP = arr[0];
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPercentProteins(newPP);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getPercentProteins());
                    }
                    if (food.getPercentFats() != null) {
                        String[] arr = food.getPercentFats().split("%");
                        String newPF;
                        newPF = arr[0];
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPercentFats(newPF);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getPercentFats());
                    }
                    if (food.getPercentCarbohydrates() != null) {
                        String[] arr = food.getPercentCarbohydrates().split("%");
                        String newPC;
                        newPC = arr[0];
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPercentCarbohydrates(newPC);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getPercentCarbohydrates());
                    }

                }
            }
        }
        Log.e("LOL", "END checkAllFields" + count);
        writeInFile(getJson(globalCur));
    }

    private void replaceFats(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food.getSaturatedFats() != null) {
                        String[] arr = food.getSaturatedFats().split("г");
                        String newSF;
                        if (arr[0].contains(",")) {
                            newSF = arr[0].replace(",", ".");
                        } else {
                            newSF = arr[0];
                        }
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setSaturatedFats(newSF);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getSaturatedFats());
                    }

                    if (food.getMonoUnSaturatedFats() != null) {
                        String[] arr = food.getMonoUnSaturatedFats().split("г");
                        String newMUS;
                        if (arr[0].contains(",")) {
                            newMUS = arr[0].replace(",", ".");
                        } else {
                            newMUS = arr[0];
                        }
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setMonoUnSaturatedFats(newMUS);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getMonoUnSaturatedFats());
                    }

                    if (food.getPolyUnSaturatedFats() != null) {
                        String[] arr = food.getPolyUnSaturatedFats().split("г");
                        String newPUS;
                        if (arr[0].contains(",")) {
                            newPUS = arr[0].replace(",", ".");
                        } else {
                            newPUS = arr[0];
                        }
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPolyUnSaturatedFats(newPUS);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getPolyUnSaturatedFats());
                    }

                }
            }
        }
        Log.e("LOL", "END checkAllFields" + count);
        writeInFile(getJson(globalCur));
    }

    private void replaceFat(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    String[] arr = food.getFats().split("г");
                    String newFat;
                    if (arr[0].contains(",")) {
                        newFat = arr[0].replace(",", ".");
                    } else {
                        newFat = arr[0];
                    }
                    //Log.e("LOL", newEnergy);
                    globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setFats(newFat);
                    //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getFats());
                }
            }
        }
        Log.e("LOL", "END checkAllFields" + count);
        writeInFile(getJson(globalCur));
    }

    private void replaceFewFields(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food.getSugar() != null) {
                        String[] arr = food.getSugar().split("г");
                        String newSugar;
                        if (arr[0].contains(",")) {
                            newSugar = arr[0].replace(",", ".");
                        } else {
                            newSugar = arr[0];
                        }
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setSugar(newSugar);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getSugar());
                    }

                    if (food.getCholesterol() != null) {
                        String[] arr = food.getCholesterol().split("мг");
                        String newCholesterol;
                        if (arr[0].contains(",")) {
                            newCholesterol = arr[0].replace(",", ".");
                        } else {
                            newCholesterol = arr[0];
                        }
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setCholesterol(newCholesterol);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getCholesterol());
                    }

                    if (food.getCellulose() != null) {
                        String[] arr = food.getCellulose().split("г");
                        String newCell;
                        if (arr[0].contains(",")) {
                            newCell = arr[0].replace(",", ".");
                        } else {
                            newCell = arr[0];
                        }
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setCellulose(newCell);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getCellulose());
                    }

                    if (food.getSodium() != null) {
                        String[] arr = food.getSodium().split("мг");
                        String newSod;
                        if (arr[0].contains(",")) {
                            newSod = arr[0].replace(",", ".");
                        } else {
                            newSod = arr[0];
                        }
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setSodium(newSod);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getSodium());
                    }

                    if (food.getPottassium() != null) {
                        String[] arr = food.getPottassium().split("мг");
                        String newPottasium;
                        if (arr[0].contains(",")) {
                            newPottasium = arr[0].replace(",", ".");
                        } else {
                            newPottasium = arr[0];
                        }
                        //Log.e("LOL", newEnergy);
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPottassium(newPottasium);
                        //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getPottassium());
                    }
                }
            }
        }
        Log.e("LOL", "END checkAllFields" + count);
        writeInFile(getJson(globalCur));
    }

    private void replaceCarbo(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    String[] arr = food.getCarbohydrates().split("г");
                    String newCarbo;
                    if (arr[0].contains(",")) {
                        newCarbo = arr[0].replace(",", ".");
                    } else {
                        newCarbo = arr[0];
                    }
                    //Log.e("LOL", newEnergy);
                    globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setCarbohydrates(newCarbo);
                    //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getCarbohydrates());
                }
            }
        }
        Log.e("LOL", "END checkAllFields" + count);
        writeInFile(getJson(globalCur));
    }

    private void replaceProt(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    String[] arr = food.getProteins().split("г");
                    String newProt;
                    if (arr[0].contains(",")) {
                        newProt = arr[0].replace(",", ".");
                    } else {
                        newProt = arr[0];
                    }
                    //Log.e("LOL", newEnergy);
                    globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setProteins(newProt);
                    //Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getProteins());
                }
            }
        }
        Log.e("LOL", "END checkAllFields" + count);
        writeInFile(getJson(globalCur));
    }


    private void replaceKcal(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    String[] arr = food.getCalories().split("ккал");
                    String newEnergy;
                    if (arr[0].contains(",")) {
                        newEnergy = arr[0].replace(",", ".");
                    } else {
                        newEnergy = arr[0];
                    }
                    //Log.e("LOL", newEnergy);
                    globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setCalories(newEnergy);
                }
            }
        }
        Log.e("LOL", "END checkAllFields" + count);
        writeInFile(getJson(globalCur));
    }

    private void replaceEnergy(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food.getKilojoules().equals("0 ")) {
                        count += 1;
                    }
                    /*String[] arr = food.getKilojoules().split("кДж");
                    String newEnergy;
                    if (arr[0].contains(",")) {
                        newEnergy = arr[0].replace(",", ".");
                    } else {
                        newEnergy = arr[0];
                    }
                    globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setKilojoules(newEnergy);*/
                }
            }
        }
        Log.e("LOL", "END checkAllFields" + count);
        //writeInFile(getJson(globalCur));
    }

    private void checkPortion(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    count += 1;
                    double port = 0;
                    if (food.getPortion().contains("г")) {
                        String[] portionG = food.getPortion().split("г");
                        port = Double.parseDouble(portionG[0]);
                    } else if (food.getPortion().contains("мл")) {
                        String[] portionML = food.getPortion().split("мл");
                        port = Double.parseDouble(portionML[0]);
                    } else {
                        Log.e("LOL", "piz");
                    }

                    Log.e("LOL", String.valueOf(port));
                }
            }
        }
        Log.e("LOL", "END viewProc" + count);
        //writeInFile(getJson(globalCur));
    }

    private void replaceDot(Global global) {
        Log.e("LOL", "Start viewProc");
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food.getPortion().contains(",")) {
                        count += 1;
                        String port = food.getPortion().replace(",", ".");
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion(port);
                    }
                }
            }
        }
        Log.e("LOL", "END viewProc" + count);
        writeInFile(getJson(globalCur));
    }

    private void deleteUnxpected(Global global) {
        Log.e("LOL", "Start viewProc");
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food.getPortion().contains("500 г торта)") || food.getPortion().contains("(0,816 г)")) {
                        count += 1;
                        //globalCur.getLetters().get(i).getOwners().get(j).getFoods().remove(k);
                    }
                }
            }
        }
        Log.e("LOL", "END viewProc" + count);
        //writeInFile(getJson(globalCur));
    }



    /*private void checkPercent(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food.getPortion().contains(",")){
                        String port = food.getPortion().replace("," , ".");
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion(port);
                        Log.e("LOL", globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).getPortion());
                    }
                }
            }
        }
        Log.e("LOL", "END viewProc" + count);
        writeInFile(getJson(globalCur));
    }*/

    private void viewProc(Global global) {
        Log.e("LOL", "Start viewProc");
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    count += 1;
                    String[] arr = food.getPercentCarbohydrates().split(",");
                    globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPercentFats(arr[0].split(">")[1].split("<")[0]);
                    globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPercentProteins(arr[2].split("\\.")[0]);
                    globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPercentCarbohydrates(arr[1]);
                }
            }
        }
        Log.e("LOL", "END viewProc" + count);
        //writeInFile(getJson(globalCur));
    }

    private void viewUnTypicalPortions(Global global) {
        Global globalCur = global;
        int count = 0;
        ArrayList<String> typicalPortion = new ArrayList<>();
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    /*if(food.getPortion().contains("100 г")){
                        //Log.e("LOL", food.getPortion());
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion("100г");
                    }*/
                    /*if(food.getPortion().contains("100 мл")){
                        //Log.e("LOL", food.getPortion());
                        globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion("100мл");
                    }*/
                    /*if (!food.getPortion().equals("100г") && !food.getPortion().equals("100мл")) {
                        if (food.getPortion().contains(" 1 г") && !food.getPortion().contains(")")) {
                            globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion("1г");
                        }
                    }*/

                    /*if (!food.getPortion().equals("100г") && !food.getPortion().equals("100мл") && !food.getPortion().equals("1г")
                            && !food.getPortion().equals("1мл")) {
                        if (food.getPortion().contains("(") && food.getPortion().contains(")")) {
                            String[] arr = food.getPortion().split("\\(");
                            String[] arrr = arr[1].split("\\)");
                            //Log.e("LOL", arrr[0]);
                            if (arrr[0].length() < 7) {
                                globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion(arrr[0].replace(" ", ""));
                                //Log.e("LOL", arrr[0].replace(" ", ""));
                            }
                        }
                    }*/
                    /*if (!food.getPortion().equals("100г") && !food.getPortion().equals("100мл") && !food.getPortion().equals("1г")
                            && !food.getPortion().equals("1мл")) {
                        if (food.getPortion().contains("Размер Порции") && food.getPortion().contains("1/2 г")) {
                            globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion("0.5г");
                        }
                    }*/
                    /*if (!food.getPortion().equals("100г") && !food.getPortion().equals("100мл") && !food.getPortion().equals("1г")
                            && !food.getPortion().equals("1мл")) {
                        if (food.getPortion().contains("Размер Порции") && food.getPortion().contains("(1 / 4 фунта)")) {
                            globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion("113г");
                        }
                    }*/
                    /*if (!food.getPortion().equals("100г") && !food.getPortion().equals("100мл") && !food.getPortion().equals("1г")
                            && !food.getPortion().equals("1мл")) {
                        if (food.getPortion().contains("Размер Порции") && food.getPortion().contains("2 г")) {
                            globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion("2г");
                        }
                    }*/
                    /*if (!food.getPortion().equals("100г") && !food.getPortion().equals("100мл") && !food.getPortion().equals("1г")
                            && !food.getPortion().equals("1мл")) {
                        if (food.getPortion().contains("Размер Порции") && food.getPortion().contains("5 г")) {
                            globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion("5г");
                        }
                    }*/

                    /*if (!food.getPortion().equals("100г") && !food.getPortion().equals("100мл") && !food.getPortion().equals("1г")
                            && !food.getPortion().equals("1мл")) {
                        if (food.getPortion().contains("Размер Порции") && food.getPortion().contains("10 г")) {
                            globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k).setPortion("10г");
                        }
                    }*/

                    if (!food.getPortion().equals("100г") && !food.getPortion().equals("100мл") && !food.getPortion().equals("1г")
                            && !food.getPortion().equals("1мл")) {
                        if (food.getPortion().contains("Размер Порции")) {
                            globalCur.getLetters().get(i).getOwners().get(j).getFoods().remove(k);
                        }
                    }

                    /*if (!food.getPortion().contains("100г") && !food.getPortion().contains("100мл") &&
                    !food.getPortion().contains("100 г") && !food.getPortion().contains("1 г") &&
                            !food.getPortion().contains("г)") && !food.getPortion().contains("мл)") &&
                            !food.getPortion().contains("1 мл") && !food.getPortion().contains("100 мл")
                            && !food.getPortion().contains("2 г") && !food.getPortion().contains("5 г")
                            && !food.getPortion().contains("500 г") && !food.getPortion().contains("10 г")
                            && !food.getPortion().contains("1 / 4 фунта")) {
                        count += 1;
                        Log.e("LOL", food.getPortion());
                        //globalCur.getLetters().get(i).getOwners().get(j).getFoods().remove(k);
                    }*/
                }
            }
        }
        Log.e("LOL", "END viewUnTypicalPortions" + count);
        //writeInFile(getJson(globalCur));
    }

    private void viewTagsNames(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food.getName().contains(">") || food.getName().contains("<")) {
                        String[] names = food.getName().split(">");
                        String[] normName = names[1].split("<");
                        food.setName(normName[0]);
                        Log.e("LOL", food.getName());
                    }
                }
            }
        }
        Log.e("LOL", "END viewTagsNames" + String.valueOf(count));
        //writeInFile(getJson(globalCur));
    }

    private void deleteEmptyNamesOfFood(Global global) {
        Global globalCur = global;
        int count = 0;
        for (int i = 0; i < globalCur.getLetters().size(); i++) {
            for (int j = 0; j < globalCur.getLetters().get(i).getOwners().size(); j++) {
                count += globalCur.getLetters().get(i).getOwners().get(j).getFoods().size();
                for (int k = 0; k < globalCur.getLetters().get(i).getOwners().get(j).getFoods().size(); k++) {
                    Food food = globalCur.getLetters().get(i).getOwners().get(j).getFoods().get(k);
                    if (food == null) {
                        Log.e("LOL", "LOL");
                        //globalCur.getLetters().get(i).getOwners().get(j).getFoods().remove(k);
                    }
                }
            }
        }
        Log.e("LOL", "END deleteEmptyNamesOfFood" + String.valueOf(count));
        //writeInFile(getJson(globalCur));
    }

    private Global createGlobal(String readFile) {
        Global global = new Global();
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Global> globalJsonAdapter = moshi.adapter(Global.class);
        try {
            global = globalJsonAdapter.fromJson(readFile);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("LOL", "fuck read json");
        }
        return global;
    }

    private void deleteEmptyOwners(Global global) {
        Global globalNew = new Global();
        globalNew.setName("global");
        List<AllOwner> allOwners = new ArrayList<>();
        Log.e("LOL", "Start deleting");
        for (int i = 0; i < global.getLetters().size(); i++) {
            List<Owner> owners = new ArrayList<>();
            for (int j = 0; j < global.getLetters().get(i).getOwners().size(); j++) {
                if (global.getLetters().get(i).getOwners().get(j).getFoods() != null) {
                    owners.add(global.getLetters().get(i).getOwners().get(j));
                } else {
                    Log.e("LOL", global.getLetters().get(i).getOwners().get(j).getName());
                }
            }
            AllOwner allOwner = new AllOwner(global.getLetters().get(i).getName(), owners);
            allOwners.add(allOwner);
        }
        //globalNew.setLetters(allOwners);
        check(globalNew);
        //writeInFile(getJson(globalNew));
    }

    private String getJson(Global globalNew) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Global> globalJsonAdapter = moshi.adapter(Global.class);
        String json = globalJsonAdapter.toJson(globalNew);
        return json;
    }

    private void loadToFB() {
        Log.e("LOL", "Start loading");
        global.setName("GlobRU");
        global.setLetters(letters);
        letters = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GLOB");
        myRef.setValue(global);

        Log.e("LOL", "Fin loading");
    }

    private void unionAllLetters() {
        final int max = ABC.length;
        Log.e("LOL", ABC[index] + " start");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ABC[index]);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AllOwner allOwner = dataSnapshot.getValue(AllOwner.class);
                allOwner.setName(ABC[index]);
                letters.add(allOwner);
                index += 1;
                if (index < ABC.length) {
                    unionAllLetters();
                } else {
                    Log.e("LOL", "ALL FIN -- " + String.valueOf(letters.size()));
                    saveAlfaBet();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveAlfaBet() {
        //check();
        /*Global global = new Global("globalRus", letters);
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Global> globalJsonAdapter = moshi.adapter(Global.class);
        String json = globalJsonAdapter.toJson(global);
        Log.e("LOL", json);
        writeInFile(json);*/
    }

    private void writeInFile(String json) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("FoodDB", MODE_PRIVATE)));
            // пишем данные
            bw.write(json);
            // закрываем поток
            bw.close();
            Log.e("LOL", "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile() {
        Log.e("LOL", "start read");
        String str = "";
        String json = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput("FoodDB")));
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                json += str;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("LOL", "fin read");
        return json;
    }

    private void check(Global global) {
        int count = 0;
        for (int i = 0; i < global.getLetters().size(); i++) {
            try {
                for (int j = 0; j < global.getLetters().get(i).getOwners().size(); j++) {
                    if (global.getLetters().get(i).getOwners().get(j).getFoods() != null) {
                        try {
                            count += global.getLetters().get(i).getOwners().get(j).getFoods().size();
                        } catch (Exception e) {
                            Log.e("LOL", global.getLetters().get(i).getOwners().get(j).getName() + "ex");
                        }
                    } else {
                        Log.e("LOL", global.getLetters().get(i).getOwners().get(j).getName() + " --empty");
                    }
                }
            } catch (Exception e) {
                Log.e("LOL", global.getLetters().get(i).getName());
            }

        }
        Log.e("LOL", String.valueOf(count));
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public ItemHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_list, viewGroup, false));
            textView = itemView.findViewById(R.id.tvData);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(EntryActivity.this, GroupsActivity.class).putExtra(Config.TAG_LETTER, textView.getText().toString()));
        }

        public void bind(String letter) {
            textView.setText(letter);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        String[] letters;

        public ItemAdapter(String[] letters) {
            this.letters = letters;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(EntryActivity.this);
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.bind(letters[position]);
        }

        @Override
        public int getItemCount() {
            return letters.length;
        }
    }
}
