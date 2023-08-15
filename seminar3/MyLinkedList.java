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


    public void sort(Comparator<T> comparator){
        Node node = head;
        while (node != null){

            Node minValueNode = node;

            Node node2 = node.next;
            while (node2 != null){
                if (comparator.compare(minValueNode.value, node2.value) > 0){
                    minValueNode = node2;
                }
                node2 = node2.next;
            }

            if (minValueNode != node){
                T buf = node.value;
                node.value = minValueNode.value;
                minValueNode.value = buf;
            }

            node = node.next;
        }
    }


    public void addLast(T value){
        Node node = new Node();
        node.value = value;
        if (head == null){
            head = node;
        }
        else {
            Node lastNode = head;
            while (lastNode.next != null){
                lastNode = lastNode.next;
            }
            lastNode.next = node;
        }

    }

    public void removeLast(){
        if (head == null)
            return;
        Node node = head;
        while (node.next != null){
            if (node.next.next == null){
                node.next = null;
                return;
            }
            node = node.next;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Node node = head;
        while (node != null){
            stringBuilder.append(node.value);
            stringBuilder.append('\n');
            node = node.next;
        }

        return stringBuilder.toString();

    }



}
