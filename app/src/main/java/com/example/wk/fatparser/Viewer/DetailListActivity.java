package com.example.wk.fatparser.Viewer;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wk.fatparser.POJOs.Food;
import com.example.wk.fatparser.POJOs.Owner;
import com.example.wk.fatparser.R;

public class DetailListActivity extends AppCompatActivity {
    RecyclerView rvProducts;
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        owner = (Owner) getIntent().getSerializableExtra(Config.TAG_BREND);
        setTitle(owner.getName());
        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(new ItemAdapter(owner));
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_list, viewGroup, false));
            textView = itemView.findViewById(R.id.tvData);
        }

        public void bind(Food food, int position) {
            textView.setText(String.valueOf(position + 1) + " - "+food.toString());
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        Owner owner;

        public ItemAdapter(Owner owner) {
            this.owner = owner;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(DetailListActivity.this);
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.bind(owner.getFoods().get(position), position);
        }

        @Override
        public int getItemCount() {
            return owner.getFoods().size();
        }
    }
}
