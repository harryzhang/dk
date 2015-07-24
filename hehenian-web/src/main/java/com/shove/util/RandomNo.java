package com.shove.util;

import java.util.Random;

public class RandomNo {
    
	/**
	 * 产生一定范围内数据无重复的随机数组  
	 * @param count
	 * @return
	 */
     public static int[] getRandomList(int count){
	    int[] list= new int[count];
	   
	    //初始化数组，即创建一个有序的数组
	    for(int i=0;i<count;i++){
		    list[i]=i;
	    }
	   
	    for(int i=0;i<count;i++){
		   int k =getRandom(0,count);
		   int j =getRandom(0,count);
		   int temp;
		   temp=list[k];
		   list[k]=list[j];
		   list[j]=temp;
	    }
	    return list;
    }
    
    /**
     * 得到一个随机数
     * @param start
     * @param last
     * @return
     */
    public static int getRandom(int start,int last){
    	//i为种子的变量
    	int rd;
    	do{
    		Random r =  new Random();
    		rd = r.nextInt(last);
    	}while(rd<start);
    	return rd;
    }
    
    /**
     * 得到from到to的随机数，包括to
     * @param from
     * @param to
     * @return
     */
    public static int randomIntNumber(int from, int to) {
		float a = from + (to - from) * (new Random().nextFloat());
		int b = (int) a;
		return ((a - b) > 0.5 ? 1 : 0) + b;
	}
}