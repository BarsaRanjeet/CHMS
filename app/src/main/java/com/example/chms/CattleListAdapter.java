package com.example.chms;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

public class CattleListAdapter extends ArrayAdapter
{
    private final Activity context;
    private final String[] cattleImages;
    private final String[] cattleNames;
    private final String[] cattleStates;

    public CattleListAdapter(Activity context, String[] cattleImages, String[] cattleNames, String[] cattleStates)
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

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        String rootDuirectory = Environment.getExternalStorageDirectory().getAbsolutePath();

        final Bitmap bitmap = BitmapFactory.decodeFile(rootDuirectory+"/chms/"+cattleImages[position], options);

        cattleImage.setImageBitmap(bitmap);
        //cattleImage.setImageResource(cattleImages[position]);
        cattleName.setText(cattleNames[position]);
        cattleState.setText(cattleStates[position]);

        return view;
    }
}
