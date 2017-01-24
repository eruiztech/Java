/***************************************************************
 * file: Final.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 1
 * date last modified: 10/31/2016
 *
 * purpose: Displays and creates a 3D cube with six different 
 * colored sides. User can navigate around cube.
 *          
 *
 ******************************************************************/

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Final {

    /* method: start 
     * purpose: creates a block object and renders it to the screen
     */
    public static void start() {
        try {
            createWindow();
            initGL();
            render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /* method: createWindow 
     * purpose: creates 640x480 window screen titled "Final Program Checkpoint 1"
     */
    private static void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Final Program Checkpoint 1");
        Display.create();
    }

    /* method: initGL 
     * purpose: initializing method to create the screen
     */
    private static void initGL() {
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(100.0f, (640f/480f) , 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    /* method: render
     * purpose: draws cube on screen.
     */
    private static void render() {
        Camera camera = new Camera(0f, 0f, 0f);
        float moveSpeed = 1f;
        float mouseSensitivity = 0.1f;
        Mouse.setGrabbed(true);
        Cube cube = new Cube();
        int cubeSize = 7;
        
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            camera.yaw(Mouse.getDX() * mouseSensitivity);
            camera.pitch(-Mouse.getDY() * mouseSensitivity);
            move(camera, moveSpeed);            
            glLoadIdentity();
            camera.cameraView();
            glTranslatef(0f, 0f, -30f);
            cube.drawCube(cubeSize);
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /* method: move 
     * purpose: User can move with the arrow keys or wasd keys. User can also
     * move up with spacebar or down with left shift key.
     */
    public static void move(Camera camera, float moveSpeed) {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camera.walkForward(moveSpeed);
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)) {
            camera.walkLeft(moveSpeed);
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camera.walkBack(moveSpeed);
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
            camera.walkRight(moveSpeed);
        }
         else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            camera.moveDown(moveSpeed);
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            camera.moveUp(moveSpeed);
        }
    }

    /* method: main
     * purpose: Starts program
     */
    public static void main(String[] args) {
        Final checkOne = new Final();
        checkOne.start();
    }

}
