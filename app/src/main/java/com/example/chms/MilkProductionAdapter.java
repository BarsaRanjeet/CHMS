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
    private final String[] milkIds;
    private final String[] milkTimes;


    public MilkProductionAdapter(Activity context,String[] milkIds, String[] dates,String[] milkQuantities,String[] milkTimes) {
        super(context,R.layout.activity_milk_production_view,milkIds);
        this.context = context;
        this.dates = dates;
        this.milkQuantities = milkQuantities;
        this.milkIds = milkIds;
        this.milkTimes = milkTimes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_milk_production_view,null,true);
        TextView milkDate = view.findViewById(R.id.milk_date);
        TextView milkQuantity = view.findViewById(R.id.milk_qty);
        TextView milkId = view.findViewById(R.id.milk_id);
        TextView milkTime = view.findViewById(R.id.milk_time);
        milkDate.setText(dates[position]);
        milkQuantity.setText(milkQuantities[position]);
        milkId.setText(milkIds[position]);
        milkTime.setText(milkTimes[position]);

        return view;
    }
}
