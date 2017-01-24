/***************************************************************
 * file: Chunk.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 3
 * date last modified: 11/29/2016
 *
 * purpose: This is the chunk class it handles how the blocks are setup and 
 * rendered , it also get the texture from the file and places it according to position
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
    static final int CUBE_LENGTH = 6;

    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOTextureHandle;

    private Texture texture;
  
    private float StartX,StartY, StartZ;
    private Random r;
    
    /*
    This method takes in x y z float then usesedthat to build boxes and also this method sets
    up different kind of blocks in in the array
    */
    
    public Chunk(float X, float Y, float Z) {
        r = new Random();
        try {
            texture = TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream("terrain.png"));
        } catch(Exception e){
            System.out.print("Failed to load texture");
        }
        StartX = X;
        StartY = Y;
        StartZ = Z;
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                   float f = r.nextFloat();
                    if(f > 0.95f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Coal);
                        }
                    else if(f > 0.90f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Copper);
                        }
                    else if(f > 0.86f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Gold);
                       }
                     else if(f > 0.82f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Ruby);
                       }
                    else if(f > 0.90f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Plat);
                       }
                    else {
                         Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);
                    }
                    }
                }
            }
        
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
       
        rebuildMesh(StartX, StartY, StartZ);
    }
    /*
    This method renders all the data from texture of the block aswell the size of it
    */
    public void render(){
        glPushMatrix();
        texture.bind();
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBindTexture(GL_TEXTURE_2D,1);
        glTexCoordPointer(2,GL_FLOAT,0,0L);
        
        glBindBuffer(GL_ARRAY_BUFFER,VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
       
        
        glColorPointer(3,GL_FLOAT, 0, 0L);
        glDrawArrays(GL_QUADS, 0,CHUNK_SIZE *CHUNK_SIZE*CHUNK_SIZE * 24);
        glPopMatrix();
    }

    /*
    This method build the mesh with the value of the blocks tell the computer where to create the cube
    */
    public void rebuildMesh(float startX, float startY, float startZ) {
        SimplexNoise noise = new SimplexNoise(70, 0.07, r.nextInt());
   
       
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
       
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        
        FloatBuffer VertexTextureData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        for (float x = 0; x < CHUNK_SIZE; x++) {
            for (float z = 1; z < CHUNK_SIZE; z++) {
                float height = Math.abs(StartY + (int) (70 * noise.getNoise((int) x, (int) z)) * CUBE_LENGTH);
                for (float y = 0; y <= height; y++) {
                    if(y == 0){
 
                        Blocks[(int) x][(int)y][(int) z] = new Block(Block.BlockType.BlockType_Bedrock);

                        Blocks[(int) x][(int)y][(int) z].setActive(true);
                        VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH),
                                (float) ((int)y * CUBE_LENGTH + (int) (CHUNK_SIZE * .8)),
                                (float) (startZ + z * CUBE_LENGTH)));
                       
                        VertexTextureData.put(createTexCube((float) 0, (float) 0, Blocks[(int) (x)][(int) y][(int) (z)]));
                    }
                  
                    if (y == height && y != 0) {
                        Blocks[(int) x][(int) height][(int) z] = new Block(Block.BlockType.BlockType_Grass);
                        Blocks[(int) x][(int) y][(int) z].setActive(true);
                        
                    }
                  
                    VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH),
                            (float) (y * CUBE_LENGTH + (int) (CHUNK_SIZE * .8)),
                            (float) (startZ + z * CUBE_LENGTH)));
                    VertexTextureData.put(createTexCube((float) 0, (float) 0, Blocks[(int) (x)][(int) (y)][(int) (z)]));
                }
                
                if (!Blocks[(int) x][1][(int) z].isActive()) {
                    for (int i = 1; i < 3; i++) {
                          if (i == 1){
                             Blocks[(int) x][i][(int) z] = new Block(ores());

                        Blocks[(int) x][i][(int) z].setActive(true);
                        VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH),
                                (float) (i * CUBE_LENGTH + (int) (CHUNK_SIZE * .8)),
                                (float) (startZ + z * CUBE_LENGTH)));
                       
                        VertexTextureData.put(createTexCube((float) 0, (float) 0, Blocks[(int) (x)][i][(int) (z)]));
                        }
                        if(i == 2){
                            int rand = r.nextInt(2)+1;
                            if(rand == 1)
                                Blocks[(int) x][i][(int) z] = new Block(Block.BlockType.BlockType_Sand);
                            else
                                Blocks[(int) x][i][(int) z] = new Block(Block.BlockType.BlockType_Water);
                        }
                      
                        else{
                        Blocks[(int) x][i][(int) z] = new Block(Block.BlockType.BlockType_Water);
                        }
                        Blocks[(int) x][i][(int) z].setActive(true);
                        VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH),
                                (float) (i * CUBE_LENGTH + (int) (CHUNK_SIZE * .8)),
                                (float) (startZ + z * CUBE_LENGTH)));
                       
                        VertexTextureData.put(createTexCube((float) 0, (float) 0, Blocks[(int) (x)][i][(int) (z)]));

                    }
                }
            }
        }
        
        VertexPositionData.flip();
        VertexTextureData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
  
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /* method: ores
     * purpose: This method returns a random ore cube that will be placed under water or sand cubes.
     */
    private Block.BlockType ores(){
        float f = r.nextFloat();
        
        if(f > 0.95f){
            return Block.BlockType.BlockType_Coal;
        }
        else if(f > 0.90f){
            return Block.BlockType.BlockType_Copper;
        }
        else if(f > 0.86f){
            return Block.BlockType.BlockType_Gold;
        }
         else if(f > 0.82f){
            return Block.BlockType.BlockType_Ruby;
        }
        else if(f > 0.90f){
            return Block.BlockType.BlockType_Plat;
        }
        else {
             return Block.BlockType.BlockType_Stone;
        }
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


    private static float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f / 16) / 1024f;
        switch (block.getTypeID()) {
            case 0: 
                return new float[]{
                   
                        x + offset * 2, y + offset * 9,
                        x + offset * 3, y + offset * 9,
                        x + offset * 3, y + offset * 10,
                        x + offset * 2, y + offset * 10,
                        // TOP!
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        // FRONT QUAD
                        x + offset * 3, y + offset * 0,
                        x + offset * 4, y + offset * 0,
                        x + offset * 4, y + offset * 1,
                        x + offset * 3, y + offset * 1,
                        // BACK QUAD
                        x + offset * 4, y + offset * 1,
                        x + offset * 3, y + offset * 1,
                        x + offset * 3, y + offset * 0,
                        x + offset * 4, y + offset * 0,
                        // LEFT QUAD
                        x + offset * 3, y + offset * 0,
                        x + offset * 4, y + offset * 0,
                        x + offset * 4, y + offset * 1,
                        x + offset * 3, y + offset * 1,
                        // RIGHT QUAD
                        x + offset * 3, y + offset * 0,
                        x + offset * 4, y + offset * 0,
                        x + offset * 4, y + offset * 1,
                        x + offset * 3, y + offset * 1,

                };
                
            //Updated sand texture from Checkpoint 2 (maybe we were using gold?)
            case 1: 
                return new float[]{
              
                    x + offset * 3, y + offset * 2,
                    x + offset * 2, y + offset * 2,
                    x + offset * 2, y + offset * 1,
                    x + offset * 3, y + offset * 1,
                    // TOP
                    x + offset * 3, y + offset * 2,
                    x + offset * 2, y + offset * 2,
                    x + offset * 2, y + offset * 1,
                    x + offset * 3, y + offset * 1,
                    // FRONT QUAD
                    x + offset * 3, y + offset * 2,
                    x + offset * 2, y + offset * 2,
                    x + offset * 2, y + offset * 1,
                    x + offset * 3, y + offset * 1,
                    // BACK QUAD
                    x + offset * 3, y + offset * 2,
                    x + offset * 2, y + offset * 2,
                    x + offset * 2, y + offset * 1,
                    x + offset * 3, y + offset * 1,
                    // LEFT QUAD
                    x + offset * 3, y + offset * 2,
                    x + offset * 2, y + offset * 2,
                    x + offset * 2, y + offset * 1,
                    x + offset * 3, y + offset * 1,
                    // RIGHT QUAD
                    x + offset * 3, y + offset * 2,
                    x + offset * 2, y + offset * 2,
                    x + offset * 2, y + offset * 1,
                    x + offset * 3, y + offset * 1
            };
           
            case 2: 
                return new float[]{
                   
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        // TOP QUAD
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        // FRONT QUAD
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        // BACK QUAD
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        // LEFT QUAD
                        x + offset * 15, y + offset * 12,
                        x + offset * 16, y + offset * 12,
                        x + offset * 16, y + offset * 13,
                        x + offset * 15, y + offset * 13,
                        // RIGHT QUAD
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
                        // TOP QUAD
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        // FRONT QUAD
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        // BACK QUAD
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        // LEFT QUAD
                        x + offset * 2, y + offset * 0,
                        x + offset * 3, y + offset * 0,
                        x + offset * 3, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        // RIGHT QUAD
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
                        // TOP QUAD
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        // FRONT QUAD
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        // BACK QUAD
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        // LEFT QUAD
                        x + offset * 6, y + offset * 0,
                        x + offset * 7, y + offset * 0,
                        x + offset * 7, y + offset * 1,
                        x + offset * 6, y + offset * 1,
                        // RIGHT QUAD
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
                       // TOP QUAD
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        // FRONT QUAD
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        // BACK QUAD
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        // LEFT QUAD
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        // RIGHT QUAD
                        x + offset * 1, y + offset * 1,
                        x + offset * 2, y + offset * 1,
                        x + offset * 2, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                };
                case 6:
                return new float[]{
                      
                        x + offset * 2, y + offset * 2,
                        x + offset * 3, y + offset * 3,
                        x + offset * 3, y + offset * 3,
                        x + offset * 2, y + offset * 2,
                        // TOP QUAD
                        x + offset * 2, y + offset * 2,
                        x + offset * 3, y + offset * 2,
                        x + offset * 3, y + offset * 3,
                        x + offset * 2, y + offset * 3,
                        // FRONT QUAD
                        x + offset * 2, y + offset * 2,
                        x + offset * 3, y + offset * 2,
                        x + offset * 3, y + offset * 3,
                        x + offset * 2, y + offset * 3,
                        // BACK QUAD
                        x + offset * 3, y + offset * 3,
                        x + offset * 2, y + offset * 3,
                        x + offset * 2, y + offset * 2,
                        x + offset * 3, y + offset * 2,
                        // LEFT QUAD
                        x + offset * 2, y + offset * 2,
                        x + offset * 3, y + offset * 2,
                        x + offset * 3, y + offset * 3,
                        x + offset * 2, y + offset * 3,
                        // RIGHT QUAD
                        x + offset * 2, y + offset * 2,
                        x + offset * 3, y + offset * 2,
                        x + offset * 3, y + offset * 3,
                        x + offset * 2, y + offset * 3,
                };
            case 7:
                return new float[]{
                      
                        x + offset * 1, y + offset * 2,
                        x + offset * 2, y + offset * 2,
                        x + offset * 2, y + offset * 3,
                        x + offset * 1, y + offset * 3,
                       // TOP QUAD
                        x + offset * 1, y + offset * 2,
                        x + offset * 2, y + offset * 2,
                        x + offset * 2, y + offset * 3,
                        x + offset * 1, y + offset * 3,
                        // FRONT QUAD
                        x + offset * 1, y + offset * 2,
                        x + offset * 2, y + offset * 2,
                        x + offset * 2, y + offset * 3,
                        x + offset * 1, y + offset * 3,
                        // BACK QUAD
                        x + offset * 2, y + offset * 3,
                        x + offset * 1, y + offset * 3,
                        x + offset * 1, y + offset * 2,
                        x + offset * 2, y + offset * 2,
                        // LEFT QUAD
                        x + offset * 1, y + offset * 2,
                        x + offset * 2, y + offset * 2,
                        x + offset * 2, y + offset * 3,
                        x + offset * 1, y + offset * 3,
                        // RIGHT QUAD
                        x + offset * 1, y + offset * 2,
                        x + offset * 2, y + offset * 2,
                        x + offset * 2, y + offset * 3,
                        x + offset * 1, y + offset * 3,
                };
            case 8:
                return new float[]{
                      
                        x + offset * 0, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        x + offset * 1, y + offset * 3,
                        x + offset * 0, y + offset * 3,
                       // TOP QUAD
                        x + offset * 0, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        x + offset * 1, y + offset * 3,
                        x + offset * 0, y + offset * 3,
                        // FRONT QUAD
                        x + offset * 0, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        x + offset * 1, y + offset * 3,
                        x + offset * 0, y + offset * 3,
                        // BACK QUAD
                        x + offset * 1, y + offset * 3,
                        x + offset * 0, y + offset * 3,
                        x + offset * 0, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        // LEFT QUAD
                        x + offset * 0, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        x + offset * 1, y + offset * 3,
                        x + offset * 0, y + offset * 3,
                        // RIGHT QUAD
                        x + offset * 0, y + offset * 2,
                        x + offset * 1, y + offset * 2,
                        x + offset * 1, y + offset * 3,
                        x + offset * 0, y + offset * 3,
                };
            case 9:
                return new float[]{
                      
                        x + offset * 3, y + offset * 4,
                        x + offset * 4, y + offset * 4,
                        x + offset * 4, y + offset * 4,
                        x + offset * 3, y + offset * 3,
                        // TOP QUAD
                        x + offset * 3, y + offset * 3,
                        x + offset * 4, y + offset * 3,
                        x + offset * 4, y + offset * 4,
                        x + offset * 3, y + offset * 4,
                        // FRONT QUAD
                        x + offset * 3, y + offset * 3,
                        x + offset * 4, y + offset * 3,
                        x + offset * 4, y + offset * 4,
                        x + offset * 3, y + offset * 4,
                        // BACK QUAD
                        x + offset * 4, y + offset * 4,
                        x + offset * 3, y + offset * 4,
                        x + offset * 3, y + offset * 3,
                        x + offset * 4, y + offset * 3,
                        // LEFT QUAD
                        x + offset * 3, y + offset * 3,
                        x + offset * 4, y + offset * 3,
                        x + offset * 4, y + offset * 4,
                        x + offset * 3, y + offset * 4,
                        // RIGHT QUAD
                        x + offset * 3, y + offset * 3,
                        x + offset * 4, y + offset * 3,
                        x + offset * 4, y + offset * 4,
                        x + offset * 3, y + offset * 4,
                };
            case 10:
                return new float[]{
                      
                        x + offset * 2, y + offset * 4,
                        x + offset * 3, y + offset * 4,
                        x + offset * 3, y + offset * 4,
                        x + offset * 2, y + offset * 3,
                        // TOP QUAD
                        x + offset * 2, y + offset * 3,
                        x + offset * 3, y + offset * 3,
                        x + offset * 3, y + offset * 4,
                        x + offset * 2, y + offset * 4,
                        // FRONT QUAD
                        x + offset * 2, y + offset * 3,
                        x + offset * 3, y + offset * 3,
                        x + offset * 3, y + offset * 4,
                        x + offset * 2, y + offset * 4,
                        // BACK QUAD
                        x + offset * 3, y + offset * 4,
                        x + offset * 2, y + offset * 4,
                        x + offset * 2, y + offset * 3,
                        x + offset * 3, y + offset * 3,
                        // LEFT QUAD
                        x + offset * 2, y + offset * 3,
                        x + offset * 3, y + offset * 3,
                        x + offset * 3, y + offset * 4,
                        x + offset * 2, y + offset * 4,
                        // RIGHT QUAD
                        x + offset * 2, y + offset * 3,
                        x + offset * 3, y + offset * 3,
                        x + offset * 3, y + offset * 4,
                        x + offset * 2, y + offset * 4,
                };

        }
        return null;    
    }
}