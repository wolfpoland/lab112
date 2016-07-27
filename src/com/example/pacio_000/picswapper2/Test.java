package com.example.pacio_000.picswapper2;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException{
	//	Serwer s=new Serwer(null);
		//s.serwerStart();
		File f=new File("C:/PicSwaper");
		List<String> lista=new ArrayList<String>();
		lista=FolderWorker.robTo(new ArrayList<String>(), f);
	//	li=FolderWorker.getLista();
		int n=0;
		for(String s : lista){
			System.out.println("["+n+"]: "+s);
		}
	}
}
