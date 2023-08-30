package Seminar.seminar4;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class MyHashMap<K, V> implements Iterable<MyHashMap.Entity<K,V>> {

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;
    private int capacity;
    private int size;

    // Массив бакетов (связных списков)
    private Bucket<K,V>[] buckets;

    @Override
    public Iterator<MyHashMap.Entity<K,V>> iterator() {
        return new MyHashMapIterator();
    }

    class MyHashMapIterator implements Iterator<MyHashMap.Entity<K,V>>{

        private int index;
        private Entity<K,V> currentEntry;
        private Entity<K,V> lastEntry;

        public MyHashMapIterator(){
            this.index = 0;
            this.currentEntry = null;
            this.lastEntry = null;
        }

        @Override
        public boolean hasNext() {

            if(currentEntry != null && currentEntry.next !=null){
                return true;
            }while(index < capacity){

                if(buckets[index] != null){
                    return true;
                }
                index++;

            }
            return false;
        }

        @Override
        public Entity<K,V> next() {

            if(!hasNext()){
                throw new NoSuchElementException("There is no any elements");
            }if(currentEntry == null || currentEntry.next == null){
                while(buckets[index] == null){
                    index++;
                }
            }else{
                currentEntry = currentEntry.next;
            }
            lastEntry = currentEntry;
            return currentEntry;
        }
    }

    // @Override
    // public String toString() {
    //     //TODO: Если тяжело, пойти через toString
    //     return super.toString();
    // }

    /**
     * Элемент хэш-таблицы
     */
    public static class Entity<K,V>{

        // Ключ элемента
        private K key;

        // Значение элемента
        private V value;

        private Entity<K,V> next;

        public Entity(K key, V value, Entity<K,V> next){

            this.key = key;
            this.value = value; 
            this.next = next;

        }

        public Entity(K key, V value){

            this.key = key;
            this.value = value; 
            this.next = null;

        }

        // public Entity(){

        //     this.key = (K) "null";
        //     this.value = (V) "null";
        //     this.next = 

        // }

        public K getKey(){
            return this.key;
        }

        public void setKey(K newKey){
            this.key = newKey;
        }

        public V getValue(){
            return this.value;
        }

        public void setValue(V newValue){
            this.value = newValue;
        }

        // @Override
        // public String toString() {
        //     return String.format("%s - %s", key, value);
        // }
    }




    /**
     * Связный список
     * @param <K> Ключ элемента хэш-таблицы
     * @param <V> Значение элемента хэш-таблицы
     */
    class Bucket<K, V>{

        // Указатель на первый элемент связного списка
        Node head;

        /**
         * Узел бакета (связного списка)
         */
        class Node{

            // Указатель на следующий элемент связного списка
            Node next;

            // Значение узла, указывающее на элемент хэш-таблицы
            Entity<K,V> value;
        }

        /**
         * Добавление нового элемента хэш-таблицы
         * @param entity элемент
         * @return
         */
        public V add(Entity<K,V> entity){
            Node node = new Node();
            node.value = entity;

            if (head == null){
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true){
                if (currentNode.value.key.equals(entity.key)){
                    V buf = (V)currentNode.value.value;
                    currentNode.value.value = (V) entity.value;
                    return buf;
                }
                if (currentNode.next != null){
                    currentNode = currentNode.next;
                }
                else {
                    currentNode.next = node;
                    return null;
                }
            }
        }

        public V get(K key){
            Node node = head;
            while (node != null){
                if (node.value.key.equals(key))
                    return (V)node.value.value;
                node = node.next;
            }
            return null;
        }

        public V remove(K key){
            if (head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = (V)head.value.value;
                head = head.next;
                return buf;
            }
            else {
                Node node = head;
                while (node.next != null){
                    if (node.next.value.key.equals(key)){
                        V buf = (V)node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }

    }

    private int calculateBucketIndex(K key){
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private void recalculate(){
        size = 0;
        Bucket<K, V>[] old = buckets;
        buckets = new Bucket[old.length * 2];
        for (int i = 0; i < old.length; i++){
            Bucket<K, V> bucket = old[i];
            if (bucket != null){
                Bucket.Node node = bucket.head;
                while (node != null){
                    put((K)node.value.key, (V)node.value.value);
                    node = node.next;
                }
            }
        }
    }

    public V put(K key, V value){
        if (buckets.length * LOAD_FACTOR <= size){
            recalculate();
        }
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity(key,value);
        // entity.key = key;
        // entity.value = value;

        V buf = (V)bucket.add(entity);
        if (buf == null){
            size++;
        }
        return buf;
    }

    public V get(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        return (V)bucket.get(key);
    }

    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        V buf = (V)bucket.remove(key);
        if (buf != null){
            size--;
        }
        return buf;
    }



    public MyHashMap(){
        buckets = new Bucket[INIT_BUCKET_COUNT];
    }

    public MyHashMap(int initCount){

        this.capacity = initCount;
        buckets = new Bucket[initCount];
    }

    
}
