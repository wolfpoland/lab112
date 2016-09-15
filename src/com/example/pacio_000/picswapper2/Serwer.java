package com.example.pacio_000.picswapper2;


import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import javax.swing.JTextArea;

public class Serwer implements Runnable {
	private static JTextArea text;
	private static int port=60;
	private static boolean dzialanie=true;
	static ServerSocket serwer;
	public static boolean isDzialanie() {
		return dzialanie;
	}
	public static void setDzialanie(boolean dzialanie) {
		Serwer.dzialanie = dzialanie;
	}
	public Serwer(JTextArea text,int port){
		this.text=text;
		this.port=port;
	}
	public static void serwerStart() throws IOException, InterruptedException {
		
		//int port=0;
			System.out.println("Port w serwere: "+port);
			 serwer=new ServerSocket(port);
			System.out.println("Serwer ruszyl");
			Socket socket = null;
			InputStream in=null;
			OutputStream out=null;
			ObjectInputStream obj=null;
			ObjectOutputStream oos = null;
			//serwer.setSoTimeout(100000);
		//	socket.setSoTimeout(1000);
			//File plik=new File("C:/PicSwaper/Temp/serwerlog.txt");
			
		//	FileInputStream fis=new FileInputStream(plik);
			//Scanner skaner=new Scanner(plik);
			//FileWriter fw=new FileWriter(plik);
			
		//	PrintWriter writer=new PrintWriter(plik);
			Thread t = null;
			int p=0;
		while(dzialanie==true){
			//	PrintWriter writer=new PrintWriter(plik);
			try{
				if(p==0){
					text.append(" Serwer start ");
				}
				p++;
				//BufferedWriter bw=new BufferedWriter(fw);
				//bw.write("Serwer >> Serwer ruszy ");
				//System.out.println("Poczatek petli ");
				//text.append(" Serwer start ");
				
			//	System.out.println(" Czekam na klienta..");
				
			//	writer.println("Serwer >> Czekam na klienta /n");
				//System.out.println("Stoje przed ifem");
				if(t==null || !t.isAlive()){
					System.out.println("Jestem w srodku i czekam na klienta");
					text.append("\n Czekam na klienta " );
				socket=serwer.accept();
				System.out.println("Klient polaczony");
				 in=socket.getInputStream();
				 System.out.println("in poszedl");
				out=socket.getOutputStream();
				System.out.println("out poszedl");
			//	DataInputStream inD=new DataInputStream(in);
		//		DataOutputStream outD=new DataOutputStream(out);
			//	ObjectOutputStream obj=new ObjectOutputStream(out);
				 oos=new ObjectOutputStream(socket.getOutputStream());
				 System.out.println("oos poszedl");
				 obj=new ObjectInputStream(in);
				 System.out.println("obj poszedl");
				// oos=new ObjectOutputStream(socket.getOutputStream());
				// System.out.println("oos poszedl");
				//if(((Koniec)r2).getDzialanie()){
				System.out.println("Jestem w ifie");
				
				System.out.println("Klietn polaczony");
				Runnable r=new Watek(socket,in,out,obj,oos);
				 t=new Thread(r);
				
				t.start();
			//	}
				//boolean tmp=((Koniec)r2).getDzialanie();
				//System.out.println("Dzialanie: "+tmp);
			//	dzialanie=tmp;
				text.append("\n Koniec  dzialania serwera ");
				//bw.write("Dzialalo");
			System.out.println("Konicze dzialanie");
			
				}
			//writer.close();
			}catch(Exception e){
				e.printStackTrace();
				System.err.println("Zepsulo sie ");
				//dzialanie=false;
				
				oos.close();
				if(obj!=null){
					obj.close();
				}
				in.close();
				out.close();
				socket.close();
				System.out.println("Zakonczone zamykanie");
				
			}
		}
		//	}
			if(socket!=null){
			socket.close();
			oos.close();
			obj.close();
			in.close();
			out.close();
			}
			serwer.close();
			text.append("Serwer zatrzymany");
			//skaner.close();
			//writer.close();
		
			//t2.sleep(1000);
		//	System.exit(0);
		}
		public static void dzialajDalej(Socket socket, InputStream in, OutputStream out, ObjectInputStream obj, ObjectOutputStream oos) throws IOException{
			try{
				System.out.println("Dzialaj dalej");
				
			text.append(" Serwer start ");
			
			System.out.println(" Czekam na klienta..");
			text.append("\n Czekam na klienta " );
		//	writer.println("Serwer >> Czekam na klienta /n");
			socket=serwer.accept();
			 in=socket.getInputStream();
			out=socket.getOutputStream();
		//	DataInputStream inD=new DataInputStream(in);
	//		DataOutputStream outD=new DataOutputStream(out);
		//	ObjectOutputStream obj=new ObjectOutputStream(out);
			 obj=new ObjectInputStream(socket.getInputStream());
			 oos=new ObjectOutputStream(socket.getOutputStream());
			//if(((Koniec)r2).getDzialanie()){
			System.out.println("Jestem w ifie");
			
			System.out.println("Klietn polaczony");
			Runnable r=new Watek(socket,in,out,obj,oos);
			Thread t=new Thread(r);
			
			t.start();
		//	}
			//boolean tmp=((Koniec)r2).getDzialanie();
			//System.out.println("Dzialanie: "+tmp);
		//	dzialanie=tmp;
			text.append("\n Koniec  dzialania serwera ");
			//bw.write("Dzialalo");
		System.out.println("Konicze dzialanie");
			
		//writer.close();
		}catch(SocketTimeoutException e){
			System.out.println("Czas min¹ !");
		}catch(Exception e){
			System.err.println("Zepsulo sie ");
			dzialanie=false;
		}
	//	}
		if(socket!=null){
		socket.close();
		oos.close();
		obj.close();
		in.close();
		out.close();
		}
		serwer.close();
		text.append("Serwer zatrzymany");
		}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			serwerStart();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
			
			
			}
			
					
		
	
	

	


	

