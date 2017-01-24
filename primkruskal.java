import java.util.Random;

public class primkruskal {
	public static void main(String[] args){
		int arrayLength = 10;	//set length of graph here
		double edgeMultiplier = 1.0;
		double numofEdges = Math.ceil(edgeMultiplier * (((double) arrayLength * ((double) arrayLength - 1)) / 2));
		int[][] graphdoubleArray = new int[arrayLength][arrayLength]; 
		int edgeCount = (int) numofEdges;  //Change to E, 0.2E, 0.4E, 0.6E, 0.8E
		
		costTable(graphdoubleArray, edgeCount); //establish cost table
		if(graphdoubleArray.length <= 10){
			System.out.println("Graph Cost Table\n");
			printnumberHeader(graphdoubleArray);
			printcostTable(graphdoubleArray);
		}
		System.out.println("\nPrim's Algorithm\n");
		final long startTime = System.nanoTime();
		System.out.println(primsAlgorithm(graphdoubleArray));
		final long endTime = System.nanoTime();
		System.out.println("\nPrim's Time: " + (endTime - startTime) + " ns");
		System.out.println("\nKruskal's Algorithm\n");
		System.out.println("To be implemented...");
		//kruskalsAlgorithm(graphdoubleArray);
	}

	public static int primsAlgorithm(int[][] a){
		int[] near = new int[a.length];
		near = nearInitialize(near);
		int[][] T = new int[a.length][2];
		int mCost = 0;
		int j = 0;
		near = nearStart(near);
		
		for(int i = 0; i < a.length-1; i++){
			j = min(a, near);	
			T[i][0] = j;
			T[i][1] = near [j];
			mCost = mCost + Cost(a, j, near[j]);
			near[j] = -1;
		
			for(int k = 0; k < a.length; k++){
				if(near[k] != -1){
					if(Cost(a, k, near[k]) > Cost(a, k, j)){
						near[k] = j;
					}
				}
			}
		}
		
		printStartVertex(T);
		printEndVertex(T);
		System.out.println("\nMinimum Cost of Tree: ");
		return mCost;
	
	}
	
	public static void kruskalsAlgorithm(int[][] a){
		/*int i = 0;
		int mCost = 0;
		int[][] T = new int[a.length][2];
		int[] roots = rootInstantiate(a, roots);
		while(i < (a.length - 1) && heap not empty){
			delete minimum cost edge (u,v) from heap;
			int j = find2(u);
			int k = find2(v);
			if(j != k){
				i++;
				(T[i, 0], T[i, 1]) = (u, v);
				mCost = mCost + Cost(u,v);
				merge3(j, k);
			}
		}
		if(i != n - 1){
			System.out.println("No Spanning Tree");
		}
		*/
		
		/*
		 * The way Kruskal's algorithm works is by sorting all the edge costs
		 * in order using a min_heap. Then it will construct sub trees based on 
		 * the heap and remove edges from the heap when they are used for the subtrees.
		 * Edges will be used based on if they are of low cost and does not cause a cycle 
		 * or A[i][j] != 0. Once all these subtrees are found including each vertex of the tree,
		 * then it will merge these sub trees to make a minimum spanning tree. 
		 * (Ran out of time to completely implement Kruskal's)
		 */
	}
	
	public static int[] nearInitialize(int[] a){
		for(int i = 0; i < a.length; i++){
			if(i == 0){
				a[i] = 0;
			}
			else{
				a[i] = 1;
			}
		}
		return a;
	}
	
	public static int[] nearStart(int[] a){
		a[0] = -1;
		for(int i = 1; i < a.length; i++){
			a[i] = 0;
		}
		return a;
	}
	
	public static void costTable(int[][] a, int edgeCount){
		Random rand = new Random();
		int count = 1;
		for(int i = 0; i < a.length; i++){   //2D array to hold the weights of the edges
			for(int j = i; j < a[i].length; j++){
				if(i == j){
					a[i][j] = 0;
				}
				else{
					if(count <= edgeCount){
						int n = rand.nextInt(10) + 1; 
						a[i][j] = n;
						a[j][i] = a[i][j];
					}
					else{
						a[i][j] = (int) Double.POSITIVE_INFINITY;
						a[j][i] = (int) Double.POSITIVE_INFINITY;
					}
					count++;
				}
			}
		}
	}
	
	public static void printnumberHeader(int[][] a){
		for(int k = 0; k < a.length; k++){
			if(k > 0){
				System.out.print(" " + k);
			}
			else{
				System.out.print("   " + k);
			}
		}
		System.out.println("\n");
	}
	
	public static void printcostTable(int[][] a){
		for(int m = 0; m < a.length; m++) {
			System.out.print(m + " ");
			for(int n = 0; n < a[0].length; n++) {
				if(a[m][n] > 0 && a[m][n] != (int) Double.POSITIVE_INFINITY){
					System.out.print(" " + a[m][n]);
				}
				else if(m == n){
					System.out.print(" " + a[m][n]);
				}
				else{
					System.out.print(" ∞"); //print out infinity when there is no connection to 2 vertices.
				}
			}
		System.out.println();
		}
	}
	
	public static void printStartVertex(int[][] a){
		System.out.print("Start Vertex: ");
		for(int i = 0; i < a.length; i++){
			System.out.print(a[i][1] + " ");
		}
	}
	
	public static void printEndVertex(int[][] a){
		System.out.print("\nEnd Vertex:   ");
		for(int i = 0; i < a.length; i++){
			System.out.print(a[i][0] + " ");
		}
		System.out.println();
	}
	
	public static int Cost(int[][] a, int i, int j){
		return a[i][j];
	}
	
	public static int min(int[][] A, int[] a){
		int mCost = 10;
		int imCost = 0;
		
		for(int i = 0; i < a.length; i++){
			if(a[i] != -1){
				if(Cost(A, i, a[i]) <= mCost){
					mCost = Cost(A, i, a[i]);
					imCost = i;
				}
			}
		}
		return imCost;
	}
	
	public static int[] heapify(int[][] a){
		int[] array = new int[a.length];
		return array;
	}
	
	public static int find2(int[] A, int x){
		int i = x;
		while(A[i] != i){
			i = A[i];
		}
		return i;
	}
	
	public static int[] rootInstantiate(int[][] A, int[] a){
		for(int i = 0; i < A.length; i++){
			int temp = (int) Double.POSITIVE_INFINITY;
			for(int j = i; j < A[i].length; j++){
				if(A[i][j] < temp){
					temp = A[i][j];
					if(i < j){
						a[i] = i; 
					}
					else{
						a[i] = j;
					}
				}
			}
		}
		return a;
	}
	
	
}