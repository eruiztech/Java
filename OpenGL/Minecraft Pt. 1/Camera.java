/***************************************************************
 * file: Camera.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 1
 * date last modified: 10/31/2016
 *
 * purpose: initialize and change the position of the camera 
 * based on user input
 ****************************************************************/
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

public class Camera {

    public Vector3f position;
    private float xOffset = 0f;
    private float yOffset = 0f;

    // method: Camera 
    // purpose: Camera constructor to this object class
    public Camera(float x, float y, float z) {
        position = new Vector3f(x, y, z);
    }

    // method: cameraView 
    // purpose: allows the user to rotate the camera with the mouse
    public void cameraView() {
        glRotatef(xOffset, 1.0f, 0.0f, 0.0f);
        glRotatef(yOffset, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.x, position.y, position.z);
    }

    // method: yaw 
    // purpose: increment the camera's current yaw rotation
    public void yaw(float yawOffset) {
        //increment the yaw by the amount param
        this.yOffset += yawOffset;
    }
    
    // method: pitch
    // purpose: increment the camera's current pitch rotation
    public void pitch(float pitchOffset) {
        //increment the pitch by the amount param
        this.xOffset += pitchOffset;
    }

    // method: walkForward 
    // purpose: moves the camera forward relative to its current rotation
    public void walkForward(float distance) {
        float xOff = distance * (float) Math.sin(Math.toRadians(yOffset));
        float zOff = distance * (float) Math.cos(Math.toRadians(yOffset));
        position.x -= xOff;
        position.z += zOff;
    }

    // method: walkBack 
    // purpose: moves the camera back relative to its current rotation
    public void walkBack(float distance) {
        float xOff = distance * (float) Math.sin(Math.toRadians(yOffset));
        float zOff = distance * (float) Math.cos(Math.toRadians(yOffset));
        position.x += xOff;
        position.z -= zOff;
    }

    // method: walkLeft 
    // purpose: moves the camera left relative to its current rotation
    public void walkLeft(float distance) {
        float xOff = distance * (float) Math.sin(Math.toRadians(yOffset - 90));
        float zOff = distance * (float) Math.cos(Math.toRadians(yOffset - 90));
        position.x -= xOff;
        position.z += zOff;
    }

    // method: walkRight 
    // purpose: moves the camera right relative to its current rotation
    public void walkRight(float distance) {
        float xOff = distance * (float) Math.sin(Math.toRadians(yOffset + 90));
        float zOff = distance * (float) Math.cos(Math.toRadians(yOffset + 90));
        position.x -= xOff;
        position.z += zOff;
    }

    // method: moveUp 
    // purpose: moves the camera up relative to its current rotation
    public void moveUp(float distance) {
        position.y -= distance;
    }

    // method: moveDown 
    // purpose: moves the camera down relative to its current rotation
    public void moveDown(float distance) {
        position.y += distance;
    }

}
