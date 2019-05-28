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
import android.widget.TextView;

import com.example.wk.fatparser.POJOs.AllOwner;
import com.example.wk.fatparser.POJOsForConvert.CAllOwner;
import com.example.wk.fatparser.R;
import com.example.wk.fatparser.Singleton.DataHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupsActivity extends AppCompatActivity {
    RecyclerView rvGroupsList;
    String currentLetter = new String();
    CAllOwner allOwner = new CAllOwner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        allOwner = DataHolder.getcGlobal().getLetters().get(getIntent().getIntExtra(Config.TAG_LETTER, 0));
        Log.e("LOL", String.valueOf(allOwner.getName()));
        rvGroupsList = findViewById(R.id.rvGroupsList);
        rvGroupsList.setLayoutManager(new LinearLayoutManager(this));
        rvGroupsList.setAdapter(new ItemAdapter());

    }

    /*private void updateUI() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(currentLetter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allOwner = dataSnapshot.getValue(AllOwner.class);
                rvGroupsList.setAdapter(new ItemAdapter());
                setTitle(currentLetter + " - " + String.valueOf(allOwner.getOwners().size()) + " - " + getCountProducts(allOwner));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private String getCountProducts(AllOwner allOwner) {
        int count = 0;
        for (int i = 0; i < allOwner.getOwners().size(); i++) {
            if (allOwner.getOwners().get(i).getFoods() != null) {
                count += allOwner.getOwners().get(i).getFoods().size();
            }
        }
        return String.valueOf(count);
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
            startActivity(new Intent(GroupsActivity.this, DetailListActivity.class)
                    .putExtra(Config.TAG_BREND, getAdapterPosition()).putExtra(Config.TAG_LETTER, getIntent().getIntExtra(Config.TAG_LETTER, 0)));
        }

        public void bind(int position) {
            textView.setText(allOwner.getOwners().get(position).getName());
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(GroupsActivity.this);
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return allOwner.getOwners().size();
        }
    }
}
