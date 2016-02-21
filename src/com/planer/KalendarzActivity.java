package com.planer;

import com.example.planer.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class KalendarzActivity extends android.support.v4.app.Fragment  {
     
    public KalendarzActivity(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_kalendarz, container, false);
          
        return rootView;
    }
}
