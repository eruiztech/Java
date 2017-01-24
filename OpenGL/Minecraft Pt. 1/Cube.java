/***************************************************************
 * file: Cube.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 1
 * date last modified: 10/31/2016
 *
 * purpose: The purpose of the project is to draw the cube based on the size we get from the final class
 * This class also colors all the sides of the cube
 *
 ****************************************************************/

import static org.lwjgl.opengl.GL11.*;

public class Cube {
    /*
     This method draws the cube and gets the @i from the final class
     */
   public void drawCube(int i) {
       float size = (float)i;
        glBegin(GL_QUADS);
        // THE TOP
        glColor3f(0.5f, 0.0f, 1.0f); // PURPLE
        glVertex3f(size, size, -size);
        glVertex3f(-size, size, -size);
        glVertex3f(-size, size, size);
        glVertex3f(size, size, size);
        //bottom 
        glColor3f(1.0f, 0.0f, 0.0f); // RED
        glVertex3f(size, -size, size);
        glVertex3f(-size, -size, size);
        glVertex3f(-size, -size, -size);
        glVertex3f(size, -size, -size);
        //front
        glColor3f(0.0f, 1.0f, 0.0f); // GREEN
        glVertex3f(size, size, size);
        glVertex3f(-size, size, size);
        glVertex3f(-size, -size, size);
        glVertex3f(size, -size, size);
        //back
        glColor3f(1.0f, 1.0f, 0.0f); // YELLOW
        glVertex3f(size, -size, -size);
        glVertex3f(-size, -size, -size);
        glVertex3f(-size, size, -size);
        glVertex3f(size, size, -size);
        //Right
        glColor3f(0.0f, 0.3f, 0.2f); // DarkGreen(Grass Green?)
        glVertex3f(-size, size, size);
        glVertex3f(-size, size, -size);
        glVertex3f(-size, -size, -size);
        glVertex3f(-size, -size, size);
        //Left
        glColor3f(0.0f, 1.0f, 1.0f); // CYAN color
        glVertex3f(size, size, -size);
        glVertex3f(size, size, size);
        glVertex3f(size, -size, size);
        glVertex3f(size, -size, -size);

        glEnd();
    }
}
