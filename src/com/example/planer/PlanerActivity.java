package com.example.planer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class PlanerActivity extends android.support.v4.app.Fragment  {
     
	private DrawerLayout mDrawerLayout;
	private MainActivity parentActivity;
	private Context context;
    public PlanerActivity(DrawerLayout mDrawerLayout, MainActivity parentActivity, Context context){
		this.mDrawerLayout = mDrawerLayout;
		this.parentActivity = parentActivity;
		this.context = context;
	}
    public List<Zadanie> lista_zadan;
    private ArrayAdapter<String> adapter ;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_planer, container, false);
        //this prevents the drawer icon from showing up
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        assert parentActivity.getSupportActionBar() != null;
        parentActivity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        parentActivity.getSupportActionBar().setHomeButtonEnabled(false);
        parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        
        lista_zadan = new ArrayList<Zadanie>();
    	
    	lista_zadan.add(new Zadanie(1,"Zadanie 1"));
        lista_zadan.add(new Zadanie(2,"Zadanie 2"));
        lista_zadan.add(new Zadanie(3,"Zadanie 3"));
        lista_zadan.add(new Zadanie(4,"Zadanie 4"));
        lista_zadan.add(new Zadanie(5,"Zadanie 5"));
        lista_zadan.add(new Zadanie(6,"Zadanie 6"));
        
        ListView listview_lista_zadan=(ListView) rootView.findViewById(R.id.listview_zadania);
        adapter=new ArrayAdapter<String>(context, R.layout.list_item);
        
        for(Zadanie my_zadanie:lista_zadan)
        {
        	adapter.add(my_zadanie.nazwa);
        }
        
        listview_lista_zadan.setAdapter(adapter);
          
        return rootView;
    }
}
