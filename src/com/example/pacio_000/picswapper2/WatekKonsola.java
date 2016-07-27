package com.example.pacio_000.picswapper2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JTextArea;


public class WatekKonsola implements Runnable{
	private boolean dzialanie;
	File plik;
	Scanner skaner;
	JTextArea text;
	public WatekKonsola(JTextArea text) throws IOException{
		this.text=text;
		dzialanie=true;
		plik=new File("C:/PicSwaper/Temp/serwerlog.txt");
		if(!plik.exists()){
			
			plik.createNewFile();
		}
		skaner=new Scanner(plik);
	}
	
	public boolean isDzialanie() {
		return dzialanie;
	}

	public void setDzialanie(boolean dzialanie) {
		this.dzialanie = dzialanie;
	}

	public File getPlik() {
		return plik;
	}

	public void setPlik(File plik) {
		this.plik = plik;
	}

	public void run(){
		while(dzialanie==true){
			if(skaner.hasNextLine()){
			text.append(skaner.nextLine());
			text.updateUI();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		skaner.close();
	}
}
