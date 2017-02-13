package klient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TCPServer {
  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(12900, 100,
        InetAddress.getByName("localhost"));
    System.out.println("Server starta på socket:  " + serverSocket);

    while (true) {
      System.out.println("Ventar på tilkopling...");

      final Socket activeSocket = serverSocket.accept();

      System.out.println("Det er oppretta ei tilkopling med:  " + activeSocket);
      
      Runnable runnable = () -> run(activeSocket);
      new Thread(runnable).start(); // startar ny tråd.
    }
  }
  
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
      	  if(inMsg.equals("CLOSE")){
      		outMsg = "Stenger tilkopling";
      		socket.close();
      	  }
      	  
        System.out.println("Received from  client: " + inMsg);
        
        socketWriter.write(outMsg);
        socketWriter.write("\n");
        socketWriter.flush();
        
       
        
      }
      socket.close();
    }catch(Exception e){
      e.printStackTrace();
    }

  }
  public static Object dato(String dat){
	  String feil = "Noko gjekk til helvete";
	  Date date = new Date();
	  
	  if(dat.equals("FULL")){
		  DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy HH:mm:ss");
		  return dateFormat.format(date);
	  }
	  
	  if(dat.equals("DATE")){
		  DateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
		  return dateFormat.format(date);
	  }
	  
	  if(dat.equals("TIME")){
		  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		  return dateFormat.format(date);
	  }
	  
	  return feil;
  }
}