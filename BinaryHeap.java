

public class BinaryHeap {

    private Node[] heapArray; // массив со всеми вершинами
    private int maxSize; // размер массива
    private int currentSize; // количество узлов массиве

    public BinaryHeap(int maxSize){

        this.maxSize = maxSize;
        this.currentSize = 0;
        this.heapArray = new Node[maxSize];
    }

    public void printHeap(){
        for(int i = 0; i < this.currentSize; i++){
            if(heapArray[i] != null){
                System.out.println(heapArray[i].getValue());
            }else{
                System.out.println("-");
            }
        }

        System.out.println();
        int countOfGaps = 32;
        int itemsPerRow = 1;
        int columnNumber = 0; // номер элемента в данной строке
        for(int i = 0; i < currentSize; i++){
            if(columnNumber == 0){ // проверяем первый элемент ли в текущей строке
                for(int k = 0; k < countOfGaps; k++){ // добавляем предшествующие пробелы
                    System.out.println(" ");
                }

            }

            System.out.println(heapArray[i].getValue());

            if(++columnNumber == itemsPerRow){ // проверяем последний ли элемент в строке

                countOfGaps /= 2;// уменьшаем количество оступов применяемое для следующей строки
                itemsPerRow *= 2;// указываем, что элементов может быть вдвое больше
                columnNumber = 0;// сбрасываем счётчик для текущего элемента строки
                System.out.println();// переходим на нову строку
                
            }else{ //переход к следующему элементу

                for(int k = 0; k < countOfGaps * 2 - 2; k++){
                    System.out.println(" ");// добавляем отступы

                }
            }
        }

    }

    private void displaceUp(int index){
        int parentIndex = (index - 1) / 2;
        Node bottom = this.heapArray[index];
        while(index > 0 && heapArray[parentIndex].getValue() < bottom.getValue()){
            heapArray[index] = heapArray[parentIndex]; // если родительский элемент меньше искомого , то меняем их местами
            index = parentIndex;
            parentIndex = (index - 1) /2; // берем новый родительский индекс и повторяем сравнение элементов
        }

        heapArray[index] = bottom;
    }

    private void displaceDown(int index){
        int largestChild;
        Node top = heapArray[index]; // сохранение корня, пока у узла есть хотя бы один потомок
        while(index < currentSize /2){ // если данное условие не выполняется то элемент уже в самом низу пирамиды

            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            if(rightChild < currentSize && heapArray[leftChild].getValue() < heapArray[rightChild].getValue()){
                largestChild = rightChild; // вычисляем ребенка вершину с наибольшим числовым значением
            }else{
                largestChild = leftChild;
            }if(top.getValue() >= heapArray[largestChild].getValue()){ // если значение вершины больше или равно

                break;

            }

            heapArray[index] = heapArray[largestChild];// заменяем вершину, большей дочерней вершиной
            index = largestChild; // текущий индекс переходит вниз
        
        }

        heapArray[index] = top;// задаем конечное местоположение для элемента

    }

    public boolean insertNode(int value){

        if(currentSize == maxSize){ // проверяем не выходим ли мы за рамки массива
            return false;
        }

        Node newNode = new Node(value);
        heapArray[currentSize] = newNode;// вершину задём в самый низ дерева
        displaceUp(currentSize++);// пытаемся поднять вершину, если значение вершины позволяет

        return true;
    }

    public Node removeNode(int index){ // удалить элемент по индексу массива

        if(index > 0 && currentSize > index){

            Node root = heapArray[index];
            heapArray[index] = heapArray[--currentSize]; // задаём элементу с переданным индексом, значение последнего элемента
            heapArray[currentSize] = null; // последний элемент удаляем
            displaceDown(index);// проталкиваем вниз новый элемент, чтобы он должное ему место
            return root;
        }
        return null;
    }

    public boolean changeNode(int index, int newValue){

        if(index < 0 || currentSize <= index){
             return false;
        }

        int oldValue = heapArray[index].getValue();
        heapArray[index].setValue(newValue);
        if(oldValue < newValue){ // если узел повышается
            displaceUp(index);// выполняется смещение вверх
        }else{  // если понижается
            displaceDown(index);  // смещение вниз
        }

        return true;
    }



}
