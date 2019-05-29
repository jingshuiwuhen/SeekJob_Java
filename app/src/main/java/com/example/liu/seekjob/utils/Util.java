package com.example.liu.seekjob.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.liu.seekjob.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static void setBooleanValueToSharedPreferences(Context context, String name, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setStringValueToSharedPreferences(Context context, String name, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setEditTextBottomLine(EditText editText, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        editText.setCompoundDrawables(null, null, null, drawable);
    }

    public static void loadImg(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.drawable.img_load_error).into(imageView);
    }

    public static String formatDate(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
