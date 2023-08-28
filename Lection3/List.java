package Lection3;

public class List {

    Node head; //ссылка на первый элемент
    Node tail; // ссылка на последний элемент

    public void add(int value){ // добавление в конец списка
        Node newNode = new Node();
        newNode.value = value;
        if(head == null){
            head = newNode;
            tail = newNode;
        }else{
            tail.next = newNode;// следующая нода тэйла является новой нодой
            newNode.previous = tail; // новая нода ссылается на текущий хвост
            tail = newNode;// обновляем значение тэйла новой нодой 
        }
    }

    public void add(int value, Node node){  // добавление элемента в произвольном месте списка, после указанной ноды 
        // встраиваемся в текущую цепочку и вставляем туда нужное значение
        Node next = node.next;
        Node newNode = new Node();
        newNode.value = value;
        node.next = newNode;
        newNode.previous = node;
        if(next == null){ //в случае, если мы добавляем элемент в конец списка, то мы ссылаемся на тэйл, и тогда нужно обновить его 
            // значение новой нодой 

            tail = newNode; //если у элемента списка нет следующего элемента, то тогда это можно считать концом списка

        }else{

            next.previous = newNode; 
            newNode.next = next;
        }
    }

    public Node find(int value){

        Node currentNode = head;

        while(currentNode != null){ // перебор всех возможных детей, пока нода next у нас заполняется,линейная сложность
            if(currentNode.value == value){
                return currentNode;
            }

            currentNode = currentNode.next;
        }
        return null;

    }

    public void deleteNode(Node node){ //удаление ноды по его значению
        Node previous = node.previous;
        Node next = node.next;
        if(previous == null){  // в случае если удаляемая нода является главой(head)

            next.previous = null;
            head = next; // теперь началом является следующий по очередности элемент

        }else{
            if(next == null){  //если удаляемый объект является концом списка

                previous.next = null;
                tail = previous; 
            }else{

                // смещаем ссылки так , чтобы затерлись ссылки на удаляемый объект
                previous.next = next; 
                next.previous = previous;
            }
        }
    }
    // разворот для двусвязанного списка
    public void revert(){
        Node currentNode = head;
        while(currentNode != null){
            Node next = currentNode.next;
            Node previous = currentNode.previous;
            currentNode.next = previous; // меняем местами узлы для разворота 
            currentNode.previous = next;
            // добавляем обработку событий head и tail
            if(previous == null){

                tail = currentNode;

            }
            if(next == null){

                head = currentNode;
            }
            currentNode = next;
        }
    }

    // разворот для односвязанного списка

    public void revertOneNode(){

        if(head != null && head.next != null){

            // Node temp = head;
            revert(head.next, head);
            // temp.next = null;

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

    // LIFO Stack

    public void push(int value){ // функция для добавления элемента в начало стека

        Node newNode = new Node();
        newNode.value = value;
        newNode.next = head;
        head = newNode;

    }

    // извлечение элемента из начала списка 

    public Integer pop(){

        Integer result = null;

        if(head != null){

            result = head.value;
            head = head.next;
        }
        
        return result;

    }

    // метод для очереди - добавление элемента в начало очереди 
    public void pushQueue(int value){

        Node newNode = new Node();
        newNode.value = value;
        newNode.next = head;
        head.previous = newNode;
        head = newNode;

    }

    // метод для очереди - извлечение элемента из конца очереди

    public Integer peek(){

        Integer result = null;
        if(tail != null){

            result = tail.value;
            tail.previous.next = null;
            tail = tail.previous;

        }

        return result;
    }


    public class Node{ // класс узел

        int value; // значение узла
        Node next; // описывает следующий элемент 
        Node previous; //описывает предыдущее значение

    }
}
