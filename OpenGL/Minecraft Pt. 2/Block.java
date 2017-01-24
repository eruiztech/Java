/***************************************************************
 * file: Block.java
 * authors: E. Ruiz , S. Avila, K. Soni
 * class: CS 445 - Computer Graphics
 *
 * assignment: Final Project Checkpoint 2
 * date last modified: 11/15/2016
 *
 * purpose: Creates the Block object with different based on different
 * types of values
 *
 ******************************************************************/

public class Block {
    private boolean isActive;
    private BlockType Type;
    private float x, y, z;

 /*
    Create a enum blocktype which stores all the different type of value this 
    class consist and different types of blocks and each block has a calue attached
    to it. It also sets the block ative and in active
    */
   public enum BlockType{
    BlockType_Grass(0),
    BlockType_Sand(1),
    BlockType_Water(2),
    BlockType_Dirt(3),
    BlockType_Stone(4),
    BlockType_Bedrock(5);
    private int BlockID;
    
    BlockType(int i) {
        BlockID=i;
    }
    
    public int GetID(){
        return BlockID;
    }

    public void SetID(int i){
        BlockID = i;
    }
}

   
    public Block(BlockType type) {
        this.Type = type;
        isActive = false;
        x = 0;
        y = 0;
        z = 0;
    }

   
    private void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    
    public boolean isActive() {
        return isActive;
    }

  
    public void setActive(boolean active) {
        isActive = active;
    }


    public int getTypeID() {
        return Type.GetID();
    }
}