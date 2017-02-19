package Oblig1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dma004 & mme015
 */

public class Server {
  public static void main(String[] args) throws Exception {

    ServerSocket serverSocket = new ServerSocket(12900, 100,  InetAddress.getByName("localhost"));
    System.out.println("Server starta på socket:  " + serverSocket);

    while (true) {
      System.out.println("Ventar på tilkopling...");

      final Socket activeSocket = serverSocket.accept();

      System.out.println("Det er oppretta ei tilkopling med:  " + activeSocket);
      
      Runnable runnable = () -> run(activeSocket);
      new Thread(runnable).start(); //Startar ny tråd for aktiv socket
    }
  }
  
  //Handterer klientar
  public static void run(Socket socket) {
    try{
      BufferedReader socketReader = null;
      BufferedWriter socketWriter = null;
      socketReader = new BufferedReader(new InputStreamReader(
          socket.getInputStream()));
      socketWriter = new BufferedWriter(new OutputStreamWriter(
          socket.getOutputStream()));

      String inMsg = null;
      String outMsg = null;
     
      while ((inMsg = socketReader.readLine()) != null) {    	  
    	 
    	  if(inMsg.equals("FULL")){
    		  outMsg = (String) dato(inMsg);
          }
    	  if(inMsg.equals("DATE")){
    		  outMsg = (String) dato(inMsg);
            }
    	  if(inMsg.equals("TIME")){
    		  outMsg = (String) dato(inMsg);
            }

    	//Skriver ut hos server kva som kom frå klient  
        System.out.println("Mottatt frå klient med IP: " + socket.getInetAddress() +" og port: " + socket.getPort() + ": " + inMsg);
        
        if(inMsg.equals("CLOSE")){
  		  System.out.println("Klient med IP: " + socket.getInetAddress() +" og port: " + socket.getPort() + ": " +  " Har stengt tilkoplinga");
    	  } 
        
        //Skriver til klient
        socketWriter.write(outMsg);
        socketWriter.write("\n");
        socketWriter.flush(); 
      }
      socket.close();
    }catch(SocketException f){
    	System.out.println("SocketException. Klienten blir kopla frå");
    	//Uncomment for å sjå full feilkode
    	//f.printStackTrace();
    	try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }catch(Exception e){
      e.printStackTrace();
    }

  }
  public static Object dato(String dat){
	  String feil = "Feil kommando"; //Bør aldri skje
	  Date date = new Date();
	  
	  if(dat.equals("FULL")){
		  DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
		  return dateFormat.format(date);
	  }
	  
	  if(dat.equals("DATE")){
		  DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		  return dateFormat.format(date);
	  }
	  
	  if(dat.equals("TIME")){
		  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		  return dateFormat.format(date);
	  }
	  
	  return feil;
  }
}