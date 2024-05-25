import java.awt.Graphics2D;
/*
 * This interface sets the DrawingObjects. It allows me to standardize my drawings in the
 * canvas.
 */
/**
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


public interface DrawingObjects {
    public void draw(Graphics2D g2d);
}