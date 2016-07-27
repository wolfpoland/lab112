package com.example.pacio_000.picswapper2;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Koniec implements Runnable{
	
	private boolean dzialanie;
	private Socket socket;
	private ServerSocket serwer;
	
	public Koniec(){
		dzialanie=true;
	
		
	}

	public Koniec(Socket socket, ServerSocket serwer){
		this.socket=socket;
		this.serwer=serwer;
	}
	public void run(){
		Scanner skaner=new Scanner(System.in);
		System.out.println("Czekam na koniec dzialania");
		int liczba=skaner.nextInt();
		if(liczba == 0){
			dzialanie=false;
		}
		System.out.println("Za skanerem!");
		System.out.println("Dzialanie: "+dzialanie+" Liczba: "+liczba);
		
	}
	public boolean getDzialanie(){
		return dzialanie;
	}

}
