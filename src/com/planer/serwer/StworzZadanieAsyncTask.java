package com.planer.serwer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.planer.R;
import com.planer.MainActivity;
import com.planer.pracownik.Pracownik;
import com.planer.zadanie.Zadanie;

import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.Toast;

public class StworzZadanieAsyncTask  extends AsyncTask<Zadanie,Void,Void>{
   private MainActivity parentActivity;
   private boolean done;
   
   public StworzZadanieAsyncTask(MainActivity parentActivity) {
      this.parentActivity = parentActivity;
      done=true;
   }
   
   protected void onPreExecute(){

   }
   
   @Override
   protected Void doInBackground(Zadanie... arg0) {
	   Zadanie zad = (Zadanie )arg0[0];
		  try{
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			 String poczatek = formatter.format(zad.poczatek);
			 String koniec = formatter.format(zad.koniec);
			 // utwórz wpis w tabeli zadania
		     String link = "http://kalendarzplaner.esy.es/dodaj_zadanie.php?poczatek="+poczatek+"&koniec="+koniec+"&nazwa="+zad.nazwa+"&wlasciciel="+zad.wlasciciel.login;
			 HttpClient client = new DefaultHttpClient();
			 HttpGet request = new HttpGet();
			 request.setURI(new URI(link));
			 HttpResponse response = client.execute(request);
			 BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			 int id = Integer.parseInt(in.readLine());
			 
			 // dodaj do tabeli ³¹cznikowej pracowników...
			 for(int i=0; i<zad.lista_pracownikow.size(); i++){
				 link = "http://kalendarzplaner.esy.es/dodaj_uczestnika.php?id="+id+"&login="+zad.lista_pracownikow.get(i).login;
				 client = new DefaultHttpClient();
				 request = new HttpGet();
				 request.setURI(new URI(link));
				 client.execute(request);
			 }
			 // ...oraz w³aœciciela
			 link = "http://kalendarzplaner.esy.es/dodaj_uczestnika.php?id="+id+"&login="+zad.wlasciciel.login;
			 client = new DefaultHttpClient();
			 request = new HttpGet();
			 request.setURI(new URI(link));
			 client.execute(request);
			 done=true;
		      
		  } catch(Exception e){
			  //Toast.makeText(parentActivity, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	    	return null;
   }
   
   @Override
   protected void onPostExecute(Void result){
	   if(done)
		   Toast.makeText(parentActivity, parentActivity.getString(R.string.zadanie_dodane), Toast.LENGTH_LONG).show();
	   
   }
}