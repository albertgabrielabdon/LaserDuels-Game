/**
 * This program detects movement. Other than that, it also sets up the main GUI in use
 * for the game.
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
import java.awt.event.*;
import javax.swing.*;

//initialize gameframe values.

public class GameFrame extends JComponent implements MouseListener, MouseMotionListener, KeyListener, ActionListener {
    private Timer timer, LaserUpdate, CanvaUpdate;
    private GameCanvas canva;
    private JFrame fram;

    //make the constructor and also establishing Timers as to repeatedly update values such as the laser and repainting the canva.
   public GameFrame(GameCanvas canvas) {

    fram = new JFrame();
    this.canva = canvas;

    timer = new Timer(15,this);
    timer.start();
    LaserUpdate = new Timer(0, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(canva.returnChar1().returnLaserValue()) {
                canva.returnChar1().getLaserX(canva.returnChar1().returnMouseX());
                canva.returnChar1().getLaserY(canva.returnChar1().returnMouseY());
                canva.repaint();
            }
        }
    });
    CanvaUpdate = new Timer(15, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            canva.repaint();
        }
    });

   }

   //a needed method when using actionlisteners.
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    //detects mouse pressed and sets laser visiblity.
    public void mousePressed(MouseEvent e) {
        canva.returnChar1().getMouseX(e.getX());
        canva.returnChar1().getMouseY(e.getY());

        if (canva.returnChar1().returnMouseX() > canva.returnChar1().getX() && canva.returnChar1().returnMouseX() < canva.returnChar1().getX() + canva.returnChar1().getwidth() &&
            canva.returnChar1().returnMouseY() > canva.returnChar1().getY() && canva.returnChar1().returnMouseY() < canva.returnChar1().getY() + canva.returnChar1().getheight()) {
            canva.returnChar1().LaserValue(false);
        } else {
            canva.returnChar1().getLaserX(canva.returnChar1().returnMouseX());
            canva.returnChar1().getLaserY(canva.returnChar1().returnMouseY());
            canva.returnChar1().LaserValue(true);

        }
    }

    //detects mouse released and makes laser invisible.
    public void mouseReleased(MouseEvent e) {
        canva.returnChar1().LaserValue(false);
    }

    //detects mouse movement and updates them to player.
    public void mouseMoved(MouseEvent e) {
        canva.returnChar1().getMouseX(e.getX());
        canva.returnChar1().getMouseY(e.getY());
    }

    //detects mouse movement and updates them to player.
    public void mouseDragged(MouseEvent e) {
        canva.returnChar1().getMouseX(e.getX());
        canva.returnChar1().getMouseY(e.getY());

        canva.returnChar1().getLaserX(canva.returnChar1().returnMouseX());
        canva.returnChar1().getLaserY(canva.returnChar1().returnMouseY());

    }
    //detects mouse clicked
    public void mouseClicked(MouseEvent e) {
    }

    //detects mouse movement and updates them to player.

    public void mouseEntered(MouseEvent e) {
        canva.returnChar1().getMouseX(e.getX());
        canva.returnChar1().getMouseY(e.getY());

        if (canva.returnChar1().returnMouseX() > canva.returnChar1().getX() && canva.returnChar1().returnMouseX() < canva.returnChar1().getX() + canva.returnChar1().getwidth() &&
            canva.returnChar1().returnMouseY() > canva.returnChar1().getY() && canva.returnChar1().returnMouseY() < canva.returnChar1().getY() + canva.returnChar1().getheight()) {
            setCursor(Cursor.getDefaultCursor());
        } else {     
            Image cursorImg = Toolkit.getDefaultToolkit().getImage("aim.png");
            cursorImg = cursorImg.getScaledInstance(25, 15, Image.SCALE_SMOOTH);
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "customCursor");
            setCursor(customCursor);
        }   
    }
    //detects mouse exiting and updates them to player. Laser drawing also stops (may it be visible or not);
    public void mouseExited(MouseEvent e) {
        canva.returnChar1().LaserValue(false);
        LaserUpdate.stop();
    }

    //detects keys being typed
    @Override
    public void keyTyped(KeyEvent e) {
    } 

    //detects the aswd and arrow keys to be responsible for player movement.
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            canva.returnChar1().getLeft(true);
    
  
        } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            canva.returnChar1().getRight(true);
  

        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            if (canva.returnChar1().getY() == getHeight()-50 || (canva.returnChar1().getTJVal() && canva.returnChar1().getTJRem() > 0)) {
                canva.returnChar1().getVelY(-18);
                if (canva.returnChar1().getY() != getHeight()-50) {
                    canva.returnChar1().getTJR(-1);
                }
                if (canva.returnChar1().getTJRem() == 0){
                    canva.returnChar1().getTripleJV(false);
                }
            }
            canva.returnChar1().getUp(true);

        } else if (key == KeyEvent.VK_SPACE){
            canva.returnChar1().LaserValue(true);
            LaserUpdate.start();
        }
    }

    //detects the aswd and arrow keys released to be responsible for player movement.
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            canva.returnChar1().getLeft(false);
  
        } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            canva.returnChar1().getRight(false);
  
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            canva.returnChar1().getUp(false);
   
        } else if  (key == KeyEvent.VK_SPACE){
            canva.returnChar1().LaserValue(false);
            LaserUpdate.stop();
        }
    }

    //sets up the GUI including the name of the game and listeners. It also combined itself
    // with the canvas.
    public void setUpGUI() {
        fram.setTitle("Laser Duels: Player " + canva.getID());
        fram.setLocationRelativeTo(null);
        fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        fram.add(canva);
        
        fram.addMouseListener(this);
        fram.addMouseMotionListener(this);
        fram.addKeyListener(this);
        
        setFocusable(true);
        LaserUpdate.setRepeats(true);
        CanvaUpdate.setRepeats(true);
        fram.pack();
        fram.setVisible(true);

        CanvaUpdate.start();
    }


}

