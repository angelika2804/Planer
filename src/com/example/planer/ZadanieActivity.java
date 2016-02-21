package com.example.planer;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
 
public class ZadanieActivity extends android.support.v4.app.Fragment  {
     
    public ZadanieActivity(){}

     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_zadanie, container, false);
          
        
        
        
        
        return rootView;
    }
}
