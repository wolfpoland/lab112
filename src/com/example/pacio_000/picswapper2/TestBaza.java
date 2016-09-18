package com.example.pacio_000.picswapper2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

import netscape.javascript.JSObject;

public class TestBaza {
	private static Connection c=null;
	
	public static void polaczZBaza() throws ClassNotFoundException, SQLException{
	
		Class.forName("org.postgresql.Driver");
		c=DriverManager.getConnection("jdbc:postgresql://localhost:5432/test",
            "postgres", "haslo");
		System.out.println("Chyba sie udalo ?");
	}
	public static void CreateTable() throws SQLException, ClassNotFoundException{
		polaczZBaza();
		Statement stm=c.createStatement();
		String sql="CREATE TABLE uzytkownik ("+
		 "ID SERIAL PRIMARY KEY,"+
		 "imie 	TEXT NOT NULL,"+
		 "nazwisko TEXT NOT NULL,"+
		 "mail TEXT NOT NULL,"+
		 "haslo TEXT NOT NULL);";
		stm.executeUpdate(sql);
		stm.close();
		c.close();
		
	}
	public static Uzytkownik LogowanieZresta(String mail, String haslo) throws IOException, JSONException{
		//http://restpicswapper220160914030601.azurewebsites.net/
		   String url = "http://restpicswapper220160914030601.azurewebsites.net/api/Uzytkowniks/logo?mail=" + mail + "&haslo=" + haslo;
		 //  List<String> lista=new ArrayList<String>();
		   Uzytkownik uz = null;
		   URL lacz=new URL(url);
		   URLConnection pol=lacz.openConnection();
		   InputStream is=pol.getInputStream();
		   BufferedReader br=new BufferedReader(new InputStreamReader(is));
		   String line;
		   while((line=br.readLine()) != null){
			   JSONObject obj=new JSONObject(line);
			   uz=new Uzytkownik(obj.getInt("Id"),obj.getString("imie"),obj.getString("nazwisko"),obj.getString("mail"),obj.getString("haslo"));
			   
		   }
		   br.close();
		   is.close();
		   return uz;
		   
	}
	public static Uzytkownik Logowanie(String mail, String haslo) throws ClassNotFoundException, SQLException{
		polaczZBaza();
		Statement stm=c.createStatement();
		String sql="SELECT * FROM uzytkownik WHERE mail= '"+mail+"' AND haslo= '"+haslo+"' ;";
		ResultSet rs=stm.executeQuery(sql);
		Uzytkownik uz = null;
		while(rs.next()){
		 uz=new Uzytkownik(rs.getInt("ID"), rs.getString("imie"),rs.getString("nazwisko"),rs.getString("mail"),rs.getString("haslo"));
		}
		rs.close();
		stm.close();
		c.close();
		System.out.println("Wyœwietlam dane zalogowanego uzytkownika: ");
		if(uz!=null){
		System.out.println("ID: "+uz.getID());
		System.out.println("Imie: "+uz.getImie());
		System.out.println("Nazwisko: "+uz.getNazwisko());
		System.out.println("Email: "+uz.getMail());
		System.out.println("Haslo: "+uz.getHaslo());
		}
		return uz;
				
	}
	public static void Rejestracja(Uzytkownik uz) throws ClassNotFoundException, SQLException{
		polaczZBaza();
		Statement stm=c.createStatement();
		String sql="INSERT INTO uzytkownik (imie,nazwisko,mail,haslo) VALUES ( '"+uz.getImie()+"', '"+uz.getNazwisko()+"' , '"+uz.getMail()+ "' , '"+uz.getHaslo()+"' );";
		stm.executeUpdate(sql);
		stm.close();
		
		c.close();
		
		
	}
}
