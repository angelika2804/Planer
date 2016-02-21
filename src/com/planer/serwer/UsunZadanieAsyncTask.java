package com.planer.serwer;

import java.net.URI;
import java.text.SimpleDateFormat;

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

public class UsunZadanieAsyncTask  extends AsyncTask<int[],Void,Void>{
   private MainActivity parentActivity;
   
   public UsunZadanieAsyncTask(MainActivity parentActivity) {
      this.parentActivity = parentActivity;
   }
   
   protected void onPreExecute(){

   }
   
   @Override
   protected Void doInBackground(int[]... arg0) {
	   int id = (int )arg0[0][0];
		  try{
			 //usuñ zadanie
		     String link = "http://kalendarzplaner.esy.es/usun_zadanie.php?id="+id;
			 HttpClient client = new DefaultHttpClient();
			 HttpGet request = new HttpGet();
			 request.setURI(new URI(link));
			 client.execute(request);
			 
			 //usuñ wpisy w tabeli ³¹cznikowej
			 link = "http://kalendarzplaner.esy.es/usun_uczestnikow.php?id="+id;
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
	   Toast.makeText(parentActivity, parentActivity.getString(R.string.zadanie_usuniete), Toast.LENGTH_LONG).show();
	   
   }
}