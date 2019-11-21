package com.example.chms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HeatDetailsAdapter extends ArrayAdapter {
    private Activity context;
    private String[] probableHeatDates;
    private String[] actualHeatDates;
    private String[] inseminations;

    TextView probableHeatDate,actualHeatDate,insemination;
    public HeatDetailsAdapter(Activity context, String[] probableHeatDates, String[] actualHeatDates, String[] inseminations) {
        super(context,R.layout.activity_heat_details_view,probableHeatDates);
        this.context = context;
        this.probableHeatDates = probableHeatDates;
        this.actualHeatDates = actualHeatDates;
        this.inseminations = inseminations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_heat_details_view,null,true);
        probableHeatDate = (TextView)view.findViewById(R.id.probable_heat_date);
        actualHeatDate = (TextView)view.findViewById(R.id.actual_heat_date);
        insemination = (TextView)view.findViewById(R.id.insemination_status);
        probableHeatDate.setText(probableHeatDates[position]);
        actualHeatDate.setText(actualHeatDates[position]);
        insemination.setText(inseminations[position]);
        return view;
    }
}
