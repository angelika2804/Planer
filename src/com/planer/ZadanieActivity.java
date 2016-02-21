package com.planer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.planer.R;
import com.planer.pracownik.Pracownik;
import com.planer.zadanie.Zadanie;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class ZadanieActivity extends AppCompatActivity  {
     
	private Calendar calendar;
	private TextView dateView;
	private EditText nazwa;
	
	private int year, month, day;
	
	private Calendar calendar2;
	private TextView dateView2;
	private int year2, month2, day2;
	
	private boolean flag_edit;
	private int id;
	
	//private DrawerLayout mDrawerLayout;
	//private MainActivity parentActivity;
	//private Context context;
	//public ZadanieActivity(DrawerLayout mDrawerLayout, MainActivity parentActivity, Context context){
	//		this.mDrawerLayout = mDrawerLayout;
	//		this.parentActivity = parentActivity;
	//		this.context = context;
	//}
	
	public ArrayList<Pracownik> lista_pracownikow;
    private ArrayAdapter<String> adapter;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zadanie);
        
        flag_edit=false;
        
        nazwa=(EditText) findViewById(R.id.editText_nazwa_zadania);
        
        dateView = (TextView) findViewById(R.id.textView_poczatek_zadania);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        
        
        dateView2 = (TextView) findViewById(R.id.textView_koniec_zadania);
        calendar2 = Calendar.getInstance();
        year2 = calendar2.get(Calendar.YEAR);
        
        month2 = calendar2.get(Calendar.MONTH);
        day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        showDate2(year2, month2+1, day2);
        
        Button usun = (Button) findViewById(R.id.btn_usun_zadanie);
        usun.setEnabled(false);
        
        Intent i = getIntent();
        if(i.hasExtra("nazwa")) {
        	 Bundle przekazanedane = i.getExtras();
        	 nazwa.setText(przekazanedane.getString("nazwa"));
        	 dateView.setText(przekazanedane.getString("poczatek"));
        	 flag_edit=true;
        	 dateView2.setText(przekazanedane.getString("koniec"));
        	 id=przekazanedane.getInt("id");
        	 usun.setEnabled(true);
        	 
        }
        
   
        lista_pracownikow = new ArrayList<Pracownik>();
        lista_pracownikow.add(new Pracownik("kowalski","Jan Kowalski",false));
        lista_pracownikow.add(new Pracownik("kierownik","Angelika Dudzik",true));
        lista_pracownikow.add(new Pracownik("Admin","Admin Adminski",true));
        lista_pracownikow.add(new Pracownik("pracownik","Jakub Kwasny",false));
        
        ListView listView_lista_pracownikow=(ListView) findViewById(R.id.listView_lista_pracownikow);
        adapter=new ArrayAdapter<String>(this, R.layout.list_item_zadanie);
        
        for(Pracownik my_pracownik:lista_pracownikow)
        {
        	adapter.add(my_pracownik.imie_nazwisko);
        }
        
       listView_lista_pracownikow.setAdapter(adapter);
       
       OnClickListener ocl = new OnClickListener(){
			@Override
			public void onClick(View v) {
				Zadanie z = null;
		        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        
		        if(flag_edit==false) {
			        try {
						z = new Zadanie(nazwa.getText().toString(), sdf.parse(dateView.getText().toString()), sdf.parse(dateView2.getText().toString()),MainActivity.uzytkownik);
						
						z.lista_pracownikow = lista_pracownikow;
						LoginActivity.serwer.stworzZadanie(z);
					} catch (ParseException e) {
						e.printStackTrace();
					} 
			        if(z.nazwa.length()>0)
			        {
			        	PlanerActivity.adapter.add(z.nazwa);
			        }
		        }
		        else{
		        	try {
						z = new Zadanie(nazwa.getText().toString(), sdf.parse(dateView.getText().toString()), sdf.parse(dateView2.getText().toString()),MainActivity.uzytkownik);
						Zadanie stare = new Zadanie(nazwa.getText().toString(), sdf.parse(dateView.getText().toString()), sdf.parse(dateView2.getText().toString()),MainActivity.uzytkownik);
						stare.id=id;
						z.lista_pracownikow = lista_pracownikow;
						LoginActivity.serwer.edytujZadanie(stare, z);
					} catch (ParseException e) {
						e.printStackTrace();
					}
		        }
		        finish();
			}
		};
		
		Button btn_zapisz = (Button) findViewById(R.id.btn_zapisz_zadanie);
		btn_zapisz.setOnClickListener(ocl); 
		
		
		
		 OnClickListener usun_ocl = new OnClickListener(){
			@Override
			public void onClick(View v) {
				LoginActivity.serwer.usunZadanie(id);
				finish();
			}
		};
		usun.setOnClickListener(usun_ocl); 
        
       // return rootView;
        
    }
    
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
       showDialog(999);
       Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
       .show();
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
       // TODO Auto-generated method stub
       if (id == 999) {
          return new DatePickerDialog(this, myDateListener, year, month, day);
       }
       else if(id == 998){
    	   return new DatePickerDialog(this, myDateListener2, year2, month2, day2);
       }
       return null;
    }
    
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
           // TODO Auto-generated method stub
           // arg1 = year
           // arg2 = month
           // arg3 = day
           showDate(arg1, arg2+1, arg3);
        }
     };
     
     private void showDate(int year, int month, int day) {
         dateView.setText(new StringBuilder().append(day).append("/")
         .append(month).append("/").append(year));
      }
 
     
     @SuppressWarnings("deprecation")
     public void setDate2(View view) {
        showDialog(998);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
        .show();
     }
     
     private DatePickerDialog.OnDateSetListener myDateListener2 = new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate2(arg1, arg2+1, arg3);
         }
      };
      
      private void showDate2(int year2, int month2, int day2) {
          dateView2.setText(new StringBuilder().append(day2).append("/")
          .append(month2).append("/").append(year2));
       }
      
      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.main, menu);
         return true;
      }
      
      
      
}
