package com.planer;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.planer.R;
import com.planer.zadanie.Zadanie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class PlanerActivity extends android.support.v4.app.Fragment  {
     
	private DrawerLayout mDrawerLayout;
	private MainActivity parentActivity;
	private Context context;
	private ListView listview_lista_zadan;
    public PlanerActivity(DrawerLayout mDrawerLayout, MainActivity parentActivity, Context context){
		this.mDrawerLayout = mDrawerLayout;
		this.parentActivity = parentActivity;
		this.context = context;
	}
    public static ArrayAdapter<String> adapter ;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_planer, container, false);
        
        LoginActivity.serwer.pobierzListeZadan(MainActivity.uzytkownik); 
        Boolean pobrano = LoginActivity.serwer.wait_for_pobierzListeZadan();

             
        listview_lista_zadan=(ListView) rootView.findViewById(R.id.listview_zadania);
        adapter=new ArrayAdapter<String>(context, R.layout.list_item);
        
        if(pobrano)
        {
        	adapter.clear();
	        for(Zadanie my_zadanie:parentActivity.lista_zadan)
	        {
	        	adapter.add(my_zadanie.nazwa);
	        }
	        
	        listview_lista_zadan.setAdapter(adapter);
        }
        
        listview_lista_zadan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                   String nazwa_zadania=listview_lista_zadan.getItemAtPosition(position).toString();
                   Zadanie szukane_zadanie = null;
                   for(Zadanie my_zadanie: parentActivity.lista_zadan)
                   {
                	   if(my_zadanie.nazwa.compareTo(nazwa_zadania)==0)
                	   {
                		   szukane_zadanie=my_zadanie;
                	   }
                	   
                   }
					Intent intent = new Intent(context, ZadanieActivity.class);
					intent.putExtra("nazwa",szukane_zadanie.nazwa);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					intent.putExtra("poczatek", sdf.format(szukane_zadanie.poczatek));
					intent.putExtra("koniec",sdf.format(szukane_zadanie.koniec));
					intent.putExtra("id", szukane_zadanie.id);
				    startActivity(intent);
            }
        });
        
        
		OnClickListener ocl = new OnClickListener(){
		        	
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, ZadanieActivity.class);
					    //intent.putExtra("dane",przekaz);
					    //startActivity(intent);
					    startActivityForResult(intent, getTargetRequestCode());
					    
					    LoginActivity.serwer.pobierzListeZadan(MainActivity.uzytkownik); 
				        Boolean pobrano = LoginActivity.serwer.wait_for_pobierzListeZadan();
				        if(pobrano)
				        {
				        	adapter.clear();
					        for(Zadanie my_zadanie:parentActivity.lista_zadan)
					        {
					        	adapter.add(my_zadanie.nazwa);
					        }
					        
					        listview_lista_zadan.setAdapter(adapter);
				        }

				};
		};
				
		Button btn_dodaj = (Button) rootView.findViewById(R.id.btn_dodaj_zadanie);
		btn_dodaj.setOnClickListener(ocl); 
				
		OnClickListener update_ocl = new OnClickListener(){
		@Override
		public void onClick(View v) {			    
		    LoginActivity.serwer.pobierzListeZadan(MainActivity.uzytkownik); 
	        Boolean pobrano = LoginActivity.serwer.wait_for_pobierzListeZadan();
	        if(pobrano)
	        {
	        	adapter.clear();
		        for(Zadanie my_zadanie:parentActivity.lista_zadan)
		        {
		        	adapter.add(my_zadanie.nazwa);
		        }
		        listview_lista_zadan.setAdapter(adapter);
	        }
		};
	};
	
	Button btn_aktualizuj = (Button) rootView.findViewById(R.id.btn_aktualizuj);
	btn_aktualizuj.setOnClickListener(update_ocl); 
    
      
        return rootView;
    }  	
      
    
}
