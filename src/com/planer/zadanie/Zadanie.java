package com.planer.zadanie;

import java.util.ArrayList;
import java.util.Date;

import com.planer.pracownik.Pracownik;

import android.R.string;

public class Zadanie {
	public int id;
	public Date poczatek;
	public Date koniec;
	public String nazwa;
	public Pracownik wlasciciel;
	public ArrayList<Pracownik> lista_pracownikow; 
	
	public Zadanie(){}
	public Zadanie(int my_id, String my_nazwa)
	{
		id=my_id;
		nazwa=my_nazwa;
	}
	public Zadanie(String nazwa, Date poczatek, Date koniec, Pracownik wlasciciel){
		this.nazwa = nazwa;
		this.poczatek = poczatek;
		this.koniec = koniec;
		this.wlasciciel = wlasciciel;
	}
}
