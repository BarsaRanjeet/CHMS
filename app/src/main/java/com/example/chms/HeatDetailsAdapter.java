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
    private String[] lastHeatDates;
    private String[] probableHeatDates;
    private String[] inseminations;

    TextView lastHeatDate,probableHeatDate,insemination;
    public HeatDetailsAdapter(Activity context, String[] lastHeatDates, String[] probableHeatDates, String[] inseminations) {
        super(context,R.layout.activity_heat_details_view,lastHeatDates);
        this.context = context;
        this.lastHeatDates = lastHeatDates;
        this.probableHeatDates = probableHeatDates;
        this.inseminations = inseminations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_heat_details_view,null,true);
        lastHeatDate = (TextView)view.findViewById(R.id.last_heat_date);
        probableHeatDate = (TextView)view.findViewById(R.id.probable_heat_date);
        insemination = (TextView)view.findViewById(R.id.insemination_status);
        lastHeatDate.setText(lastHeatDates[position]);
        probableHeatDate.setText(probableHeatDates[position]);
        insemination.setText(inseminations[position]);
        return view;
    }
}
