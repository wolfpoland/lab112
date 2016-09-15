package com.example.pacio_000.picswapper2;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Watek implements Runnable{
	private int numer=0;
	Socket socket;
	InputStream in;
	OutputStream out;
	ObjectInputStream obj;
	ObjectOutputStream oos;
	List<PlikInfo> lista;
	public Watek(Socket socket, InputStream in, OutputStream out, ObjectInputStream obj, ObjectOutputStream oos){
		this.socket=socket;
		this.in=in;
		this.out=out;
		this.obj=obj;
		this.oos=oos;
	}

	@Override
	public void run() {
		try {
			//InputStream in=socket.getInputStream();
			//OutputStream out=socket.getOutputStream();
		//	DataInputStream inD=new DataInputStream(in);
	//		DataOutputStream outD=new DataOutputStream(out);
		//	ObjectOutputStream obj=new ObjectOutputStream(out);
		//	ObjectInputStream obj=new ObjectInputStream(socket.getInputStream());
			//ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
			//int liczba=inD.read();
			//int liczba= inD.read();
			boolean wyk=true;
		
			System.out.println("<< \t W PETLI");
			int zamowienie=in.read();
			switch(zamowienie){
			case 1:{
				System.out.println("Case 1");
				System.out.println("Zapytanie o id");
				out=socket.getOutputStream();
				out.write(1);
				out.flush();
				System.out.println("Teraz id");
				int id=in.read();
				System.out.println("Polaczono z klientem o id: "+id);
				System.out.println("Wysylam liste do klienta");
				File plik=new File("C:/PicSwaper/Users/U"+id+"");
				List<String> lista=PlikWorker.listaTekstowa(plik.list(),plik);
				System.out.println("Zawartoœæ listy: ");
				for(String s : lista){
					System.out.println("- "+s);
				}
			//	FileInputStream input=new FileInputStream(plik);
			//	ObjectInputStream iner=new ObjectInputStream(input);
			//	lista=(List<PlikInfo>)iner.readObject();
			//	iner.close();
			//	input.close();
				oos.writeObject(lista);
				
				
			//	obj.writeObject(lista);
				break;
			}
			case 2:{
				System.out.println("Case 2");
				System.out.println("Zapytanie o id");
				out.write(1);
				out.flush();
				int id=in.read();
				System.out.println("Polaczono z klientem o id: "+id);
			//	inD.close();
			//	outD.close();
			//	ObjectInputStream obj=new ObjectInputStream(socket.getInputStream());
				//ObjectInputStream obj=new ObjectInputStream(in);
			//	PlikInfo lol;
				PlikInfo plik=(PlikInfo) obj.readObject();
				System.out.println("Odczytano plik: "+plik.getNazwa());
				File pliko=new File("C:/PicSwaper/Users/U"+id);
				if(!pliko.exists()){
					pliko.mkdir();
				}
				if(plik.getTres()!=null){
					String tekst=plik.getTres();
					char[] tab=new char[tekst.length()];
					for(int n=0;n<tekst.length();n++){
						tab[n]=tekst.charAt(n);
					}
					List<String> slowa=new ArrayList<String>();
					int licznik=0;
					for(int n=licznik;n<tekst.length();n++){
						if(tab[n]=='.'){
							String tmp="";
							for(int m=licznik;m<n;m++){
								tmp+=tab[m];
							}
								slowa.add(tmp);
							licznik=n+1;
						}
					}
					File pliczek=new File("C:/PicSwaper/Users/U"+id);
					String napis="C:/PicSwaper/Users/U"+id;
					for(int n=0;n<slowa.size();n++){
						napis+="/"+slowa.get(n);
						pliczek=new File(napis);
						if(!pliczek.exists()){
							pliczek.mkdir();
						}
						System.out.println("sciezka: "+pliczek.getAbsolutePath());
						
					}
					pliczek=new File(napis+"/"+plik.getPlik().getName());
					if(!pliczek.exists()){
						pliczek.createNewFile();
					}
					//FileInputStream input=new FileInputStream(pliczek);
					//ObjectInputStream iner=new ObjectInputStream(input);
					BufferedImage img = ImageIO.read(plik.getPlik());
					ImageIO.write(img, "png", pliczek);
					pliczek=new File(napis+"/source");
					if(!pliczek.exists()){
						pliczek.mkdir();
					}
					pliczek=new File(napis+"/source/"+plik.getNazwa()+".dat");
					pliczek.createNewFile();
					FileOutputStream input=new FileOutputStream(pliczek);
					ObjectOutputStream iner=new ObjectOutputStream(input);
					iner.writeObject(plik);
					iner.close();
					input.close();
					
				}
				
				
				wyk=false;
				break;
				
			}
			case 3:{
				System.out.println("Case 3");
				System.out.println("Zapytanie o id");
				out=socket.getOutputStream();
				out.write(1);
				out.flush();
				System.out.println("Teraz id");
				int id=in.read();
				System.out.println("Polaczono z klientem o id: "+id);
			//	System.out.println("Wysylam liste do klienta");
				File plik=new File("C:/PicSwaper/Users/U"+id+"");
				List<String> lista=PlikWorker.listaTekstowa(plik.list(),plik);
			//	FileInputStream input=new FileInputStream(plik);
			//	ObjectInputStream iner=new ObjectInputStream(input);
			//	lista=(List<PlikInfo>)iner.readObject();
			//	iner.close();
			//	input.close();
				System.out.println("Odbieram ¿adanie od klienta: ");
				File f=(File) obj.readObject();
				System.out.println("Plik: "+f.getAbsolutePath() );
				//File pliko1=new File(f.getAbsolutePath()+File.separator);
				//Plik
				if(f.isDirectory()){
					List<String> listor=FolderWorker.robTo(new ArrayList<String>(), f);
					List<TypyFile> ls=FolderWorker.tworer(listor,f);
					PlikInfo pliko=new PlikInfo("nowy",1,1,"hahahaha",1,ls);
					System.out.println("Przed wys³aniem pliku:");
					oos.writeObject(pliko);
					System.out.println("Plik wys³ano");
				}else{
				System.out.println("Zaczynam prace z przygotowaniem pliku");
				byte[] tablica=new byte[(int)f.length()];
				FileInputStream fis=new FileInputStream(f);
				BufferedInputStream bis=new BufferedInputStream(fis);
				bis.read(tablica,0,tablica.length);
				PlikInfo pliko=new PlikInfo(f.getName(),1,1,"paparapa",1,tablica);
				bis.close();
				fis.close();
				System.out.println("Przed wys³aniem pliku");
				oos.writeObject(pliko);
				System.out.println("Plik wys³ano");
			}
				//oos.writeObject(lista);
				
				
			//	obj.writeObject(lista);
				break;
			}
			case 4:{
				System.out.println("Android CASE! ");
				System.out.println("Case 4");
				System.out.println("Zapytanie o id");
				out.write(1);
				out.flush();
				System.out.println("Teraz id");
				int id=in.read();
				System.out.println("Polaczono z klientem o id: "+id);
				
				System.out.println("Odbieram ¿adanie od klienta: ");
				File f=(File) obj.readObject();
				System.out.println("Plik: "+f.getAbsolutePath() );
				
				if(f.isDirectory()){
					List<String> listor=FolderWorker.robTo(new ArrayList<String>(), f);
					List<TypyFile> ls=FolderWorker.tworer(listor,f);
					PlikInfo pliko=new PlikInfo("nowy",1,1,"hahahaha",1,ls);
					System.out.println("Przed wys³aniem pliku:");
					oos.writeObject(pliko);
					System.out.println("Plik wys³ano");
				}else{
				System.out.println("Zaczynam prace z przygotowaniem pliku");
				byte[] tablica=new byte[(int)f.length()];
				FileInputStream fis=new FileInputStream(f);
				BufferedInputStream bis=new BufferedInputStream(fis);
				bis.read(tablica,0,tablica.length);
				PlikInfo pliko=new PlikInfo(f.getName(),1,1,"paparapa",1,tablica);
				bis.close();
				fis.close();
				System.out.println("Przed wys³aniem pliku");
				oos.writeObject(pliko);
				System.out.println("Plik wys³ano");
			}
				break;
			}
			case 5:{
				System.out.println("LISTA ANDROID CASE! ");
				System.out.println("Case 5");
				System.out.println("Zapytanie o id");
				out.write(1);
				out.flush();
				System.out.println("Teraz id");
				int id=in.read();
				System.out.println("Polaczono z klientem o id: "+id);
				File plik=new File("C:/PicSwaper/Users/U"+id);
				List<String> listor=FolderWorker.robTo(new ArrayList<String>(), plik);
				List<TypyFile> ls=FolderWorker.tworer(listor,plik);
				oos.writeObject(ls);
				System.out.println("Lista wyslana");
			}
			
			}
			
			
			
		
			
		//obj.close();
		//outD.close();
		// inD.close();
			oos.close();
			obj.close();
			in.close();
			out.close();
			socket.close();
	//	Serwer.dzialajDalej(socket, in, out, obj, oos);
			System.out.println("Watek zamkniety");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
		
		
	}
}
	


