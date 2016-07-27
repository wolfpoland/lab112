package com.example.pacio_000.picswapper2;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;


public class PlikWorker {
	//File f;
	public  static List<String> listaTekstowa(String[] strings, File jc){
		List<String> nowa=new ArrayList<String>();
		for(String f : strings){
			String tekst=jc.getAbsolutePath()+File.separator+f;
			nowa.add(tekst);
		}
		return nowa;
	}
}
