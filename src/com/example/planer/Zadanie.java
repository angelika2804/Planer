package com.example.planer;

import java.util.Date;

import android.R.string;

public class Zadanie {
	private int id;
	public Date poczatek;
	public Date koniec;
	public String nazwa;
	//private Pracownik * wlasciciel;
	//private list <Pracownik *> lista_pracownikow; 
	
	public Zadanie(){}
	public Zadanie(int my_id, String my_nazwa)
	{
		id=my_id;
		nazwa=my_nazwa;
	}
}
