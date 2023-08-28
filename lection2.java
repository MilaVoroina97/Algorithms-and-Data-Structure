

public class lection2 {

    public static void main(String[] args) {
        
        int[] array = new int[]{ 2, 1, 4, 6, 3, 5 , 90, 45,34,12 };
        choiceSort(array);
        for(int i = 0; i < array.length; i++){
            System.out.println();
            System.out.println(array[i]);
        }

        

    }
    

    public static void bubleSort(int[] array){

        boolean finish;
        do{
            finish = true;
            for(int i = 0; i < array.length-1; i++){
                if(array[i] > array[i+1]){
                    int temp = array[i];
                    array[i] = array[i+1];
                    array[i+1]=temp;
                    finish = false;
                }

            }
        }while(!finish);
    }

    public static void optimizedBubbleSort(int[] array){
        int i = 0;
        int n = array.length;
        boolean needSwap = true;
        while(i < n - 1 && needSwap){
            needSwap = false;
            for(int j = 1; j < n - i; j++){
                if(array[j-1] > array[j]){
                    int temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                    needSwap = true;
                }
            }
            if(!needSwap) {
                break;
            }
            i++;
        }
    }

    public static void choiceSort(int[] array){
        for(int i = 0; i < array.length - 1; i++){
            int minPosition = i;
            System.out.println("i : " + i);
            for(int j = i + 1; j < array.length; j++){
                System.out.println("j : " + j);
                if(array[j] < array[minPosition]){
                    minPosition = j;
                    System.out.println("minposition : " + minPosition);
                }
            }
            if(i != minPosition){
                System.out.println("i : "+ i);
                System.out.println("array[i] : " + array[i]);
                System.out.println("swap : " + array[minPosition]);
                int temp = array[i];
                array[i] = array[minPosition];
                array[minPosition] = temp;
            }
        }
    }


    public static void insertSort(int[] array){
        for(int i = 0; i < array.length - 1; i++){
            for(int j = i + 1; j < array.length; j++){
                if(array[i] > array[j]){
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    // Алгоритмы поиска

    public static int find(int[] array, int value){
        for(int i = 0; i < array.length; i++){
            if(array[i] == value){
                return i;
            }
        }
        return -1;

    }


    public static int binarySearch(int[] array, int value, int min, int max){

        int midPoint;

        if(max < min){
            return - 1;
        }else{

            midPoint = (max - min) / 2 + min;
        }
         

        if(array[midPoint] < value){
            return binarySearch(array, value, midPoint+1, max);
        }else{
            if(array[midPoint] > value){
                return binarySearch(array, value, min, midPoint-1);
            }else{
                return midPoint;
            }
        }

    }

    public static void quickSort(int[] array, int low, int high){
        int left = low;
        int right = high;
        int mid = (right - left) / 2 + left;
        while( left <= right){
            while(array[left] < mid){
                left++;
            }
            while (array[right] > mid){
                right--;
            }

            if(left <= right){
                if(left < right){
                    int temp = array[left];
                    array[left] = array[right];
                    array[right] = temp;
                }

                left++;
                right--;
            }
        }

        if(left < high){
            quickSort(array, left, high);
        }
        if(low < right){
            quickSort(array, low, right);
        }

    }


}
