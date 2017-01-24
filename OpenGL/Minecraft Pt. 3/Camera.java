/***************************************************************
 * file: Camera.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 3
 * date last modified: 11/29/2016
 *
 * purpose: initialize and change the position of the camera 
 * based on user input
 ****************************************************************/
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

public class Camera {

    private Vector3f position =  null;
    private Vector3f lPosition = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;

    // method: Camera 
    // purpose: Camera constructor to this object class
    public Camera(float x, float y, float z) {
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(90f, 90f, 90f);
    }

    // method: cameraView 
    // purpose: allows the user to rotate the camera with the mouse
    public void cameraView() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(position.x, position.y, position.z);
        
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }

    // method: yaw 
    // purpose: increment the camera's current yaw rotation
    public void yaw(float yawOffset) {
        //increment the yaw by the amount param
        yaw += yawOffset;
    }
    
    // method: pitch
    // purpose: increment the camera's current pitch rotation
    public void pitch(float pitchOffset) {
        //increment the pitch by the amount param
        pitch -= pitchOffset;
    }

    // method: strafeForward 
    // purpose: moves the camera forward relative to its current rotation
    public void walkForward(float distance) {
        float xOff = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOff = distance * (float) Math.cos(Math.toRadians(yaw));
        position.x -= xOff;
        position.z += zOff;
    }

    // method: strafeBack 
    // purpose: moves the camera back relative to its current rotation
    public void walkBack(float distance) {
        float xOff = distance * (float) Math.sin(Math.toRadians(yaw));
        float zOff = distance * (float) Math.cos(Math.toRadians(yaw));
        position.x += xOff;
        position.z -= zOff;
    }

    // method: strafeLeft 
    // purpose: moves the camera left relative to its current rotation
    public void walkLeft(float distance) {
        float xOff = distance * (float) Math.sin(Math.toRadians(yaw - 90));
        float zOff = distance * (float) Math.cos(Math.toRadians(yaw - 90));
        position.x -= xOff;
        position.z += zOff;
    }

    // method: strafeRight 
    // purpose: moves the camera right relative to its current rotation
    public void walkRight(float distance) {
        float xOff = distance * (float) Math.sin(Math.toRadians(yaw + 90));
        float zOff = distance * (float) Math.cos(Math.toRadians(yaw + 90));
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