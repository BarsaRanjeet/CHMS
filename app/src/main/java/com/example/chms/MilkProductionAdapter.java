package com.example.chms;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MilkProductionAdapter extends ArrayAdapter {
    private final Activity context;
    private final String[] dates;
    private final String[] milkQuantities;

    public MilkProductionAdapter(Activity context,String[] dates,String[] milkQuantities) {
        super(context,R.layout.activity_milk_production_view,dates);
        this.context = context;
        this.dates = dates;
        this.milkQuantities = milkQuantities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_milk_production_view,null,true);
        TextView milkDate = view.findViewById(R.id.milkDate);
        TextView milkQuantity = view.findViewById(R.id.milkQuantity);
        milkDate.setText(dates[position]);
        milkQuantity.setText(milkQuantities[position]);

        return view;
    }
}
