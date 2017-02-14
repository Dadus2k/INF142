package klient;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class TCPClient {
  public static void main(String[] args) throws Exception {
    Socket socket = new Socket("10.96.174.2", 12900);
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
    		System.out.println("Kommando ikkje gjenkjend, pr�v igjen ");
    	} else{
      // Add a new line to the message to the server,
      // because the server reads one line at a time.
	      socketWriter.write(outMsg);
	      socketWriter.write("\n");
	      socketWriter.flush();
	
	      // Read and display the message from the server
	      String inMsg = socketReader.readLine();
	      if(outMsg.equals("CLOSE")){
	    	  System.out.println("Du er no kopla fr� serveren");
	    	  socket.close();
	    	  break;
	      }
	      System.out.println("Fr� server: " + inMsg);
	      System.out.println(); // Print a blank line
	      System.out.print(promptMsg);
	    }
    }
    socket.close();
  }
}