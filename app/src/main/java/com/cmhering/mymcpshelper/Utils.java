package com.cmhering.mymcpshelper;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Caleb on 10/3/2017.
 */
public class Utils {

    public static double round(double num){
        return Math.round(num * 10)/10.0;
    }

    public static String getGrade(double percent){
        if(percent >= 89.5){
            return "A";
        }else if((percent >= 79.5) && (percent < 89.5)){
            return "B";
        }else if((percent >= 69.5) && (percent < 79.5)){
            return "C";
        }else if((percent >= 59.5) && (percent < 69.5)){
            return "D";
        }else{
            return "E";
        }
    }

    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

    public static void setColor(TextView tv){
        if(tv.getText().equals("A")){
            tv.setTextColor(Color.GREEN);
        }else if(tv.getText().equals("B")){
            tv.setTextColor(Color.BLUE);
        }else if(tv.getText().equals("C")){
            tv.setTextColor(Color.YELLOW);
        }else{
            tv.setTextColor(Color.RED);
        }
    }

    public static int getIndex(ArrayList<String> list, String arg){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).contains(arg)){
                return i;
            }
        }
        return -1;
    }
}
