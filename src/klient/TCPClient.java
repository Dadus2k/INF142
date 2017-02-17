package klient;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class TCPClient {
  public static void main(String[] args) throws Exception {
    Socket socket = new Socket("localhost", 12900);
    System.out.println("Started client  socket at "
        + socket.getLocalSocketAddress());
    BufferedReader socketReader = new BufferedReader(new InputStreamReader(
        socket.getInputStream()));
    BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(
        socket.getOutputStream()));
    BufferedReader consoleReader = new BufferedReader(
        new InputStreamReader(System.in));

    String promptMsg = "Send ein av kommandoane: \"FULL\", \"DATE\", \"TIME\" eller \"CLOSE\": ";
    String outMsg = null;

    System.out.print(promptMsg);
    while ((outMsg = consoleReader.readLine()) != null) {
    	if(!(outMsg.equals("FULL") || outMsg.equals("DATE") || outMsg.equals("TIME") || outMsg.equals("CLOSE") )){
    		System.out.println("Kommando ikkje gjenkjend, prøv igjen ");
    	} else{


	      socketWriter.write(outMsg);
	      socketWriter.write("\n");
	      socketWriter.flush();
	
	      //Leser inn det som kjem frå serveren
	      String inMsg = socketReader.readLine();
	      if(outMsg.equals("CLOSE")){
	    	  System.out.println("Du er no kopla frå serveren");
	    	  socket.close();
	    	  break;
	      }
	      System.out.println("Frå server: " + inMsg);
	      System.out.println();
	      System.out.print(promptMsg);
	    }
    }
    socket.close();
  }
}