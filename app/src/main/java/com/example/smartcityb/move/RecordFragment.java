package com.example.smartcityb.move;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.smartcityb.R;


public class RecordFragment extends Fragment {


    private ListView history_record;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_record, null, false);
        history_record = (ListView) v.findViewById(R.id.history_record);


        return v;
    }
}