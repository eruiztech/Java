/***************************************************************
 * file: Final3.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 3
 * date last modified: 11/29/2016
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
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Final3 {

    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
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
        Display.setTitle("Final Program Checkpoint 3");
        Display.create();
    }

    /* method: initGL 
     * purpose: initializing method to create the screen and lighting
     */
    private void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(70f, (640f/480f), 0.3f, 1000);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); //sets our light’s position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);//sets our specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);//sets our diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);//sets our ambient light
        glEnable(GL_LIGHTING);//enables our lighting
        glEnable(GL_LIGHT0);//enables light0
    }
    
    /* method: initLightArrays
     * purpose: used to help set up lighting
     */
    private void initLightArrays(){
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
    }

    /* method: render
     * purpose: draws out 3D platform on screen.
     */
    private void render(){
        Camera camera = new Camera(-90f, -54f, -90f);
        float mouseSensitivity = 0.2f;
        float moveSpeed = 1.0f;
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
     * move up with spacebar or down with left shift key. Update - got rid of
     * "else if" from Checkpoint 2 and instead use "if" to enable diagonal movement.
     */
    public static void move(Camera camera, float moveSpeed) {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camera.walkForward(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)) {
            camera.walkLeft(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camera.walkBack(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
            camera.walkRight(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            camera.moveDown(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            camera.moveUp(moveSpeed);
        }
    }
    
    /* method: main
     * purpose: Starts program
     */
    public static void main(String[] args) {
        Final3 checkTwo = new Final3();
        checkTwo.start();
    }
}