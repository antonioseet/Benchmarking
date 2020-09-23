/*
 *  Name: Tony Barrera
 * Project: PA1 Benchmarking
 * Repository URL: https://github.com/antonioseet/Benchmarking.git
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Benchmarking {
	
	public static void main(String[] args) {
		
		// Locate files
		System.out.println("Please enter the filename that contains a list of integers: ");
		Scanner inputFileScanner = new Scanner(System.in);
		String filename = inputFileScanner.next();
		File inputFile = new File(filename);
		
		Scanner scan = null;
		
		try {
			scan = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println("File not valid");
			e.printStackTrace();
		}
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		
		//Insert all elements of the text file into the LinkedList
		double t1 = System.nanoTime();
		while(scan.hasNext()) {
			insertToLinkedListInOrder(list, scan.nextInt());	
		}
		double t2 = System.nanoTime();
		
		averageTimes(list, (t2-t1)/1000000);
		
		scan.close();
	}
	
	public static void insertToLinkedListInOrder(LinkedList<Integer> list, Integer num) {
		
		
		if(!list.isEmpty()){
			
			int index = 0;
			boolean locationFound = false;
			
			// Loop to find a location to insert
			while(!locationFound && index < list.size())
			{
				// if num less than or equal to number in list, we should check the next index
				int listNumber = list.get(index);
				if(num < listNumber)
				{
					if(!nextIsOutOfBounds(list, index))
					{
						if(frontIsBigger(list, num, index))
						{
							list.add(index, num);
							locationFound = true;
						}
					}
					else
					{
						list.add(index, num);
						locationFound = true;
					}
					index++;
				}
				else if (num == listNumber)
				{
					list.add(index, num);
					locationFound = true;
				}
				else
				{
					if(nextIsOutOfBounds(list, index))
					{
						list.add(index+1, num);
						locationFound= true;
					}
					else
					{
						index++;
					}
				}
			}
		}else{
			list.add(num);
		}
		
	}
	
	
	//Assuming list is sorted
	public static int findMin(LinkedList<Integer> list) {
		
		return list.peek();
	}
	
	
	// Assuming list is sorted and we need to traverse each node
	public static int findMax(LinkedList<Integer> list) {
		
		int maxIndex = 0;
		
		for(int i = 0; i < list.size(); i++) {
			maxIndex = i;
		}
		
		return list.get(maxIndex);
	}
	
	
	// Assuming list is sorted
	public static int findMed(LinkedList<Integer> list) {
		
		int medIndex = 0;
		
		for(int i = 0; i < list.size()/2; i++) {
			medIndex = i;
		}
		
		return list.get(medIndex);
	}
	
	
	public static boolean frontIsBigger(LinkedList<Integer> list, int n, int index) {
		boolean isBigger = false;
		
		// if front number is bigger
		if(list.get(index+1) > n) {
			isBigger = true;
		}
		
		return isBigger;
	}
	
	
	public static boolean nextIsOutOfBounds(LinkedList<Integer> list, int index) {
		return (index + 1 >= list.size() );
	}
	
	
	// Sanity check
	public static boolean checkOrder(LinkedList<Integer> list) {
		
		for(int i = 0; i < list.size()-1; i++) {
			
			int front = list.get(i);
			int back = list.get(i+1);
			
			if(front > back) {
				return false;
			}
				
		}
		return true;
	}
	
	
	//Get average result times for input1
	public static void averageTimes(LinkedList<Integer> list, double insertTime) {
		
		ArrayList<Long> minTimes = new ArrayList<Long>();
		ArrayList<Long> maxTimes = new ArrayList<Long>();
		ArrayList<Long> medTimes = new ArrayList<Long>();
		
		
		for(int i = 0; i < 100; i++) {
			long t1 = System.nanoTime();
			findMin(list);
			long t2 = System.nanoTime();
			minTimes.add(t2-t1);
			
			t1 = System.nanoTime();
			findMax(list);
			t2 = System.nanoTime();
			maxTimes.add(t2-t1);
			
			t1 = System.nanoTime();
			findMed(list);
			t2 = System.nanoTime();
			medTimes.add(t2-t1);
		}
		
		double minAverage =0;
		double maxAverage =0;
		double medAverage =0;
		
		for(int i = 0; i < minTimes.size(); i++) {
			minAverage += minTimes.get(i);
			maxAverage += maxTimes.get(i);
			medAverage += medTimes.get(i);
		}

		minAverage /= minTimes.size();
		maxAverage /= maxTimes.size();
		medAverage /= medTimes.size();
		
		System.out.println();
		System.out.println("min: " + findMin(list));
		System.out.println("max: " + findMax(list));
		System.out.println("med: " + findMed(list));
		System.out.println("time_insert: " + insertTime + " miliseconds");
		System.out.println("time_min: " + minAverage + " nanoseconds on average (of 100 samples)");
		System.out.println("time_max: " + maxAverage + " nanoseconds on average (of 100 samples)");
		System.out.println("time_med: " + medAverage + " nanoseconds on average (of 100 samples)");
	}
	
	
}
