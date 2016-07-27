package com.example.pacio_000.picswapper2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Testo {
	
	public static void main(String[] args) throws FileNotFoundException{
		File plik=new File("C:/PicSwaper/Temp/serwerlog.txt");
		PrintWriter wri=new PrintWriter(plik);
		wri.println("Dupa");
		wri.close();
	}
}
