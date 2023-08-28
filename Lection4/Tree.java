package Lection4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Tree {


    private Node root; //корневой элемент, с которого начинаются все отсчеты

  // обход в глубину

    public boolean exists(int value){

        if(root != null){
            Node node = nodeFind(root, value);
                if(node != null){
                    return true;
                }
        }

        return false;

    }

    // приватный рекурсивный метод поиска интересующей нас ноды, на вход принимает аргументы - ноды, которую мы будем проверять и 
    // значение,которое нужно будет искать
    private Node nodeFind(Node node, int value){

        if(node.value == value){
            return node;
        }else{
            // если текущая нода не равна необходимому нам значению, то перебираем ее детей

            for(Node child : node.children){
                Node result = nodeFind(child, value);
                if(result != null){
                    return result;
                }
            }

        }

        return null;
    }

    // обход в ширину

    public Node find(int value){
        List<Node> line = new ArrayList<>(); //текущая линия детей корня
        line.add(root);
        while(line.size() > 0){ //до тех пор пока в нашем объекте существуют какие-либо дети,мы будем перебирать текущие линии
            List<Node> nextLine = new ArrayList<>();
            for(Node currentNode : line){

                if(currentNode.value == value){
                    return currentNode;
                }else{
                    nextLine.addAll(currentNode.children); 
                }

                line = nextLine; //обновляем линию и перезапускаем цикл
            }
        }
        return null;
    }

    private void addNode(Node currentNode, int value){
        Node newNode = new Node();
        newNode.value = value;
        if(root == null){
            root = newNode;
        }else{
            currentNode.children.add(newNode);
        }
       
        
    }

    public void add(int value){
        addNode(root, value);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        print(stringBuilder, "", "");
        return stringBuilder.toString();
    }

    private void print(StringBuilder buffer, String prefix,String childrenPrefix){

        buffer.append(prefix);
        buffer.append((root.value));
        buffer.append("\n");
        for(Iterator<Node> it = root.children.iterator(); it.hasNext();){

            Node next = it.next();
            if(it.hasNext()){
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            }else{
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }

        }


    }
    
    public class Node{
        int value;
        List<Node> children = new ArrayList<>(); //список детей узла
        public void print(StringBuilder buffer, String string, String string2) {
        }

    }
    
}
