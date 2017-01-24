/***************************************************************
 * file: Final2.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 2
 * date last modified: 11/15/2016
 *
 * purpose: Displays and creates a 3D 30 x 30 platform made of cubes.
 *          User can navigate around platform.
 *
 ******************************************************************/

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Final2 {

    /* method: start 
     * purpose: starts main functions of program to display 3D platform.
     */
    public void start() {
        try {
            createWindow();
            initGL();
            render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /* method: createWindow 
     * purpose: creates 640x480 window screen titled "Final Program Checkpoint 2"
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Final Program Checkpoint 2");
        Display.create();
    }

    /* method: initGL 
     * purpose: initializing method to create the screen
     */
    private void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(70f, (640f/480f), 0.3f, 1000);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
    }

    /* method: render
     * purpose: draws out 3D platform on screen.
     */
    private void render(){
        Camera camera = new Camera(0f, 0f, 0f);
        float mouseSensitivity = 0.2f;
        float moveSpeed = 0.5f;
        Mouse.setGrabbed(true);
        Chunk chunk = new Chunk(0, 0, 0);

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {          
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            camera.yaw(Mouse.getDX() * mouseSensitivity);
            camera.pitch(Mouse.getDY() * mouseSensitivity);
            move(camera, moveSpeed);
            glLoadIdentity();
            camera.cameraView();
            chunk.render();
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
        Final2 checkTwo = new Final2();
        checkTwo.start();
    }
}