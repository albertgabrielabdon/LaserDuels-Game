/**
 * This program communicates with the server as it creates the players inside the server
 * to co-exist. It makes sure their inputs and outputs are well received in the other end.
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

import javax.swing.*;
import java.io.*;
import java.net.*;

//instantiates the needed values.

public class GameStarter extends JFrame {
    private int playerID;
    private Socket socket;
    private Player me, enemy;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private GameCanvas canv;
    private GameFrame GF;
   

  //makes the constructor.
    public GameStarter(int w, int h) {
  
        }

    //makes the main method that will connect the game into a specific server given an 
    //address and a port.
    private void connectToServer(){
      try {
          socket = new Socket("localhost", 51734);
          DataInputStream in = new DataInputStream(socket.getInputStream());
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());
          playerID = in.readInt();
          System.out.println("You are player #" + playerID);
          if(playerID == 1){
          System.out.println("Waiting for Player #2 to connect...");
      
          }
          rfsRunnable = new ReadFromServer(in);
          wtsRunnable = new WriteToServer(out);
          rfsRunnable.waitForStartMsg();
  
      } catch(IOException ex){
          System.out.println("IOException from connectToServer()");
      }
  }

    //draws and differentiates sprites from main player to enemy.
    private void createSprites(){
      if (playerID == 1){
        canv.setChar1(100, 400);
        canv.returnChar1().getPlayerID(playerID);
        //do i still need this LMAO
        me = canv.returnChar1();
        enemy = canv.returnChar2();

        System.out.println(playerID);
      } else {
        canv.setChar1(700, 400);
        canv.returnChar1().getPlayerID(playerID);
        enemy = canv.returnChar2(); //not workin
        me = canv.returnChar1(); // workin

        System.out.println(playerID);
      }
    }

    //reads data received from GameStarter to be interpreted by the sprites.
    private class ReadFromServer implements Runnable {
      private DataInputStream dataIn;
  
      public ReadFromServer(DataInputStream in){
        dataIn = in;
        System.out.println("RFS Runnable created");
      }

      public void run(){
        try{
            while(true) {
              int enemyX = dataIn.readInt();
              int enemyY = dataIn.readInt();
              boolean enemyLV = dataIn.readBoolean();
              int enemyMx = dataIn.readInt();
              int enemyMy = dataIn.readInt();
              boolean gotHit = dataIn.readBoolean();
              boolean meHit = dataIn.readBoolean();
              
              if(enemy != null) {
                  enemy.setX(enemyX);
                  enemy.setY(enemyY);
                  enemy.LaserValue(enemyLV);
                  enemy.getLaserX(enemyMx);
                  enemy.getLaserY(enemyMy);
                  
                  enemy.setKB(gotHit);
                  me.setKB(meHit);


              }
          }
  
        } catch(IOException ex){
            System.out.println("IOException deom RFS run()");
        }
  
      }
      
      //a buffer method which aims to delay the spawning of 2 GUIs as to wait for 2 players
      //to begin the game.
      public void waitForStartMsg(){
        try{
          String startMsg = dataIn.readUTF();
          System.out.println("Message from server: " + startMsg);
          Thread readThread = new Thread(rfsRunnable);
          Thread writeThread = new Thread(wtsRunnable);
          readThread.start();
          writeThread.start();
  
        } catch(IOException ex){
          System.out.println("IOException from waitForStartMsg()");
        }
      }
    }

    //Sends data to the server given specific input.
  
    private class WriteToServer implements Runnable {
      private DataOutputStream dataOut;
  
      public WriteToServer(DataOutputStream out){
        dataOut = out;
        System.out.println("WTS Runnable created");
      }

      public void run(){
        try{
          while(true){
            if(me != null){            
              dataOut.writeInt(me.getX());
              dataOut.writeInt(me.getY());

              dataOut.writeBoolean(me.returnLaserValue());
              dataOut.writeInt(me.returnMouseX());
              dataOut.writeInt(me.returnMouseY());

              dataOut.writeBoolean(me.isHitting(enemy));
              dataOut.writeBoolean(enemy.isHitting(me));

              dataOut.flush();
            }
            try{
              Thread.sleep(25);
            } catch(InterruptedException ex){
                System.out.println("InterruptedException from WTS run()");
            }
          }
  
  
        } catch(IOException ex){
            System.out.println("IOException from WTS run()");
        }
        
      }
    }

    //sets up the gamecanvas, frame's GUI and the loading screen.

    public void setUpGUIs(){
      canv = new GameCanvas(playerID);
      createSprites();
      GF = new GameFrame(canv);
      LoadScreen ls = new LoadScreen();
      GF.setUpGUI();
    }

    //connects to the actual server and sets the main gui.

    public static void main(String[] args) {
        GameStarter p = new GameStarter(500, 100);
        p.connectToServer();
        p.setUpGUIs();
    }
}