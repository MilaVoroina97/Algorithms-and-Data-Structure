public class HeapSort {
    // процедура для преобразования поддерева в двоичную кучу с корневым узлом i, размером n 
    // это также операция просвеивания, которая занимается продвижением элементов наверх, если они не являются самыми большими
    public static void heapify(int[] array, int n, int i){
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if(left < n && array[left] < array[largest]){
            largest = left;
        }if(right < n && array[right] < array[largest]){
            largest = right;
        }

        if(largest != i){ 
            // обмен внутри массива 
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            // Рекурсивно запускаем функцию вглубь затронутого поддерева, т.е. необходимо посмотреть, что поменялось у нижлежащих
            // элементов
            heapify(array, n, largest);
        }
    }

    public static void sort(int[] array){
        int n = array.length;
        // Построение кучи (перегруппируем массив), берем середину массива, выбираем в качестве корня и запускаем просеивание
        // для элементов, начиная с середины и до самого начала, в результате самый большой элемент окажется на 0 позиции(корень)

        for(int i = n/2 -1; i >= 0; i--){
            heapify(array, n, i);
        }
        // Один за другим извлекаем элементы из кучи ,берем корневой элемент и меняем его на последней элемент в куче
        for(int i = n - 1; i >= 0; i--){ // перебираем элементы от конца до самого начала 

            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            // Вызываем процедуру heapify на оставшемся массиве,не затрагивая последний элемент
            heapify(array, i, 0);
        }
    }
    public static void main(String[] args) {
        int arr[] = { 12, 11, 13, 5, 6, 7 };
        sort(arr);
        for (int i = 0; i < arr.length; ++i)
        System.out.print(arr[i] + " ");
    System.out.println();
    }
}
