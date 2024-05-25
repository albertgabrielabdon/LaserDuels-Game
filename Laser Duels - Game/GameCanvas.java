/**
 * This program draws the entire game. 
 * This includes the background, healthbar, and player.
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
import javax.swing.*;

//initializes gamecanvas values.

public class GameCanvas extends JComponent {
    private Player charac1, charac2;
    private int ID;
    private Image image;

    //makes the constructor.
    
    public GameCanvas(int playerID) {
        ID = playerID;
        charac1 = new Player(100, 400);
        charac2 = new Player(700, 400);
        image = new ImageIcon("Background.png").getImage();
        setPreferredSize(new Dimension(800, 600));

    }

    //draws the g2d objects and also healthbar.
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        super.paintComponent(g);
        g2d.drawImage(image,0,0,800,600,null);
        g2d.setColor(Color.RED);

        g2d.fillRect(10,35, charac1.HPbarValue(),15);
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Dialog Bold",Font.BOLD,15));
        g2d.drawString("Enemy Health:" + charac1.HPbarValue(), 20, 55);

        g2d.fillRect(10,90, charac2.HPbarValue(),15);
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Dialog Bold",Font.BOLD,15));
        g2d.drawString("Your Health:" + charac2.HPbarValue(), 20, 110);
        if(charac2.HPbarValue() == 0){
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Dialog Bold",Font.BOLD,30));
            g2d.drawString("YOU LOSE, restart the program to play again.", 20, 160);
          } 
        if(charac1.HPbarValue() == 0) {
            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Dialog Bold",Font.BOLD,30));
            g2d.drawString("YOU WIN, restart the program to play again.", 20, 160);
          }
        
        charac1.draw(g);
        charac2.draw(g);
        returnChar1().movement();
    }
    //return player 1 values
    public Player returnChar1() {
        return charac1;
    }
    //return player 2 values
    public Player returnChar2() {
        return charac2;
    }

    //sets location of player 1 on where to spawn.
    public void setChar1(int xpos, int ypos) {
        charac1 = new Player(xpos, ypos);
    }

    //sets location of player 2 on where to spawn.
    public void setChar2(int xpos, int ypos) {
        charac2 = new Player(xpos, ypos);
    }

    //Sends playerID value.
    public int getID(){
        return ID;
    }
    
}