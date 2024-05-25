/**
 * This program is where the player is created and instantiated. This includes the
 * animation drawing it has, values for movement to be used in the frame, and collisons in
 * terms of laser hovering of enemy player.
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

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

//initializes main Player values to be used. Extends JComponent to draw the laser.
public class Player extends JComponent {
    private int laserX = 0;
    private int laserY = 0;
    private int mouseX, mouseY;
    private boolean laserVisible = false;
    private int xPos, yPos, xVel, yVel, width, height, playerID;
    private boolean left, right, up, canTripleJump, alive, DamageDealt;
    private int tripleJumpsRemaining = 2;
    private BufferedImage pengu;
    private BufferedImage[] penguIdle;
    private int aniTick, aniIndex, HP;
    private int aniSpeed = 15;
    private boolean loser;

   //instantiates said values in the constructor. It also starts the animation loop for
   //player to use.
    public Player(int xpos, int ypos) {
        HP = 550;
        playerID = 0;
        xPos = xpos; //100
        yPos = ypos; //400
        width = 50;
        height = 50;
        xVel = 0;
        yVel = 0;
        left = false;
        right = false;
        up = false;
        canTripleJump = false;
        alive = true;
        DamageDealt = false;
        loser = false;
        importImage();
        loadPengu();
    }

    //Gets the imported image to be used for animation. 
    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/pengu_data_sheet.png");
        try {
            pengu = ImageIO.read(is); 
        } catch (Exception e) {
            System.out.println("Wrong");
        }
    }

    //Loops and cuts through a specific segment of the imported image. It takes a specific
    //pattern of pixels from the main image to appear as if animated.
    public void loadPengu(){
        penguIdle = new BufferedImage[3];

        if(playerID == 1){
            for(int i = 0; i < penguIdle.length; i++){
                penguIdle[i] = pengu.getSubimage(i*32,32,32,30);
                }

        } else {
            for(int i = 0; i < penguIdle.length; i++){
                penguIdle[i] = pengu.getSubimage(i*32,0,32,30);
                }
        } 
    }
    
    //Sets the index to repeat so the animation can continue looping forever.
    public void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= penguIdle.length)
                aniIndex = 0;
        }
    }

    //Draws the player and the conditions for the laser to appear.
    public void draw(Graphics g) {

        updateAnimationTick();
        g.drawImage(penguIdle[aniIndex],xPos, yPos, width, height,null);

        if (laserVisible) {
            g.setColor(Color.RED);
            g.drawLine(xPos + (width/2), yPos + (height/2), laserX, laserY);
        }
    }

    //Takes damage dealt value and inputs them into HPdamaged to subtract the health
    //of the enemy.

    public void setKB(boolean value){
        DamageDealt = value;
        HPdamaged(DamageDealt);
    }

    //Takes the playerID from which the server had provided it.
    public void getPlayerID(int id){
        playerID += id;
    }

    //Gets and renews the value of x.
    public void setX(int x){
        xPos = x;
    }
    //Gets and renews the value of y.
    public void setY(int y){
        yPos = y;
    }
    //Gets and renews the value of y velocity.
    public void getVelY(int yVelocity) {
        yVel = yVelocity;
    }

    //Adds on the available jumps a player can do (max 3).
    public void getTJR(int tJR) {
        tripleJumpsRemaining += tJR;
    }

    //Gets and renews the value of the truth value of a person that can triple jump.
    public void getTripleJV(boolean value) {
        canTripleJump = value;
    }

    //Gets and renews the value of the truth value of a person that can go left.
    public void getLeft(boolean value){
        left = value;
    }
    //Gets and renews the value of the truth value of a person that can go right.
    public void getRight(boolean value){
        right = value;
    }
    //Gets and renews the value of the truth value of a person that can jump.
    public void getUp(boolean value){
        up = value;
    }
    //Gets and renews the value of the truth value of a person's laser visibility.
    public void LaserValue(boolean value){
        laserVisible = value;
    }
    //Gets and renews the value of player's laser's y coordinate.
    public void getLaserY(int value) {
        laserY = value;
    }
    //Gets and renews the value of player's laser's x coordinate.
    public void getLaserX(int value) {
        laserX = value;
    }
    //Gets and renews the value of player's mouse's x coordinate.
    public void getMouseX(int value) {
        mouseX = value;
    }
    //Gets and renews the value of player's mouse's y coordinate.
    public void getMouseY(int value) {
        mouseY = value;
    }
  
    //show if the player's laser can be visible.
    public boolean returnLaserValue(){
        return laserVisible;
    }

    //shows the current x value of the mouse in the GUI.
    public int returnMouseX() {
        return mouseX;
    }
    
    //shows the current y value of the mouse in the GUI.
    public int returnMouseY() {
        return mouseY;
    }

    //shows the truth value if the player can perform his triple jump;
    public boolean getTJVal(){
        return canTripleJump;
    }

    //shows the available jumps left after using his first jump;
    public int getTJRem(){
        return tripleJumpsRemaining;
    }

    //gets the players current x position.
    public int getX() {
        return xPos;
      }
    
    //gets the players current y position.
    public int getY() {
        return yPos;
      }
    
    //gets the player character's width hitbox size.
    public int getwidth() {
        return width;
      }

    //gets the player character's height hitbox size.
    public int getheight() {
        return height;
      }
    
    //tests whether or not the mouse/laser is hovering on enemy player.
    public boolean isHitting(Player other) {
        return !(this.laserX <= other.getX() ||
                this.laserX >= other.getX() + other.getwidth() ||
                this.laserY <= other.getY() ||
                this.laserY >= other.getY() + other.getheight());
    }
    
    //Declares the players movement.
    public void movement() {
        if (left) {
            xVel = -13;
        } else if (right) {
            xVel = 13;
        } else {
            xVel = 0;
        }

        xPos += xVel;

        if (xPos < 0) {
            xPos = 0;
        } else if (xPos > 800-width) {
            xPos = 800-width;
        }

        if (up && yPos == 600-(height+20)) {
            yVel = -18;
            canTripleJump = true;
        }
        yPos += yVel;
        yVel += 1;

            //this where u set da limits 
        if (yPos >= 600-(height+20)) {
            yPos = 600-(height+20);
            yVel = 0;
            tripleJumpsRemaining = 2;
        } else if(yPos < 0) {
            yPos = 0;
        }
      }
    
    //returns the current hp of the player.
    public int HPbarValue(){
        return HP;
    }

    //method that computes for the damages in HP when a player is hit.
    public void HPdamaged(boolean damage){
        if(damage){
            HP -= 2;
            DamageDealt = false;
            if(HP <= 0){
                HP = 0;
                DamageDealt = false;
                alive = false;
            }
        }
        if(alive == false){
            loser = true;
        }
    }

    //To check who won.
    public boolean checkWinner(){
        return loser;
    }


}