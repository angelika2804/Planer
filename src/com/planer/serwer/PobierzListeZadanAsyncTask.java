package com.planer.serwer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

public class PobierzListeZadanAsyncTask  extends AsyncTask<Pracownik,Void,ArrayList<Zadanie>>{
   private MainActivity parentActivity;
   
   public PobierzListeZadanAsyncTask(MainActivity parentActivity) {
      this.parentActivity = parentActivity;
   }
   
   protected void onPreExecute(){

   }
   
   @Override
   protected ArrayList<Zadanie> doInBackground(Pracownik... arg0) {
	   Pracownik prac = (Pracownik)arg0[0];
	   ArrayList<Zadanie> result = new ArrayList<Zadanie>();
	   int index1, index2;
		  try{
		     String link = "http://kalendarzplaner.esy.es/pobierz_liste_zadan.php?login="+prac.login;
			 HttpClient client = new DefaultHttpClient();
			 HttpGet request = new HttpGet();
			 request.setURI(new URI(link));
			 HttpResponse response = client.execute(request);
			 BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent())); 
			 String line="";
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		     while ((line = in.readLine()) != null) {
		    	  Zadanie biezaceZadanie = new Zadanie();
		    	  index1 = 0;
		    	  index2 = line.indexOf("#");
		    	  biezaceZadanie.id = Integer.parseInt(line.substring(index1, index2));
		    	  index1 = index2+1;
		    	  index2 = line.indexOf("#", index1);
		    	  biezaceZadanie.poczatek = sdf.parse(line.substring(index1, index2));
		    	  index1 = index2+1;
		    	  index2 = line.indexOf("#", index1);
		    	  biezaceZadanie.koniec = sdf.parse(line.substring(index1, index2));
		    	  index1 = index2+1;
		    	  index2 = line.indexOf("#", index1);
		    	  biezaceZadanie.nazwa = line.substring(index1, index2);
		    	  biezaceZadanie.wlasciciel = new Pracownik();
		    	  index1 = index2+1;
		    	  index2 = line.indexOf("#", index1);
		    	  biezaceZadanie.wlasciciel.login = line.substring(index1, index2);
		    	  index1 = index2+1;
		    	  index2 = line.indexOf("#", index1);
		    	  biezaceZadanie.wlasciciel.imie_nazwisko = line.substring(index1, index2);
		    	  index1 = index2+1;
		    	  biezaceZadanie.wlasciciel.czy_kierownik = (Integer.parseInt(line.substring(index1)) == 1);
		    	  result.add(biezaceZadanie);
		      }
		      in.close();
		      
		      
		  } catch(Exception e){
			  //Toast.makeText(parentActivity, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	    	return result;
   }
   
   @Override
   protected void onPostExecute(ArrayList<Zadanie> result){
	   //TODO: zamontowaæ listê zadañ gdzie trzeba, ew. gdzieœ j¹ przechowaæ
	   //TODO: póki co elementy listy zadañ nie maj¹ przypisanych list pracowników
	   parentActivity.lista_zadan=result;
	   
//	   for(int i=0; i<result.size();i++)
//		   Toast.makeText(parentActivity, result.get(i).nazwa, Toast.LENGTH_SHORT).show();
   }
}