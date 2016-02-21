package com.planer.serwer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.planer.R;
import com.planer.MainActivity;
import com.planer.pracownik.Pracownik;

import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.Toast;

public class LogowanieAsyncTask  extends AsyncTask<String,Void,Pracownik>{
   private MainActivity parentActivity;
   private DrawerLayout mDrawerLayout;
   private EditText edit_login;
   private EditText edit_haslo;
   
   public LogowanieAsyncTask(MainActivity parentActivity, DrawerLayout mDrawerLayout, EditText edit_login, EditText edit_haslo) {
      this.parentActivity = parentActivity;
      this.mDrawerLayout = mDrawerLayout;
      this.edit_haslo = edit_haslo;
      this.edit_login = edit_login;
   }
   
   protected void onPreExecute(){

   }
   
   @Override
   protected Pracownik doInBackground(String... arg0) {
	   String login = (String)arg0[0];
       String passhash = (String)arg0[1];
	   Pracownik pracownik = new Pracownik("","",false);
	   pracownik.status = Pracownik.STATUS_NO_CONNECTION | Pracownik.STATUS_NOT_AUTHENTICATED;
		  try{
		     String link = "http://kalendarzplaner.esy.es/logowanie.php?login="+login+"&passhash="+passhash;
			 HttpClient client = new DefaultHttpClient();
			 HttpGet request = new HttpGet();
			 request.setURI(new URI(link));
			 HttpResponse response = client.execute(request);
			 BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent())); 
			 StringBuffer sb = new StringBuffer("");
			 String line="";
		     while ((line = in.readLine()) != null) {
		          sb.append(line);
		          break;
		      }
		      in.close();
		      if(sb.toString().compareTo("")==0){
		    	  pracownik.status = Pracownik.STATUS_NOT_AUTHENTICATED;
		      }
		      else if (sb.charAt(0) == '1'){
		    	  pracownik.czy_kierownik = true;
		    	  pracownik.login=login;
		    	  pracownik.status = Pracownik.STATUS_GOOD;
		    	  pracownik.imie_nazwisko = sb.toString().substring(sb.indexOf("#")+1);
		      }
		      else if(sb.charAt(0) == '0'){
		    	  pracownik.czy_kierownik = false;
		    	  pracownik.login=login;
		    	  pracownik.status = Pracownik.STATUS_GOOD;
		    	  pracownik.imie_nazwisko = sb.toString().substring(sb.indexOf("#")+1);
		      }
		      
		  } catch(Exception e){
			  //Toast.makeText(parentActivity, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
			  pracownik.status |= Pracownik.STATUS_SQL_EXCEPTION; 
		}
	    	return pracownik;
   }
   
   @Override
   protected void onPostExecute(Pracownik result){
	   if((result.status & Pracownik.STATUS_NOT_AUTHENTICATED) != 0){
		    Toast.makeText(parentActivity, parentActivity.getString(R.string.logowanie_blad), Toast.LENGTH_LONG).show();
   			edit_login.setText("");
   			edit_haslo.setText("");
	   }
	   else{
		   Toast.makeText(parentActivity, parentActivity.getString(R.string.logowanie_ok), Toast.LENGTH_LONG).show();
		   parentActivity.uzytkownik = result;
		   parentActivity.displayView(0);
   		//show the drawer menu
   			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
   			assert parentActivity.getSupportActionBar() != null;
           parentActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
           parentActivity.getSupportActionBar().setHomeButtonEnabled(true);
           parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	   }
   }
}