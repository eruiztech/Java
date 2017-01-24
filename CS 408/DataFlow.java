/* Edgar Ruiz
   CS 408
   May 11, 2016
*/

public class DataFlow{
	public static void main(String[] args){
		Block[] blocks = new Block[4];
		blocks[0] = new Block(new int[]{0,0,0,1,1,1,1}, new int[]{1,1,1,0,0,0,0}, new int[]{0,0,0,0}, new int[]{0,0,0,0,0,0,0}, new int[]{1,1,1,0,0,0,0});
		blocks[1] = new Block(new int[]{1,1,0,0,0,0,1}, new int[]{0,0,0,1,1,0,0}, new int[]{1,0,1,1}, new int[]{0,0,0,0,0,0,0}, new int[]{0,0,0,1,1,0,0});
		blocks[2] = new Block(new int[]{0,0,1,0,0,0,0}, new int[]{0,0,0,0,0,1,0}, new int[]{0,1,0,0}, new int[]{0,0,0,0,0,0,0}, new int[]{0,0,0,0,0,1,0});
		blocks[3] = new Block(new int[]{1,0,0,1,0,0,0}, new int[]{0,0,0,0,0,0,1}, new int[]{0,1,0,0}, new int[]{0,0,0,0,0,0,0}, new int[]{0,0,0,0,0,0,1});

		boolean change = true;
		int count = 0;
		int[] temp = new int[7];
		int[] oldout = new int[7];
		
		while(change){
			change = false;
			System.out.println("Iteration #: " + (count++));
			for(int i = 0; i < 4; i++){
				for(int j = 0; j < 4; j++){
					if(blocks[i].getPredElement(j) == 1){
						temp = blocks[i].copyFromIn();
						temp = blocks[j].unionOut(temp);
						blocks[i].copyToIn(temp);
						for(int m = 0; m < 7; m++){
							if(blocks[i].getInElement(m) == 1){
								System.out.print("d" + (m + 1) + " ");
							}
						}
						System.out.println();
					}
				}
				blocks[i].copyFromOut(oldout);
				temp = blocks[i].copyFromIn();
				temp = blocks[i].diff(temp);
				temp = blocks[i].unionGen(temp);
				blocks[i].copyToOut(temp);
				System.out.println("Oldout");
				for(int k = 0; k < 7; k++){
					if(oldout[k] == 1){
						System.out.print("d" + (k + 1) + " ");
					}
				}
				System.out.println("\nOut");
				for(int l = 0; l < 7; l++){
					if(blocks[i].getOutElement(l) == 1){
						System.out.print("d" + (l + 1) + " ");
					}
				}
				System.out.println();
				if(blocks[i].equalSet(oldout) != true){
					System.out.println("Not Equal");
					change = true;
				}
			}
		}
		System.out.print("\nFinal In Sets\n");
		for(int i = 0; i < 4; i++){
			System.out.print("B" + (i+1) + ": ");
			for(int j = 0; j < 7; j++){
				if(blocks[i].getInElement(j) == 1){
					System.out.print("d" + (j + 1) + " ");
				}
			}
			System.out.println();
		}
		System.out.print("\nFinal Out Sets\n");
		for(int i = 0; i < 4; i++){
			System.out.print("B" + (i+1) + ": ");
			for(int j = 0; j < 7; j++){
				if(blocks[i].getOutElement(j) == 1){
					System.out.print("d" + (j + 1) + " ");
				}
			}
			System.out.println();
		}
	}
}