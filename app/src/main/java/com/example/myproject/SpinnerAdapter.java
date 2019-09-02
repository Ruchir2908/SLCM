package com.example.myproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> data;

    public SpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
