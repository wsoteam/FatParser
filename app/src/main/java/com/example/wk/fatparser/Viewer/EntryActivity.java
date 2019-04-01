package com.example.wk.fatparser.Viewer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wk.fatparser.R;

public class EntryActivity extends AppCompatActivity {
    RecyclerView rvMainList;
    static String[] ABC = new String[]{"У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Э", "Ю", "Я"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        rvMainList = findViewById(R.id.rvMainList);
        rvMainList.setLayoutManager(new LinearLayoutManager(this));
        rvMainList.setAdapter(new ItemAdapter(ABC));
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
