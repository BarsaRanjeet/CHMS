package com.example.chms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CattleListAdapter extends ArrayAdapter
{
    private final Activity context;
    private final Integer[] cattleImages;
    private final String[] cattleNames;
    private final String[] cattleStates;

    public CattleListAdapter(Activity context, Integer[] cattleImages, String[] cattleNames, String[] cattleStates)
    {
        super(context,R.layout.activity_cattle_list_view,cattleNames);
        this.context = context;
        this.cattleImages = cattleImages;
        this.cattleNames = cattleNames;
        this.cattleStates = cattleStates;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_cattle_list_view,null,true);

        ImageView cattleImage = view.findViewById(R.id.cattleImage);
        TextView cattleName = view.findViewById(R.id.cattleName);
        TextView cattleState = view.findViewById(R.id.cattleState);


        cattleImage.setImageResource(cattleImages[position]);
        cattleName.setText(cattleNames[position]);
        cattleState.setText(cattleStates[position]);

        return view;
    }
}
