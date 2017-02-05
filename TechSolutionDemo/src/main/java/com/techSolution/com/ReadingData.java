package com.techSolution.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class ReadingData {
	
	public double processData() throws IOException{
		int count = 0;
		 String fileName = "data.txt";
		 BufferedReader br = null;
	        String line = "";
	        String dataSplitBy = " ";
	        int given_time=0;
	        int total_dishes=0;
	        double result=0;
	        int x=0;
	        ArrayList<Integer> satisfaction=new ArrayList<Integer>();
	        ArrayList<Integer> timetaken_dish=new ArrayList<Integer>();
	        Map<Double,Integer> map=new HashMap<Double,Integer>(); 

	        ClassLoader classLoader = new ReadingData().getClass().getClassLoader();
	       
	        File file = new File(classLoader.getResource(fileName).getFile());//reading file from resource data.txt 
	        br = new BufferedReader(new FileReader(file));
	        System.out.println("buffer"+br);
	        String[] data=null;
	        int sum=0;
            while ((line = br.readLine()) != null) {
            	data= line.split(dataSplitBy);//splitting data by space
            	if(count == 0){
                // use comma as separator
            		given_time=Integer.parseInt(data[0]);//total given time to eat
            		total_dishes=Integer.parseInt(data[1]);//total number of dishes

                System.out.println("time given"+data[0]+" "+"no. of dishes"+data[1]);
            	}
            	else{
            		double satisfaction_per_minute=((double)Integer.parseInt(data[0])/Integer.parseInt(data[1]));//calculating satisfaction per minute
            		System.out.println("checking");
            		map.put(satisfaction_per_minute
            				, Integer.parseInt(data[1]));//keeping satisfaction and time of a dish in map
            		
            	
            	}
            	count++;
            }
            NavigableMap<Double, Integer> newMap = new TreeMap(new MyComparator());//sorting the map in desending order using MyComparator
    		newMap.putAll(map);
    		for (Map.Entry<Double, Integer> entry : newMap.entrySet()) {
    			sum=sum+entry.getValue();
    			System.out.println("sum"+sum);
    			 System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
    			 
    			if(sum>given_time){//checking time consumed is greater than 10000(which is given in txt file)if it exceeds it breaks loops
    				System.out.println("sum in if "+sum);
    				if(sum>given_time){
    					
    					int present_value=entry.getValue();
    					sum=sum-entry.getValue();
    					x=given_time-sum;//if sum exceeds more than given time then we are subtracting that extra value
    					result=result+entry.getKey()*x;
    					
    				}
    				
    				
    		    
    		     break;
    		     }
    			else{
    				
					result=result+entry.getKey()*entry.getValue();//total satisfaction result
				}
				
    			
    		}

    		sum=sum+x;
    		System.out.println("total time"+sum);
	return result;
		
	}
	class MyComparator implements Comparator<Double> {

		  @Override
		  public int compare(Double first, Double second) {

		    return second.compareTo(first);
		  }
		}

}
