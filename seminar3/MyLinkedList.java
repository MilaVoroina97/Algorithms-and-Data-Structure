import java.util.Comparator;

public class MyLinkedList<T> {

    private Node head;
    

    class Node{

        public Node next; 

        public T value;


    }

    public void addFirst(T value){

        Node newNode = new Node();
        newNode.value = value;
        if(head == null){
            head = newNode;
        }else{

            newNode.next = head;  //перезаписываем ссылку на первый элемент
            head = newNode; //новый элемент становится head
        }

    }

    public void removeFirst(){
        
        if(head != null){

            head = head.next;
        }

    }
    
    
    public T contains(T value){

        Node currentNode = head;
        while(currentNode != null){

            if(currentNode.value.equals(value)){

                return currentNode.value;
            }
            currentNode = currentNode.next;

        }

        return null;
   
    }

    // метод сортировки выбором
    public void sort(Comparator<T> comparator){

        Node currentNode = head;
        while(currentNode != null){

            Node minValueNode = currentNode; // сохраняем в отдельную переменную значение текущей ноды, так как мы не можем сохранять
            // отдельно индексы, как это бывает в алгоритме быстрой сортировки

            Node indexNode = currentNode.next;
            while(indexNode != null){

                if(comparator.compare(minValueNode.value, indexNode.value) > 0){ //если первый элемент больше, чем второй, то 
                    // мы можем утверждать,что минимальный элемент на данный момент - второй элемент: 
                    // в процессе проходим по всем элементам и ищем самый минимальный
                    minValueNode = indexNode;
                }
                indexNode = indexNode.next;
            }

            if(minValueNode != currentNode){ // если после внутреннего цикла ссылка на текущую ноду поменялась, то нужно произвести
                // обмен значений(а не сами ноды)

                T buf = currentNode.value;
                currentNode.value = minValueNode.value;
                minValueNode.value = buf;
                
            }
            currentNode = currentNode.next;

        }
    }


    // добавление элемента в конец связанного списка

    public void addLast(T value){
        Node newNode = new Node();
        newNode.value= value;
        if(head == null){
            head = newNode;
        }else{
            Node lastNode = head;
            while(lastNode.next != null){ // перебираем весь список пока не дойдем до последнего(у него ссылка на следующий элемент 
                // будет пустая)

                lastNode = lastNode.next;

            }

            lastNode.next = newNode;
        }
        // третий частный случай : если у нас в списке только один элемент(он же и является head), то мы затираем на него ссылку
        head = null; 
    }

    public void removeLast(){
        if(head == null){
            return;
        }
        Node currentNode = head;
        while(currentNode.next != null){
            if(currentNode.next.next == null){ // частный случай: если у нас только два элемента в списке, то мы просто затираем
                // ссылку с первого на второй элемент и выходим из метода
                currentNode.next = null;
                return;
            }
            currentNode = currentNode.next;
        }

        head = null;
    }

    // разворот для односвязанного списка

    public void revert(){

        if(head != null && head.next != null){

            revert(head.next, head);

        }
    }

    private void revert(Node currentNode,Node previousNode){ // функция принимает в качестве аргументов ссылку на текущую ноду
        // и ссылку на предыдущую, т.к. каждая нода содержит ссылку на следующую, а нам необходимо заменить следующую ссылку на
        // предыдущую(для разворота), поэтому нам нужно указывать, с какой ноды мы сюда пришли

        // как только мы доходим до последнего элемента в списке, нужно обновить head

        if(currentNode.next == null){
            head = currentNode; // если следующая нода пустая, то head становится текущим значением
        }else{
            revert(currentNode.next, currentNode);
        } 
        // совершаем операцию замену ссылок

        currentNode.next = previousNode;
        previousNode.next = null; // указываем предыдущую ссылку как пустую, т.к. не хотим обрабатывать ее на данный момент

    }

    // нахождение среднего элемента - самый простой и неэффективный способ

    public T findMiddleElement(){

        if(head == null){
            return null;
        }
        if(head.next == null){
            return head.value;
        }
        if(head.next.next == null){

            return head.next.value;
        }

        Node currentNode = head;
        int size = 0;
        while(currentNode != null){

            currentNode = currentNode.next;
            size++;
        }

        currentNode = head;
        for(int i = 0; i < (size - 1) / 2; i++){

            currentNode = currentNode.next;
        }
        return currentNode.value;
    } 

    // второй способ рекурсивный
    // создаем вспомогательный класс, чтобы сохранять ссылки размера списка и среднего элемента во время выполнения всех 
    // рекурсивных вызовов:
    private class MiddleElemRecursion{
        Node middleNode;
        int length = 0;
    }

    private void findMiddleRecursively(Node node,MiddleElemRecursion middleAux){
        // в случае если мы достигли конца списка:
        if(node == null){

            middleAux.length = middleAux.length /2;
            return;
        }

        middleAux.length++;
        findMiddleRecursively(node.next,middleAux);
        // если мы нашли середину списка: 
        if(middleAux.length == 0){
            middleAux.middleNode = node;
        }

        middleAux.length--;

    }

    public T findMiddleRecursively(){
        if(head == null){
            return null;
        }

        MiddleElemRecursion middleElemRecursion = new MiddleElemRecursion();
        findMiddleRecursively(head,middleElemRecursion);
        return middleElemRecursion.middleNode.value;
    }

    // добавление элемента в середину списка

    public void addInMiddle(int index,T newValue){

        if(head == null){
            head.value = newValue;
            return;
        }

        if(head.next == null){
            head.next.value = newValue;
            return;
        }

        Node newNode = new Node();
        newNode.value = newValue;
        if(index >= getLinkedListLength()){
            System.out.println("Индекс вставки превышает длину списка");
        }else{
            Node currentNode = head;
            int currentIndex = 0;
            while(currentIndex < index -1){
                currentNode = currentNode.next;
                currentIndex++;
            }
            newNode.next = currentNode.next;
            currentNode.next = newNode;
        }

    }

    private int getLinkedListLength(){
        int res = 0;
        Node currentNode = head;
        while(currentNode != null){
            currentNode = currentNode.next;
            res++;
        }
        return res++;
    }

    public void addInTheMiddle(T value, int index){


        int position = 0;
        Node currentNode = head;
        while(currentNode.next != null && position != index){

            currentNode = currentNode.next;
            position++;
        }
        if(index != position){
            throw new IndexOutOfBoundsException("index: " + index);
        }
        Node newNode = new Node();
        newNode.value = value;
        if(head == null){
            head = newNode;
        }else{

            newNode.next = currentNode.next;
            currentNode.next = newNode;
        }


    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        Node node = head;
        while(node != null){

            stringBuilder.append(node.value);
            stringBuilder.append("\n");
            node = node.next;
        }

        return stringBuilder.toString();


    }



}
