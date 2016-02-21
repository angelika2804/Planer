package com.planer.serwer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.planer.MainActivity;
import com.planer.pracownik.Pracownik;
import com.planer.zadanie.Zadanie;

import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;

public class Serwer {
		private static int status;
		private MainActivity parentActivity;
		private DrawerLayout mDrawerLayout;
		private EditText edit_login;
		private EditText edit_haslo;
		private PobierzListeZadanAsyncTask task_pobierz;
		
		public static final int STATUS_GOOD = 0;
		public static final int STATUS_NO_CONNECTION = 1;
		public static final int STATUS_NOT_AUTHENTICATED = 2;
		public static final int STATUS_SQL_EXCEPTION = 4;
		
		
		public Serwer(MainActivity parentActivity, DrawerLayout mDrawerLayout, EditText edit_login, EditText edit_haslo){
			status = STATUS_NO_CONNECTION | STATUS_NOT_AUTHENTICATED;
			//user = parentActivity.getString(R.string.db_login);
			//pass = parentActivity.getString(R.string.db_pass);
			this.parentActivity = parentActivity;
			this.edit_haslo = edit_haslo;
		    this.edit_login = edit_login;
			this.mDrawerLayout = mDrawerLayout;
		}
		
		//dzia³a
		public void autoryzuj(String login, String passhash){
			new LogowanieAsyncTask(parentActivity, mDrawerLayout, edit_login, edit_haslo).execute(login,passhash);
		}
		
		//dzia³a
		public void stworzZadanie(Zadanie z){
			new StworzZadanieAsyncTask(parentActivity).execute(z);
		}
		
		//dzia³a
		public void usunZadanie(int id){
			int[] id_arr = new int[1];
			id_arr[0]=id;
			new UsunZadanieAsyncTask(parentActivity).execute(id_arr);
		}
		
		//brakuje pobierania listy pracowników w ka¿dym zadaniu
		public void pobierzListeZadan(Pracownik p){
			task_pobierz = new PobierzListeZadanAsyncTask(parentActivity);
			task_pobierz.execute(p);
			//new PobierzListeZadanAsyncTask(parentActivity).execute(p);
		}
		
		public boolean wait_for_pobierzListeZadan(){
			try {
				task_pobierz.get(3000,  TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return false;
				//e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				return false;
				//e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				return false;
				//e.printStackTrace();
			}
			return true;
		}
		
		//dzia³a
		public void edytujZadanie(Zadanie stare, Zadanie nowe){
			Zadanie[] zad_arr = new Zadanie[2];
			zad_arr[0] = stare;
			zad_arr[1] = nowe;
			new EdytujZadanieAsyncTask(parentActivity).execute(zad_arr);
		}
		

}
