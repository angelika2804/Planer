package com.planer.pracownik;




public class Pracownik {
	public String login;
	public String imie_nazwisko;
	public boolean czy_kierownik;
	public int status;
	
	//status constant - niech zostan¹ 
	public static final int STATUS_GOOD = 0;
	public static final int STATUS_NO_CONNECTION = 1;
	public static final int STATUS_NOT_AUTHENTICATED = 2;
	public static final int STATUS_SQL_EXCEPTION = 4;
	
	public Pracownik(){
		status = STATUS_GOOD;
	}
	public Pracownik(String login, String imie_nazwisko, boolean czy_kierownik){
		this.login=login;
		this.imie_nazwisko=imie_nazwisko;
		this.czy_kierownik=czy_kierownik;
		this.status = STATUS_GOOD;
	}

}
