package com.example.chms;

import android.content.Context;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HeatPrediction {
    public static Date nextHeatCalculate(Context context,Cattle cattle)
    {
        if(!cattle.getBreedingStatus().equals("Normal"))
            return null;
        Date lastHeatDate = cattle.getLastHeatDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastHeatDate);
        List<String> breeds = null;
        if(cattle.getCattleType().equals("Cow"))
        {
            breeds = Arrays.asList(context.getResources().getStringArray(R.array.cow_breed));
        }
        else if(cattle.getCattleType().equals("Buffalo"))
        {
            breeds = Arrays.asList(context.getResources().getStringArray(R.array.buffalo_breed));
        }

        int pos  = breeds.indexOf(cattle.getBreed());
        int heatPeriod = 0;
        if(cattle.getCattleType().equals("Cow"))
        {
            List<String> heatPeriods = Arrays.asList(context.getResources().getStringArray(R.array.cow_breed_avg_heat_period));
            heatPeriod = Integer.parseInt(heatPeriods.get(pos));
        }
        else if(cattle.getCattleType().equals("Buffalo"))
        {
            List<String> heatPeriods = Arrays.asList(context.getResources().getStringArray(R.array.buffalo_breed_avg_heat_period));
            heatPeriod = Integer.parseInt(heatPeriods.get(pos));
        }
        cal.add(Calendar.DATE,heatPeriod);
        return cal.getTime();
    }


}
