import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lection1 {

    public static void main(String[] args) {
        int[] a1 = new int[] {21, 23, 24, 40, 75, 76, 78, 77, 900, 2100, 2200, 2300, 2400, 2500};
        int[] a2 = new int[] {10, 11, 41, 50, 65, 86, 98, 101, 190, 1100, 1200, 3000, 5000};
        int[] a3 = new int[a1.length + a2.length];
    
        int i=0, j=0;
        for (int k=0; k<a3.length; k++) {
    
            if (i > a1.length-1) {
                int a = a2[j];
                a3[k] = a;
                j++;
            }
            else if (j > a2.length-1) {
                int a = a1[i];
                a3[k] = a;
                i++;
            }
            else if (a1[i] < a2[j]) {
                int a = a1[i]; 
                a3[k] = a;
                i++;
            }
            else {
                int b = a2[j];
                a3[k] = b;
                j++;
            }
        }
    }

    public static List<Integer> findSimpleDivider(int number){

        List<Integer> result = new ArrayList<>();
        for(int i = 0; i <= number; i++){
            if(number % i == 0){
                result.add(i);
            }
        }

        return result;
        
    }


    public static List<Integer> findSimpleNumbers(int max){
        List<Integer> result = new ArrayList<>();
        for(int i = 1; i <= max; i++ ){
            boolean simple = true;
            for(int j = 2; j < i; j++){
                if(i % j == 0) simple = false;
                
            }
            if(simple) result.add(i);
        }
        return result;
    }

    // алгоритм бинарного поиска 

    public static int binarySearch(int[] sortedArray, int key){

        int index = Integer.MAX_VALUE;
        int low = 0;
        int high = sortedArray.length -1;
        while(low <= high){
            int mid = low + ((high-low) /2);
            if(sortedArray[mid] < key){
                low = mid + 1;
            }
            else if(sortedArray[mid] > key){
                high = mid - 1;
            }else if (sortedArray[mid] == key){
                index = mid; 
                break;
            }
        }
        return index;

    }


    // алгоритм быстрой сортировки

    public static void quickSort(int[] source, int leftBorder, int rightBorder){

        // устанавливаем два маркера для массива, левый и правый, соответствующие началу и концу массива
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        // определяем опорный элемент, далее перемещаем в левую часть массива элементы, которые меньше опорного элемента, и в правую
        // часть - элементы, которые больше опорного элемента
        int pivot = source[(leftMarker + rightMarker)/2];
        do{
        // двигаем левый маркер слева направо до тех пор пока, элемент массива на позиции левого маркера меньше опорного элемента
        while(source[leftMarker] < pivot) leftMarker++;
        // двигаем правый маркер налево, до тех пор пока, элемент массива на позиции правого маркера больше опорного элемента
        while(source[rightBorder] > pivot) rightMarker--;
        // если левый указатель меньше правого, то производим обмен элементов
        if(leftMarker <= rightMarker){
            if(leftMarker < rightMarker){
                int temp = source[leftMarker];
                source[leftMarker] = source[rightMarker];
                source[rightMarker] = temp;
            }
            // сдвигаем маркеры, чтобы обозначить новые границы

            leftMarker++;
            rightMarker--;

        }
        // когда левый маркер окажется за пределами правого, это будет означать, что обмен закончен
        }while(leftMarker <=rightMarker);

        // рекурсивно вызываем такую же сортировку для участков массива от начала сортируемого участка до правого маркера   
        // и от левого маркера до конца сортируемого участка

        if(leftMarker < rightBorder){
            quickSort(source, leftMarker, rightBorder);
        }
        if(leftBorder < rightMarker){
            quickSort(source, leftBorder, rightMarker);
        }


    }
    


    // второй вариант быстрой сортировки - более правильный

    public static void quickSort2(int[] array,int low, int high){
        if(array.length == 0 || low >= high) return;
        int pivot = array[low+(low+high)/2];
        int leftMarker = low;
        int rightMarker = high;
        while(leftMarker <= rightMarker){
            while(array[leftMarker] < pivot) leftMarker++;
            while(array[rightMarker] > pivot) rightMarker--;
            if(leftMarker <= rightMarker){
                int temp = array[leftMarker];
                array[leftMarker] = array[rightMarker];
                array[rightMarker] = temp;
                leftMarker++;
                rightMarker--;
            }
        }

        if(low < rightMarker){
            quickSort2(array, low, rightMarker);
        }
        if(leftMarker < high){
            quickSort2(array, leftMarker, high);
        }
    }

    // сортировка слиянием
    // сначала делим массив
    public static int[] sortArray(int[] arrayA){
        if(arrayA.length == 0) return null;
        if(arrayA.length < 2) return arrayA;
        // копируем левую часть массива от начала до середины
        int[] arrayB = new int[arrayA.length/2];
        System.arraycopy(arrayA, 0, arrayB, 0, arrayA.length/2);
        // копируем правую часть массива от середины до конца
        int[] arrayC = new int[arrayA.length - arrayA.length/2];
        System.arraycopy(arrayA, arrayA.length/2, arrayC, 0, arrayA.length-arrayA.length/2);
        // рекурсивно закидываем новые массивы обратно в этот метод, то тех пор пока не образуются маленькие массивы длинной в 1 элемент
        arrayB = sortArray(arrayB);
        arrayC = sortArray(arrayC);
        return mergeArray(arrayB, arrayC);
    }

    public static int[] mergeArray(int[] arrayA, int[] arrayB){
        int[] arrayC = new int[arrayA.length + arrayB.length];
        int positionA = 0;
        int positionB = 0;
        for(int i = 0; i < arrayC.length; i++){
            if(positionA == arrayA.length){
                arrayC[i] = arrayB[i - positionB];
                positionB++;
            }else if(positionB == arrayB.length){
                arrayC[i] = arrayA[i - positionA];
                positionA++;
            }else if(arrayA[i - positionA] < arrayB[i - positionB]){
                arrayC[i] = arrayA[i - positionA];
                positionB++;
            }else{
                arrayC[i] = arrayB[i - positionB];
                positionA++;
            }

        }
        return arrayC;
    }
    // второй вариант сортировки слиянием - рабочий

    public static void mergeSort(int[] array, int low, int high){
        if(high <= low) return;
        int mid = low + ((high - low) /2);
        mergeSort(array, low, high);
        mergeSort(array, mid+1, high);
        int[] bufArray = Arrays.copyOf(array, array.length);
        for(int k = 0; k < high; k++){
            bufArray[k] = array[k];
        }
        int i = low;
        int j = mid + 1;
        for(int k = low; k < high; k++ ){
            if(i < mid){
                array[k] = bufArray[j];
                j++;
            }else if(j < high){
                array[k] = bufArray[i];
                i++;
            }else if(bufArray[i] < bufArray[j]){
                array[k] = bufArray[i];
                i++;
            }else{
                array[k] = bufArray[j];
                j++;
            }
        }
    }
    
}
