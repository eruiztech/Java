
import java.util.Arrays;
import java.util.Random;

public class Sorting {
	public static void main(String[] args){
		int powerOfTwo = 5;
		int n = (int) Math.pow(2, powerOfTwo);
		int[] list = new int[n];
		int[] unsortedList = new int[n];
		Random rand = new Random();
		
		for(int i = 0; i < list.length; i++){
			list[i] = rand.nextInt(100);
			unsortedList[i] = list[i];
		}
		
		for(int m = 1; m < 6; m++){
			System.out.println("Methods used: " + methodName(m));
			System.out.println("Unsorted: " + Arrays.toString(list));
			final long startTime = System.nanoTime();
			method(m, list);
			final long endTime = System.nanoTime();
			System.out.println("Sorted: " + Arrays.toString(list));
			//System.out.println("Time: " + (endTime - startTime) + " ns");
			for(int i = 0; i < list.length; i++){
				list[i] = unsortedList[i];
			}
		}
		System.out.println("----------------------------------------------------------------------");
	}
	
public static void method(int m, int[] list){
	if(m == 1){
		insertionSort(list);
	}
	else if(m == 1){
		mergeSort(list);
	}
	else if(m == 3){
		quickSort(list);
	}
	else if(m == 4){
		quickSort2(list);
	}
	else{
		quickSort3(list);
	}
}

public static String methodName(int m){
	String method = "";
	if(m == 1){
		method = "Insertion Sort";
	}
	else if(m == 2){
		method = "Merge Sort";
	}
	else if(m == 3){
		method = "Quick Sort";
	}
	else if(m == 4){
		method = "Quick Sort 2";
	}
	else{
		method = "Quick Sort 3";
	}
	return method;
}

public static int[] insertionSort(int[] numList){
	for(int i = 1; i < numList.length; i++) {
		for(int j = i; j > 0; j--) {
			if(numList[j] < numList[j-1]) {
				swap(numList, j, j-1);
			}
			else {
				break;
			}
		}
	}
	return numList;
}

public static void swap(int[] numList, int i, int j) {
	int temp = numList[i];
	numList[i] = numList[j];
	numList[j] = temp;
}

public static int[] mergeSort(int[] numList){
	if(numList.length > 1){
		int[] leftSide = leftHalf(numList);
		int[] rightSide = rightHalf(numList);
		
		mergeSort(leftSide);
		mergeSort(rightSide);
		
		merge(numList, leftSide, rightSide);
	}
	return numList;
}

public static int[] leftHalf(int[] numList){
	int size = numList.length / 2;
	int[] left = new int[size];
	for(int i = 0; i < size; i++){
		left[i] = numList[i];
	}
	return left;
}

public static int[]rightHalf(int[] numList){
	int sizeOne = numList.length / 2;
	int sizeTwo = numList.length - sizeOne;
	int[]right = new int[sizeTwo];
	for(int i = 0; i < sizeTwo; i++){
		right[i] = numList[i + sizeOne];
	}
	return right;
}

public static void merge(int[] result, int[] left, int[] right){
	int indexLeft = 0;
	int indexRight = 0;
	
	for(int i = 0; i < result.length; i++){
		if(indexRight >= right.length || (indexLeft < left.length && left[indexLeft] <= right[indexRight])){
			result[i] = left[indexLeft];
			indexLeft++;
		}
		else {
			result[i] = right[indexRight];
			indexRight++;
		}
	}
}

public static int[] quickSort(int[] numList){	
	quickSortExtension(numList, 0, numList.length - 1);
	return numList;
}

public static void quickSortExtension(int[] numList, int first, int last){
	if(first < last){
		int pivot = partition(numList, first, last);
		quickSortExtension(numList, first, pivot - 1);
		quickSortExtension(numList, pivot + 1, last);
	}
}

public static int partition(int[] numList, int first, int last){
	int lastIndex = first;
	
	for(int i = first; i < last; i++){
		if(numList[i] < numList[last]){
			swap(numList, lastIndex, i);
			++lastIndex;
		}
	}
	swap(numList, last, lastIndex);
	return lastIndex;
}

public static int[] quickSort2(int[] numList){
	if(numList.length <= 16){
		insertionSort(numList);
		return numList;
	}
	else{
		quickSort(numList);
		return numList;
	}
}

public static int[] quickSort3(int[] numList){
	quickSortExtension3(numList, 0, numList.length - 1);
	return numList;
}

public static void quickSortExtension3(int[] numList, int first, int last){
	Random rnd = new Random();
	
	if(first < last){
		if((last - first + 1) >= 16){
			swap(numList, first, first + (rnd.nextInt(numList.length - 1) % (last - first + 1)));
		}
		int pivot = partition(numList, first, last);
		quickSortExtension(numList, first, pivot - 1);
		quickSortExtension(numList, pivot + 1, last);
	}	
}

}
