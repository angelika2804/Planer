package com.planer.serwer;


import java.net.URI;
import java.text.SimpleDateFormat;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.planer.R;
import com.planer.MainActivity;
import com.planer.zadanie.Zadanie;

import android.os.AsyncTask;
import android.widget.Toast;

public class EdytujZadanieAsyncTask  extends AsyncTask<Zadanie,Void,Void>{
   private MainActivity parentActivity;
   
   public EdytujZadanieAsyncTask(MainActivity parentActivity) {
      this.parentActivity = parentActivity;
   }
   
   protected void onPreExecute(){

   }
   
   @Override
   protected Void doInBackground(Zadanie... arg0) {
	   Zadanie stare = (Zadanie )arg0[0];
	   Zadanie nowe = (Zadanie) arg0[1];
		  try{
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			 String poczatek = formatter.format(nowe.poczatek);
			 String koniec = formatter.format(nowe.koniec);
		     String link = "http://kalendarzplaner.esy.es/edytuj_zadanie.php?id="+stare.id+"&poczatek="+poczatek+"&koniec="+koniec+"&nazwa="+nowe.nazwa+"&wlasciciel="+nowe.wlasciciel.login;
			 HttpClient client = new DefaultHttpClient();
			 HttpGet request = new HttpGet();
			 request.setURI(new URI(link));
			 client.execute(request);
			 
			 //tabela ³¹cznikowa:
			 
			 link = "http://kalendarzplaner.esy.es/usun_uczestnikow.php?id="+stare.id;
			 client = new DefaultHttpClient();
			 request = new HttpGet();
			 request.setURI(new URI(link));
			 client.execute(request);
			 
			 for(int i=0; i<nowe.lista_pracownikow.size(); i++){
				 link = "http://kalendarzplaner.esy.es/dodaj_uczestnika.php?id="+stare.id+"&login="+nowe.lista_pracownikow.get(i).login;
				 client = new DefaultHttpClient();
				 request = new HttpGet();
				 request.setURI(new URI(link));
				 client.execute(request);
			 }
			 link = "http://kalendarzplaner.esy.es/dodaj_uczestnika.php?id="+stare.id+"&login="+nowe.wlasciciel.login;
			 client = new DefaultHttpClient();
			 request = new HttpGet();
			 request.setURI(new URI(link));
			 client.execute(request);
		      
		  } catch(Exception e){
			  //Toast.makeText(parentActivity, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	    	return null;
   }
   
   @Override
   protected void onPostExecute(Void result){
	   Toast.makeText(parentActivity, parentActivity.getString(R.string.zadanie_edytowane), Toast.LENGTH_LONG).show();
	   
   }
}