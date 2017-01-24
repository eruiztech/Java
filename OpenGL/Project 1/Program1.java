/* Edgar Ruiz
 * CS 445
 * Program 1
 * October 6, 2016
 */

/* Program1 reads in each line from "coordinates.txt" and draws either 
 * a line, circle, or ellipse based on the first letter of the line 
 * and the number values following.
 *
 * Note: Please deposit "coordinates.txt" in folder labeled 
 *       "coordinates" under the "src" folder in order for 
 *       the program to run properly.
 */

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import org.lwjgl.input.Keyboard;

public class Program1{
    
    /* start() includes the necessary methods to draw with OpenGl.
     */
    public void start(){
        try{
            createWindow();
            initGL();
            render();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /* createWindow() creates a window the size of (width) 640 x 480 (height),
     * with the title "Program 1 - Draw Lines, Circles, and Ellipses".
     */
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 1 - Draw Lines, Circles, and Ellipses");
        Display.create();
    }    

    /* initGL() specifies background color (glClearColor), loads camera
     * using projection to view scene (glMatrixMode), loads identity matrix
     * (glLoadIdentity), sets up orthographic matrix with the size 640 x 480
     * with a clipping distance between 1 and -1 (glOrtho), sets up scene to
     * Model view (glMatrixMode), and provides rendeing hints (glHint).
     */
    private void initGL(){
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
        
    /* render() is called every frame by containing a loop. Window will 
     * close when closing the window is requested or when the ESC key is pressed 
     * on the keyboard. Each line from "coordinates.txt", located in the 
     * coordinates folder under src, will be read. When a line is read, the 
     * first letter (draw) - which could be "l", "e", or "c" - will be tokenized and 
     * the integers following that are seperated by either blank space or a ",". 
     * These tokens will be saved into a String array the size of the amount 
     * of tokens on that line. Once saved into the String array, 'draw' 
     * will be checked to see if it equals "l", "e", or "c". Based on which 
     * letter 'draw' equaled to, the remaining saved tokens will be turned
     * into integers and sent as parameters to the appropriate method to draw 
     * a line, circle, or ellipse.
     */
    private void render() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            try{
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                try(BufferedReader br = new BufferedReader(new FileReader("src/coordinates/coordinates.txt"))){
                    String coordinates;
                    while ((coordinates = br.readLine()) != null){
                        StringTokenizer tokenizer = new StringTokenizer(coordinates," ,");
                        String[] tokens = new String [tokenizer.countTokens()];
                        for(int i = 0; i < tokens.length; i++){
                            tokens[i] = tokenizer.nextToken();
                        }                        
                        String draw = tokens[0];                       
                        if(draw.equals("l")){
                            int x0 = Integer.parseInt(tokens[1]);
                            int y0 = Integer.parseInt(tokens[2]);
                            int x1 = Integer.parseInt(tokens[3]);
                            int y1 = Integer.parseInt(tokens[4]);
                            drawLine(x0, y0, x1, y1);
                        }  
                        else if(draw.equals("c")){
                            int x = Integer.parseInt(tokens[1]);
                            int y = Integer.parseInt(tokens[2]);
                            int r = Integer.parseInt(tokens[3]);
                            drawCircle(x, y, r);
                        }
                        else if(draw.equals("e")){
                            int x = Integer.parseInt(tokens[1]);
                            int y = Integer.parseInt(tokens[2]);
                            int xRad = Integer.parseInt(tokens[3]);
                            int yRad = Integer.parseInt(tokens[4]);
                            drawEllipse(x, y, xRad, yRad);
                        }                        
                        else{
                            System.out.println("Incorrect coordinate format.");
                            System.exit(0);
                        }
                    }
                } 
                catch (IOException e){
                }
                Display.update();
                Display.sync(60);
            }
            catch(Exception e){
            }
        }
        Display.destroy();
    }
    
    /* drawLine(...) draws red points on the window to create a line using the midpoint 
     * algorithm and the two endpoints given as parameters. The first endpoint is
     * defined by the first two parameters (x0, y0) and the second endpoint is
     * the last two parameters (x1, y1). Change in x is defined by dx = x1 - x0 and
     * change in y is defined by dy = y1 - y0. IncrementRight (iR) is how much is needed
     * to move right (2 * dy) and incrementUpRight (iUR) is how much is need to move up and 
     * right (2 * (dy - dx)). The distance of the midpoint is initially defined by d = (2 * dy) - dx.
     * The values of 'x' and 'y' are initialized at x0 and y0. We must check that -1 < slope < 1 
     * is true in order to use the Midpoint alogrithm. -- If it is true, we add 1 to just x 
     * (incrementRight) if d is a negative number, add the value of incrementRight to d, 
     * and draw the point. We add 1 to x and y (incrementUpRight) if d is a positive number, 
     * add the value of incrementUpRight to d, and draw the point. -- If -1 < slope < 1 is false, 
     * we take a different approach. We add 1 to just x if d is a positive number, add incrememtRight
     * to d, and draw the point. We add 1 to x and y if d ia negative number, add the value of
     * incrementUpRight to d and draw the point. This process continues until the value of x 
     * reaches the value of x1. A line is displayed at the end.
     */
    public void drawLine(int x0, int y0, int x1, int y1){
        glColor3f(1.0f, 0.0f, 0.0f);
        int dx = x1 - x0;
        int dy = y1 - y0;
        int iR = 2 * dy;
        int iUR = 2 * (dy - dx);
        int d = (2 * dy) - dx;
        int x = x0;
        int y = y0;
        int slope = dy / dx;
        glBegin(GL_POINTS);
        glVertex2f(x0, y0);
        while(x < x1){
            if(-1 < slope && slope < 1){
                if(d > 0){
                    d += iUR;
                    x++;
                    y++;
                }
                else{
                    d += iR;
                    x++;     
                }
            }
            else{
                if(d < 0){
                    d += iUR;
                    x++;
                    y--;
                }
                else{
                    d += iR;
                    x++;
                }
            }
            glVertex2f(x, y);
        }
        glEnd();
    }
    
    /* drawCircle(...) draws blue points along the circumference of a circle from theta = 0 to 
     * theta = 2*pi by using the x, y, and radius (r) values from the parameters. The 
     * x value (cosX) is defined as the radius times the cosine of current theta. The y value 
     * (sinY) is defined as the radius times the sine of current theta. A point is drawn 
     * using the center of the circle offset by the x and y values calculated inside the while loop.
     * Theta is incremented by pi/360 after each point drawn.
     */
    public void drawCircle(int x, int y, int r){
        glColor3f(0.0f, 0.0f, 1.0f);
        double theta = 0.0;
        double increment = Math.PI / 360;
        glBegin(GL_POINTS);
        while(theta <= (2 * Math.PI)){
            double cosX = r * Math.cos(theta);
            double sinY = r * Math.sin(theta);
            int newX = (int) (x + cosX);
            int newY = (int) (y + sinY);
            theta += increment;
            glVertex2f(newX, newY);
        }
        glEnd();
    }
    
     /* drawEllipse(...) draws green points along the circumference of an ellipse from theta = 0
     * to theta = 2*pi by using the x, y, and two radii values (X-radius, Y-radius) from the 
     * parameters. The x value is defined as the X-radius times the cosine of current theta.
     * The y value is defined as the Y-radius times the sine of current theta. A point is drawn 
     * using the center of the ellipse offset by the x and y values calculated inside the loop. 
     * Theta is incremented by pi/360 after each point drawn.
     */
    public void drawEllipse(int x, int y, int xRad, int yRad){
        glColor3f(0.0f, 1.0f, 0.0f);
        double theta = 0.0;
        double increment = Math.PI / 360;
        glBegin(GL_POINTS);
        while(theta <= (2 * Math.PI)){
            double cosX = xRad * Math.cos(theta);
            double sinY = yRad * Math.sin(theta);
            int newX = (int) (x + cosX);
            int newY = (int) (y + sinY);
            theta += increment;
            glVertex2f(newX, newY);
        }
        glEnd();
    }
    
    /* This is the main method that will run the program.
     */
    public static void main(String[] args){
        Program1 program = new Program1();
        program.start();
    }
}