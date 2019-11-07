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

public class InseminationAdapter extends ArrayAdapter {
    private final Activity context;
    private final Integer[] cattleIds;
    private final String[] inseminationDates;

    public InseminationAdapter(Activity context,Integer[] cattleIds,String[] inseminationDates) {
        super(context,R.layout.activity_insemination_list_view,inseminationDates);
        this.context = context;
        this.cattleIds = cattleIds;
        this.inseminationDates = inseminationDates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_insemination_list_view,null,true);
        TextView cattleId = view.findViewById(R.id.cattleId);
        TextView inseminationDate = view.findViewById(R.id.inseminationDate);
        cattleId.setText(cattleIds[position].toString());
        inseminationDate.setText(inseminationDates[position]);

        return view;
    }
}
