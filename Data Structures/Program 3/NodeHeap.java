/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #1
 *
 * NodeHeap: Implementation of a heap using nodes.
 * 
 * @author Edgar Ruiz
 */
package edu.csupomona.cs.cs241.prog_assgmnt_1;

/**
 * Class: NodeHeap
 * 
 * Heap implementation that uses nodes instead of 
 * an array. 
 *
 * @param <V> Desired Data Type.
 */
public class NodeHeap<V extends Comparable<V>> implements Heap<V>{

	@SuppressWarnings("rawtypes")
	
	/**
	 * Stores top element of heap.
	 * 
	 */
	private Node root;
	
	/**
	 * Sets heap to be a max or min heap.
	 */
	private edu.csupomona.cs.cs241.prog_assgmnt_1.Heap.MODE myMode; 
	
	/**
	 * Holds the number of elements in heap.
	 */
	public int count = 0;     
	
	/** 
	 * NodeHeap constructor sets root to null.
	 * @param mode Set the heap to be max or min.
	 */
	public NodeHeap(edu.csupomona.cs.cs241.prog_assgmnt_1.Heap.MODE mode){
		root = null;
		myMode = mode;
	}

	
	/**
	 * Adds element to next available place in heap.
	 * After element is added, it is moved to the correct location
	 * on the heap based on the max or min heap property. 
	 * @param value Value of the new element.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void add(V value){ 
		Node newNode;
		if(count == 0){
			newNode = new Node(value, null, null, root);
			root = newNode; 
			count++;
		}
		else{
			int nextSpot = count + 1;
			String binarySpot = toBinaryString(nextSpot);	
			for(int i = 1; i < binarySpot.length()-1; i++){ 
				String c = binarySpot.substring(i, i+1);
				if(c.compareTo("0") == 0)
					root = root.left;
				else if(c.compareTo("1") == 0)
					root = root.right;	
			}
			int lastNumber = binarySpot.length()-1;
			if(binarySpot.substring(lastNumber).compareTo("0") == 0){
				newNode = new Node(value, null, null, root);
				root.left = newNode; 
				count++;
			}
			else{
				newNode = new Node(value, null, null, root);
				root.right = newNode; 
				count++;
			}
			siftUp(newNode);
		}
		resetRoot();
	}
	
	/**
	 * Removes the top element of the heap and returns the value of
	 * the element that was removed.
	 * if max heap property, the largest element in the heap will be removed.
	 * if min heap property, the smallest element in the heap will be removed.
	 * @return The value of the element that was removed.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public V remove(){
		V value;
		if(count > 0){
			resetRoot();								
			value = (V) root.elem;						
			String st = toBinaryString(count);		
			Node lastNode = getNode(st);						
			resetRoot();								
			swapElements(lastNode, root);						
			int parentOfRemove = (count) / 2;			
			String str = toBinaryString(parentOfRemove);		
			Node parent = getNode(str);						
			if(isOdd(count)){							
				parent.right = null;
			}
			else if(isEven(count)){
				parent.left = null;
			}
			count--;									
			resetRoot();
			siftDown(root);
		}
		else{
			System.out.println("Heap is empty");
			return null;
		}		
		return (V) value;
	}

	/**
	 * Copies each node from the heap into an array.
	 * @return The heap as an array.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public V[] toArray(){
		Object[] array = null;	
		if(count > 0){
			array = new Object[count];				
			Node n = null;										
			for(int i = 0; i < array.length; i++){				
				String binaryLocation = toBinaryString(i+1); 	
				n = getNode(binaryLocation);					
				array[i] = (V) n.elem;							
			}
			V[] result = (V[]) java.lang.reflect.Array.newInstance(array[0].getClass(), count);
			System.arraycopy(array, 0, result, 0, count);
			resetRoot();
			return (V[]) result;
		}		
		else{
			System.out.println("Heap is empty");
			return (V[]) array;
		}		
	}
	
	/**
	 * Gets array and places elements into a heap
	 * that follows the heap property.
	 * @param array Array with elements to add to heap.
	 */
	public void fromArray(V[] array){
		for(int i = 0; i < array.length; i++) {
			add((V) array[i]);						
		}
	}

	/**
	 * Takes heap and rearranges nodes to follow the
	 * heap property.
	 */
	@SuppressWarnings("rawtypes")
	public void heapify(){
		if (count > 1){							
			for (int i = count / 2; i > 0; i--){	
				String str = toBinaryString(i);			
				Node nn = getNode(str);				
				siftDown(nn);						
			}
		}
	}
	
	public int size(){
		return count;
	}

	/**
	 * Returns an array that contains values of the heap, 
	 * sorted based on the heap property.
	 * if max heap property, the array will contain values sorted from greatest to least.
	 * if min heap property, the array will contain values sorted from least to greatest.
	 * @return Array with sorted elements.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public V[] getSortedContents(){
		V[] result = null;
		int elemNumber = count;
		heapify();
		resetValidity();
		if(count > 0){											
			while(elemNumber > 1){	
				String x = toBinaryString(elemNumber);						
				Node y = getNode(x);							
				resetRoot();									
				swapElements(root, y);				
				elemNumber--;									
				y.isValid = false;
				resetRoot();
				siftDownUntilSpecified(root);
			}
			result = toArray();
			resetValidity();
			heapify();											
		}
		return result;
	}
	
	/**
	 * Returns the mode of the heap.
	 * @return Mode of the heap.
	 */
	public edu.csupomona.cs.cs241.prog_assgmnt_1.Heap.MODE getMode(){
		return myMode;
	}

	/**
	 * Change the mode of the heap to be either max or min.
	 * @param mode Heap mode.
	 */
	public void setMode(edu.csupomona.cs.cs241.prog_assgmnt_1.Heap.MODE mode){
		myMode = mode;
		heapify();
	}
	
	/**
	 * if max heap property, node will move up if it is larger than it's parent.
	 * if min heap property, node will move up if it is smaller than it's parent.
	 * Node will keep moving up until heap property is satisfied.
	 * @param toFix Node being fixed.
	 */
	@SuppressWarnings("rawtypes")
	private void siftUp(Node toFix){	
		Node parent = toFix.parent;
		while(parent != null && compareNodes(toFix, parent)){
				swapElements(toFix, parent);
				toFix = parent;
				parent = toFix.parent;
		}	
	}
	
	/**
	 * if max heap property, node will move down if one of it's children is larger
	 * if min heap property, node will move down if one of it's children is smaller.
	 * Node will down until heap property is satisfied.
	 * @param toFix Node being fixed.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void siftDown(Node toFix){
		while(toFix.left != null){  
			if(toFix.right != null){   
				if( compareNodes(toFix.left, toFix.right) && compareNodes(toFix.left, toFix) ){  
					swapElements(toFix, toFix.left);
					toFix = toFix.left;
					toFix.left = toFix.left;
				}	
				else if( compareNodes(toFix.right, toFix.left) && compareNodes(toFix.right, toFix) ){ 
					swapElements(toFix, toFix.right);
					toFix = toFix.right;
					toFix.right = toFix.right;
				}
				else 
					break;
			}
			else{ 
				if(compareNodes(toFix.left, toFix)){ 
					swapElements(toFix, toFix.left);
					toFix = toFix.left;
					toFix.left = toFix.left;
				}
				else 
					break;
			}
			resetRoot();
		}
	}
	
	/**
	 * Used to help getSortedContents. Checks for node's validity when sifting
	 * down.
	 * @param toFix node to fix
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void siftDownUntilSpecified(Node toFix){
		while(toFix.left != null && toFix.left.isValid){		
			if(toFix.right != null && toFix.right.isValid){ 	
																			
				if(compareNodes(toFix.left, toFix.right) && compareNodes(toFix.left, toFix)){  
					swapElements(toFix, toFix.left);
					toFix = toFix.left;
					toFix.left = toFix.left;
				}	
				else if(compareNodes(toFix.right, toFix.left) && compareNodes(toFix.right, toFix)){ 
					swapElements(toFix, toFix.right);
					toFix = toFix.right;
					toFix.right = toFix.right;
				}
				else 
					break;
			}
			else {												
				if(compareNodes(toFix.left, toFix)){ 
					swapElements(toFix, toFix.left);
					toFix = toFix.left;
					toFix.left = toFix.left;
				}
				else 
					break;
			}	
			resetRoot();
		}
	}
	
	/**
	 * Compares element values of two nodes. 
	 * @param firstNode first node to compare
	 * @param secondNode second node to compare
	 * @return boolean value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean compareNodes(Node firstNode, Node secondNode){
		if(myMode == Heap.MODE.MAX){
			return ((V)firstNode.elem).compareTo((V)secondNode.elem) > 0;
		}
		return ((V)firstNode.elem).compareTo((V)secondNode.elem) < 0;
	}
	
	/**
	 * Swaps element values between two nodes 
	 * @param firstNode first node to be swapped.
	 * @param secondNode second node to be swapped.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void swapElements(Node firstNode, Node secondNode){
		V temp = (V)secondNode.elem;
		secondNode.elem = firstNode.elem;
		firstNode.elem = temp;		
	}
	
	/**
	 * Value of root is set to top most element.
	 * */
	private void resetRoot(){
		while(root.parent != null){
			root = root.parent;
		}
	}
	
	/**
	 * Validity of all nodes in the heap become true.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void resetValidity(){
		Node node = null;		
		for(int i = 0; i < count; i++){				
			String binaryLocation = toBinaryString(i+1); 	
			node = getNode(binaryLocation);					
			node.isValid = true;								
		}
	}
	
	/**
	 * Returns true if the int passed is even.
	 * @param n Number to test.
	 * @return boolean value
	 */
	private boolean isEven(int x){
		if(x % 2 == 0) 
			return true;
		else 
			return false;
	}

	/**
	 * Returns true if the int passed is odd. 
	 * @param n Number to test.
	 * @return boolean value
	 */
	private boolean isOdd(int x){
		if(x % 2 == 1) 
			return true;
		else 
			return false;
	}

	/**
	 * Recieves integer and turns it to binary to represent position.
	 * @param position Node's position number in heap
	 * @return Binary representation of position.
	 */
	 
	private String toBinaryString(int position){
		String x = "";
		int dividend = position;
		int remainder = 0;
		int temp = 0;
		if(position == 0){
			x = "0";
		} else {
			while(dividend > 0){
				temp = dividend / 2;
				remainder = dividend % 2;
				if(remainder == 0) 
					x = "0" + x;
				else 
					x = "1" + x;		
				dividend = temp;
			}
		}	
		return x;
	}
	/**
	 * Takes in binary number as a string and finds node in that binary position.
	 * @param binaryString Binary value of a number. 
	 * @return Node trying to be found..
	 */
	@SuppressWarnings("rawtypes")
	private Node getNode(String binaryString){
		resetRoot();
		for(int i = 1; i < binaryString.length(); i++){
			String str = binaryString.substring(i, i+1);
			if(str.compareTo("0") == 0)
				root = root.left;
			else if(str.compareTo("1") == 0)
				root = root.right;	
		}
		return root;
	}
}
