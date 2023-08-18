package Seminar;

import java.util.Random;

public class seminar2 {
    

    private static Random random = new Random();
    public static int[] prepareArray(){
        // int[] array = new int[random.nextInt(10,16)];
        // for(int i = 0 ; i < array.length; i++){
        //     array[i] = random.nextInt(-99,100);
        // }

        return prepareArray(random.nextInt(10,16));

    }

    public static int[] prepareArray(int length){

        int[] array = new int[length];
        for(int i = 0 ; i < array.length; i++){
            array[i] = random.nextInt(-99,100);
        }

        return array;
    }

    public static void printArray(int[] array){

        for(int element : array ){
            System.out.println(element);
        }
    }

    public static void directSort(int[] array){

        for(int i = 0; i < array.length - 1; i++){

            int saveIndex = i;
            for(int j = i + 1; j < array.length; j++){

                if(array[j] < array[saveIndex]){
                    saveIndex = j;
                }
            }

            if(saveIndex != i){
                int swap = array[i];
                array[i] = array[saveIndex];
                array[saveIndex] = swap;

                // array[i] = 5
                // array[saveIndex] = 12

                // array[i] = array[i] + array[saveIndex];  17
                // array[saveIndex] = array[i] - array[saveIndex];  17 - 5 = 12
                // array[i] = array[i] - array[saveIndex];  17 - 12 = 5
            }
        }
    }



    public static void quickSort(int[] array, int low, int high){

        int left = low; 
        int right = high;
        int middle = array[(left + right) / 2];

        do{
        while(array[left] < array[middle]){
            left++;
        }
        while(array[right] > array[middle]){
            right--;
        }

        if(left <= right){
            if(left < right){

                int buf = array[left];
                array[left] = array[right];
                array[right] = buf;
            }

            left++;
            right--;
         }
       }while(left <= right);

       if(left < high){

        quickSort(array, left,high);
       }
       if(low < right){

        quickSort(array, low, right);
       }
    }

    public static int binarySearch(int value,int[] array){
        return binarySearch(array, 0, array.length - 1, value);
    }
    private static int binarySearch(int[] array, int low, int high, int value){


        if(high < low){
            return -1;
        }
        int middle = (high - low) /2 + low;

        if(array[middle] == value){
            return middle;
        }

        else if(array[middle] < value){

            return binarySearch(array, middle+1, high, value);

        }else{
            
            return binarySearch(array, low, middle-1, value);
        }


    }
}
