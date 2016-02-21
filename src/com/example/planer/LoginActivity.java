package com.example.planer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
	private Context context;
    public LoginActivity(DrawerLayout mDrawerLayout, MainActivity parentActivity, Context context){
		this.mDrawerLayout = mDrawerLayout;
		this.parentActivity = parentActivity;
		this.context = context;
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
        
        OnClickListener ocl = new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				if(edit_login.getText().length()==0 || edit_login.getText().length()==0)
		    		return; 
				 switch (v.getId()) {
			        case R.id.but_zaloguj:
			        	if(authentication(edit_login.getText().toString(), edit_haslo.getText().toString()) == true){
			        		Toast.makeText(getActivity(), getString(R.string.logowanie_ok), Toast.LENGTH_LONG).show();
			        		android.os.SystemClock.sleep(Toast.LENGTH_LONG);
			        		parentActivity.displayView(0);
			        		//show the drawer menu
			        		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			        		assert parentActivity.getSupportActionBar() != null;
			                parentActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
			                parentActivity.getSupportActionBar().setHomeButtonEnabled(true);
			                parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			        	}
			        	else{
			        		Toast.makeText(getActivity(), getString(R.string.logowanie_blad), Toast.LENGTH_LONG).show();
			        		edit_login.setText("");
			        		edit_haslo.setText("");
			        	}
			            break;
		        }
			}
		};
		
		Button but_zaloguj = (Button) rootView.findViewById(R.id.but_zaloguj);
		but_zaloguj.setOnClickListener(ocl); 
        
        return rootView;
    }
    
    private boolean authentication(String login, String password){
		if(login.compareTo("Admin") == 0 && password.compareTo("admin") == 0)
			return true;
		else
			return false;
	}
}
