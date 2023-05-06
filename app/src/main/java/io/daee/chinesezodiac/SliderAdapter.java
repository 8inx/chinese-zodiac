package io.daee.chinesezodiac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private ArrayList<String> list;


    public SliderAdapter(ArrayList<String> itemList) {
        this.list = itemList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        String item = list.get(position);
        holder.textView.setText(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class SliderViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.triviaText);
        }
    }
}
