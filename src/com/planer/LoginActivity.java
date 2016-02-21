package com.planer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.planer.R;
import com.planer.pracownik.Pracownik;
import com.planer.serwer.Serwer;
import com.planer.zadanie.Zadanie;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class LoginActivity extends android.support.v4.app.Fragment  {
     
	private DrawerLayout mDrawerLayout;
	private MainActivity parentActivity;
	public static Serwer serwer;
	public static Pracownik current_pracownik;
	public LoginActivity(DrawerLayout mDrawerLayout, MainActivity parentActivity){
		this.mDrawerLayout = mDrawerLayout;
		this.parentActivity = parentActivity;
	}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        //this prevents the drawer icon from showing up
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        assert parentActivity.getSupportActionBar() != null;
        parentActivity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        parentActivity.getSupportActionBar().setHomeButtonEnabled(false);
        parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        
        final EditText edit_login = (EditText) rootView.findViewById(R.id.edit_login);
        final EditText edit_haslo = (EditText) rootView.findViewById(R.id.edit_haslo);
        edit_login.requestFocus();
        
        OnClickListener ocl = new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				if(edit_login.getText().length()==0 || edit_login.getText().length()==0)
		    		return; 
				 switch (v.getId()) {
			        case R.id.but_zaloguj:
			        	try{
			        	authentication(edit_login.getText().toString(), edit_haslo.getText().toString());
			        	} catch(Exception e){
			        		Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
			        	}
			            break;
		        }
			}
		};
		
		Button but_zaloguj = (Button) rootView.findViewById(R.id.but_zaloguj);
		but_zaloguj.setOnClickListener(ocl); 
        
        return rootView;
    }
    
    private void authentication(String login, String password){
    	final EditText edit_login = (EditText) getView().findViewById(R.id.edit_login);
        final EditText edit_haslo = (EditText) getView().findViewById(R.id.edit_haslo);
    	serwer = new Serwer(parentActivity, mDrawerLayout, edit_login, edit_haslo);
    	serwer.autoryzuj(login, passwordToHash(password));
    	
    	    	
    	/* przyk³ad u¿ycia pobierzListeZadan */
    	Pracownik admin = new Pracownik("Admin", "Kuba", true);
    	serwer.pobierzListeZadan(admin);
    	
    	//przyk³ad u¿ycia dodajZadanie
    	 
    	//  dostêpni pracownicy: 
    	//	kierownik/kierownik, Angelika Dudzik, true
    	//	pracownik/pracownik, Jakub Kwasny, false
    	//	Admin/admin, Admin Adminski, true
    	//	kowalski/kowalski, Jan Kowalski, false
    	
//    	Pracownik kuba = new Pracownik("pracownik", "Jakub Kwasny", false);
//    	ArrayList<Pracownik> lista_pracownikow = new ArrayList<Pracownik>();
//    	
//        lista_pracownikow.add(new Pracownik("kowalski","Jan Kowalski",false));
//        lista_pracownikow.add(new Pracownik("kierownik","Angelika Dudzik",true));
//        lista_pracownikow.add(new Pracownik("Admin","Admin Adminski",true));
//        lista_pracownikow.add(new Pracownik("pracownik","Jakub Kwasny",false));
//    	
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//			Zadanie z = new Zadanie("Zadanie8", sdf.parse("2015-03-03"), sdf.parse("2015-03-05"),kuba);
//			z.lista_pracownikow = lista_pracownikow;
//			serwer.stworzZadanie(z);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
       
        
    	//przyk³ad u¿ycia edytujZadanie:
//    	Pracownik kuba = new Pracownik("pracownik", "Jakub Kwasny", false);
//    	ArrayList<Pracownik> lista_pracownikow = new ArrayList<Pracownik>();
//        lista_pracownikow.add(new Pracownik("kowalski","Jan Kowalski",false));
//        lista_pracownikow.add(new Pracownik("Admin","Admin Adminski",true));
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    	try {
//    		Zadanie z = new Zadanie("InnyPrzyklad", sdf.parse("2015-03-03"), sdf.parse("2015-03-05"),kuba);
//			z.lista_pracownikow = lista_pracownikow;
//			serwer.edytujZadanie(new Zadanie(14,"cokolwiek"), z);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} 
	}
    
    private String passwordToHash(String password){
    	String output = null;
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
	        byte[] bytes = md.digest();
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++)
	        {
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        output = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return output;
    }
}

