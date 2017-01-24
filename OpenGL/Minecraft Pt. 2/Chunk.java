/***************************************************************
 * file: Chunk.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 2
 * date last modified: 11/15/2016
 *
 * purpose: This is the chunk class it handles how the blocks are setup and 
 * rendered , it also get the texture from the file and places it
 *
 ******************************************************************/

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;


public class Chunk {
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;

    private Block[][][] blocks;
    private int VBOVertexHandle;
    private int VBOTextureHandle;   
    private int VBOColorHandle;
    private Texture texture;
    private float startX, startY, startZ;
    private Random r;

    /*
    This method takes in x y z float then usesedthat to build boxes and also this method sets
    up different kind of blocks in the array
    */
    public Chunk(float X, float Y, float Z) {
        try {
            texture = TextureLoader.getTexture(
                "PNG",ResourceLoader.getResourceAsStream("terrain.png"));
        } catch(Exception e){
            System.out.print("Failed to load texture");
        }

        startX = X;
        startY = Y;
        startZ = Z;
        r = new Random();

        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    float f = r.nextFloat();
                    if (f > 0.9f) {
                        blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
                    } else if (f > 0.7f) {
                        blocks[x][y][z] = new Block(Block.BlockType.BlockType_Sand);
                    } else if (f > 0.5f) {
                        blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
                    } else if (f > 0.3f) {
                        blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);
                    } else if (f > 0.1f) {
                        blocks[x][y][z] = new Block(Block.BlockType.BlockType_Stone);
                    } else {
                        blocks[x][y][z] = new Block(Block.BlockType.BlockType_Bedrock);
                    }

                }
            }
        }
        
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        rebuildMesh(startX, startY, startZ);
    }

    /*
    This method renders all the data from texture of the block aswell the size of it
    */
    public void render() {
        glPushMatrix();
        texture.bind();
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBindTexture(GL_TEXTURE_2D,1);
        glTexCoordPointer(2,GL_FLOAT,0,0L);
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        
        glColorPointer(3, GL_FLOAT, 0, 0L);
        
        glDrawArrays(GL_QUADS, 0, CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 24);
        glPopMatrix();
    }
    
    /*
    This method build the mesh with the value of the blocks tell the computer where to create the cube
    */
    public void rebuildMesh(float startX, float startY, float startZ) {
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        VBOColorHandle = glGenBuffers();

        FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer(CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 6 * 12);
        FloatBuffer vertexColorData = BufferUtils.createFloatBuffer(CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 6 * 12);
        FloatBuffer VertexTextureData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);

        SimplexNoise noise = new SimplexNoise(1024, .3, (int)System.currentTimeMillis());

        for (float x = 0; x < CHUNK_SIZE; x++) {
            for (float z = 0; z < CHUNK_SIZE; z++) {

                double height = Math.abs(noise.getNoise((int) x, (int) z) * 100);
                
                if(height == 0){
                    height = 1;
                }
                for (float y = 0; y < height; y++) {

                    if (blocks[(int)x][(int)y][(int)z] != null) {
                        VertexTextureData.put(createTexCube((float) 0,(float) 0,blocks[(int)(x)][(int)(y)][(int)(z)]));

                        vertexPositionData.put(createCube((float)(startX + x * CUBE_LENGTH),(float) startY + y * CUBE_LENGTH,(float)(startZ + z * CUBE_LENGTH)));

                        vertexColorData.put(createCubeVertexCol(getCubeColor(blocks[(int) (x - startX)][(int) (y - startY)][(int) (z - startZ)])));
                    }

                }
            }
        }
        
        VertexTextureData.flip();
        vertexColorData.flip();
        vertexPositionData.flip();
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /*
    This method creates the cubes and place it
    */
    public static float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[] {
                // TOP
                x + offset, y + offset, z,
                x - offset, y + offset, z,
                x - offset, y + offset, z - CUBE_LENGTH,
                x + offset, y + offset, z - CUBE_LENGTH,

                // BOTTOM
                x + offset, y - offset, z - CUBE_LENGTH,
                x - offset, y - offset, z - CUBE_LENGTH,
                x - offset, y - offset, z,
                x + offset, y - offset, z,

                // FRONT
                x + offset, y + offset, z - CUBE_LENGTH,
                x - offset, y + offset, z - CUBE_LENGTH,
                x - offset, y - offset, z - CUBE_LENGTH,
                x + offset, y - offset, z - CUBE_LENGTH,

                // BACK
                x + offset, y - offset, z,
                x - offset, y - offset, z,
                x - offset, y + offset, z,
                x + offset, y + offset, z,

                // LEFT
                x - offset, y + offset, z - CUBE_LENGTH,
                x - offset, y + offset, z,
                x - offset, y - offset, z,
                x - offset, y - offset, z - CUBE_LENGTH,

                //RIGHT
                x + offset, y + offset, z,
                x + offset, y + offset, z - CUBE_LENGTH,
                x + offset, y - offset, z - CUBE_LENGTH,
                x + offset, y - offset, z
        };
    }

    /*
    This method creates a cube vertex color
    */
    private float[] createCubeVertexCol(float[] cubeColorArray) {
        float[] cubeColors = new float[cubeColorArray.length * 6 * 4];
        for (int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = cubeColorArray[i % cubeColorArray.length];
        }
        return cubeColors;
    }

    /*
    This method gets the cube color.
    */
    private float[] getCubeColor(Block block) {
         return new float[] {1, 1, 1};
    }
    
    /*
    This method assigns textures for the cubes
    */
    private static float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f / 16) / 1024f;
        switch (block.getTypeID()) {
            case 0: 
                return new float[]{
                        x + offset * 2, y + offset * 9,
                        x + offset * 3, y + offset * 9,
                        x + offset * 3, y + offset * 10,
                        x + offset * 2, y + offset * 10,

                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,

                        x + offset * 3, y + offset * 0,
                        x + offset * 4, y + offset * 0,
                        x + offset * 4, y + offset * 1,
                        x + offset * 3, y + offset * 1,

                        x + offset * 4, y + offset * 1,
                        x + offset * 3, y + offset * 1,
                        x + offset * 3, y + offset * 0,
                        x + offset * 4, y + offset * 0,
                        
                        x + offset * 3, y + offset * 0,
                        x + offset * 4, y + offset * 0,
                        x + offset * 4, y + offset * 1,
                        x + offset * 3, y + offset * 1,
                        
                        x + offset * 3, y + offset * 0,
                        x + offset * 4, y + offset * 0,
                        x + offset * 4, y + offset * 1,
                        x + offset * 3, y + offset * 1,

                };
            case 1: 
                return new float[]{
                        
                        x + offset * 7, y + offset * 1,
                        x + offset * 8, y + offset * 1,
                        x + offset * 8, y + offset * 2,
                        x + offset * 7, y + offset * 2,
                        
                        x + offset * 7, y + offset * 1,
                        x + offset * 8, y + offset * 1,
                        x + offset * 8, y + offset * 2,
                        x + offset * 7, y + offset * 2,
                        
                        x + offset * 7, y + offset * 1,
                        x + offset * 8, y + offset * 1,
                        x + offset * 8, y + offset * 2,
                        x + offset * 7, y + offset * 2,
                        
                        x + offset * 8, y + offset * 2,
                        x + offset * 7, y + offset * 2,
                        x + offset * 7, y + offset * 1,
                        x + offset * 8, y + offset * 1,
                        
                        x + offset * 7, y + offset * 1,
                        x + offset * 8, y + offset * 1,
                        x + offset * 8, y + offset * 2,
                        x + offset * 7, y + offset * 2,
                        
                        x + offset * 7, y + offset * 1,
                        x + offset * 8, y + offset * 1,
                        x + offset * 8, y + offset * 2,
                        x + offset * 7, y + offset * 2,


                };
            case 2: 
                return new float[]{
                        
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,


                };
            case 3: 
                return new float[]{
                        
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,


                };
            case 4: 
                return new float[]{
                        
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,

                };
            case 5: 
                return new float[]{
                        
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                };

        }
        return null;
    }
}
