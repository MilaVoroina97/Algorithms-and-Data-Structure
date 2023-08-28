package Lection4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args){
        Tree tree = new Tree();
        tree.add(5);
        tree.add(6);
        tree.add(7);
        tree.add(8);
        tree.add(9);
        tree.add(5);
        tree.find(6);
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.add(2);
        redBlackTree.add(3);
        redBlackTree.add(45);
        redBlackTree.add(55);
        redBlackTree.add(22);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            while(true){

                try{
                    int value = Integer.parseInt(reader.readLine());
                    redBlackTree.add(value);
                    System.out.println("finish");
                }catch(Exception ignored){

                }
            }
        }catch(IOException e){
            throw new RuntimeException();
        }


    }
    
}
