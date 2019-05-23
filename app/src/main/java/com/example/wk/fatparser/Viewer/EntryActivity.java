package com.example.wk.fatparser.Viewer;

import android.content.Intent;
import android.os.Environment;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.Mms.Part.FILENAME;

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
                createGlobal(readFile());
            }
        });


        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadToFB();
            }
        });


    }

    private void createGlobal(String readFile) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Global> globalJsonAdapter = moshi.adapter(Global.class);
        try {
            Global global = globalJsonAdapter.fromJson(readFile);
            check(global);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("LOL", "fuck read json");
        }
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
