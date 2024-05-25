/**
 * This program starts the server connection to be used by GameStarter to communicate with.
 * It will be the main program that the players will be using for interaction.
 * 
@author Albert Gabriel P. Abdon (220028)
@version May 15, 2023
**/
/*
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.
I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.
*/

import java.io.*;
import java.net.*;

//initializes gameserver values.

public class GameServer {
    private ServerSocket ss;
    private int numPlayers, maxPlayers, maxHP;
    private Socket p1Socket, p2Socket;
    private ReadFromClient p1ReadRunnable, p2ReadRunnable; //read and translates liveaction movement variable
    private WriteToClient p1WriteRunnable, p2WriteRunnable;
    private int p1x, p1y, p2x, p2y;
    private int l1x, l1y, l2x, l2y;
    private boolean p1lv, p2lv, p1hit, p2hit;
    private boolean pe1, pe2;

    //makes the gameserver constructor.
    public GameServer() {
      System.out.println("--Game Server----");
      numPlayers = 0;
      maxPlayers = 2;

      maxHP = 550;

      p1x = 100;
      p1y = 400;
      l1x = 0;
      l1y = 0;
      p1lv = false;
  
      p1hit = false;
      pe1 = false;

      p2x = 700;
      p2y = 400;
      l2x = 0;
      l2y = 0;
      p2lv = false;

      p2hit = false;
      pe2 = false;

      try {
        ss = new ServerSocket(51734);
      } catch (IOException ex) {
        System.out.println("IOException from GameServer Constructor");
      }
    }
    
    //waits for other players to join as to begin the game. Differentiates players via 
    //player 1 and 2.
    public void acceptConnections() {
    try {
      System.out.println("Waiting for connections...");
      while(numPlayers < maxPlayers){
        Socket s = ss.accept();
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        numPlayers++;
        out.writeInt(numPlayers);
        System.out.println("Player #" + numPlayers + " has connected.");

        ReadFromClient rfc = new ReadFromClient(numPlayers, in);
        WriteToClient wtc = new WriteToClient(numPlayers, out);

        if(numPlayers == 1){
          p1Socket = s;
          p1ReadRunnable = rfc;
          p1WriteRunnable = wtc;
        } else {
          p2Socket = s;
          p2ReadRunnable = rfc;
          p2WriteRunnable = wtc;
          //starts both after waiting
          p1WriteRunnable.sendStartMsg();
          p2WriteRunnable.sendStartMsg();
          
          Thread readThread1 = new Thread(p1ReadRunnable);
          Thread readThread2 = new Thread(p2ReadRunnable);
          readThread1.start();
          readThread2.start();
          Thread writeThread1 = new Thread(p1WriteRunnable);
          Thread writeThread2 = new Thread(p2WriteRunnable);
          writeThread1.start();
          writeThread2.start();
        }
      }
    } catch (IOException ex) {
        System.out.println("IOException from acceptConnections()");
    }
  }

  //reads data sent from the server and sets the received values into its initiated values.
  private class ReadFromClient implements Runnable {
    private int playerID;
    private DataInputStream dataIn;
    
    public ReadFromClient(int pid, DataInputStream in){
    playerID = pid;
    dataIn = in;
    System.out.println("RFC" + playerID + "Runnable created.");
    }

    public void run() {
      try{
        while(true){
          if(playerID == 1){
            p1x = dataIn.readInt();
            p1y = dataIn.readInt();
            p1lv = dataIn.readBoolean();
            l1x = dataIn.readInt();
            l1y = dataIn.readInt();
            p1hit = dataIn.readBoolean();
            pe1 = dataIn.readBoolean();

          } else {
            p2x = dataIn.readInt();
            p2y = dataIn.readInt();
            p2lv = dataIn.readBoolean();
            l2x = dataIn.readInt();
            l2y = dataIn.readInt();
            p2hit = dataIn.readBoolean();
            pe2 = dataIn.readBoolean();
   
          }
        }

      } catch(IOException ex){
          System.out.println("IOException from RFC run()");
      }
    }
  }

  //Communicates with the server and sends out data.
  private class WriteToClient implements Runnable {
    private int playerID;
    private DataOutputStream dataOut;
    
    public WriteToClient(int pid, DataOutputStream out){
    playerID = pid;
    dataOut = out;
    System.out.println("WTC" + playerID + "Runnable created.");
    }

    public void run() {
      try{
        while(true){
          if(playerID == 1){
            dataOut.writeInt(p2x);
            dataOut.writeInt(p2y);
            dataOut.writeBoolean(p2lv);
            dataOut.writeInt(l2x);
            dataOut.writeInt(l2y);
            dataOut.writeBoolean(p2hit);
            dataOut.writeBoolean(pe2);

            dataOut.flush();
            
          } else {
            dataOut.writeInt(p1x);
            dataOut.writeInt(p1y);
            dataOut.writeBoolean(p1lv);
            dataOut.writeInt(l1x);
            dataOut.writeInt(l1y);
            dataOut.writeBoolean(p1hit);
            dataOut.writeBoolean(pe1);

            dataOut.flush();
          } 
          try{
            Thread.sleep(25);
              } catch(InterruptedException ex){
                System.out.println("InterruptedException from WTC run()");
            }
        }

      } catch(IOException ex){
          System.out.println("IOException from RFC run()");
      }
    }

    //acts as a buffer to wait for 2 players before the game begins.
    public void sendStartMsg(){
      try{
        dataOut.writeUTF("We now have two players.");

      } catch(IOException ex){
          System.out.println("IOException from sendStartMsg()");
      }
    }
  }

  //runs the game and makes players connect to the server.
  public static void main(String[] args) {
  GameServer gs = new GameServer();
  gs.acceptConnections();
  }

}
