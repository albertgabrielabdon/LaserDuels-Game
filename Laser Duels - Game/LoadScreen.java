/**
@author Albert Gabriel P. Abdon (220028)
@version May 15, 2023
**/
/*//creates a separate GUI that will serve only as a loading bar before the main GUI spawns. 
//It acts as a buffer period as to not shock the players to an instant battle.

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

//Starts and instantiates the new frame and the bar length.

public class LoadScreen {

	JFrame frame = new JFrame();
	JProgressBar bar = new JProgressBar(0,550);
	
	//Declares attributes on what to add and design on the bar which is to be added to the farme.
	LoadScreen(){
		
		bar.setValue(0);
		bar.setBounds(0,0,420,200);
		bar.setStringPainted(true);
		bar.setFont(new Font("Dialog Bold",Font.BOLD,25));
		bar.setForeground(Color.red);
		bar.setBackground(Color.black);
			
		frame.add(bar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 420);
		frame.setLayout(null);
		frame.setVisible(true);
		
		fill();
	}

	//the loading value that the bar will base on.
	public void fill() {
		int counter = 0;
		
		while(counter<=550) {
			
			bar.setValue(counter);
			bar.setString("Loading battle...");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter += 5;
		}
		bar.setString("Loading complete!");
	}
}