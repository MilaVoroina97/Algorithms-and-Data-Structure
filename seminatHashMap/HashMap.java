package Seminar.seminatHashMap;

import java.util.Iterator;

public class HashMap<K, V> implements Iterable<HashMap.Entity>{


    private static final int INIT_BUCKET_COUNT = 16;

    private static final double LOAD_FACTOR = 0.5;

    private Bucket.Node currentNode;

    private int currentIndex;

    private int size; //кол-во элементов в массиве бакетов

    // в основе HashMap должен быть массив типа Bucket(with LinkedList)
    private Bucket[] buckets;

    @Override
    public Iterator<HashMap.Entity> iterator() {
        return new HashMapIterator();
    }

    class HashMapIterator implements Iterator<HashMap.Entity>{

        @Override
        public boolean hasNext() {
            // при запуске метода currentNode будет равен null, поэтому нам необходимо сначала проинициализировать currentNode и дать
            // ему значение из значений массива бакетов
            if(currentNode == null){
                for(int i = 0; i < buckets.length; i++){
                    if(buckets[i] != null && buckets[i].head != null){
                        currentIndex = i;
                        currentNode = buckets[i].head;
                        return true;
                    }
                }

                return false;
            }
            else{

                // if(get((K)currentNode.value.key) == null){  //частный случай : в случай если у ключа нет значения(оно равно null), то
                //     // считаем, что эта нода пустая 
                //     currentNode = null;
                //     currentIndex = 0;
                //     return hasNext();
                // }else{
                    HashMap.Bucket.Node node = currentNode;
                     currentIndex = calculateBucketIndex((K)node.value.key);
                    if(node.next != null){ //если в текущей ноде находится не одна пара ключ-значение, то переходим к следующей паре 
                        // и возвращаем ее 
                        currentNode = node.next;
                        return true;
                    }
                    // запускаем цикл по проходу всех бакетов в массиве

                    for(int i = ++currentIndex; i < buckets.length; i++){
                        if(buckets[i] != null && buckets[i].head != null){
                            currentIndex = i;
                            currentNode = buckets[i].head;
                            return true;
                        }
                    }

                    // после того мы прошли по всем нодам и бакетам,завершаем метод

                    currentNode  = null;
                    currentIndex = 0;
                    return false;
                }
            }
        //}

        @Override
        public Entity next() {

            if(currentNode == null){
                for(int i = 0; i < buckets.length; i++){
                    if(buckets[i] != null && buckets[i].head != null){
                        currentIndex = i;
                        currentNode = buckets[i].head;
                        return currentNode.value;
                    }
                }
                return null;
            }
            // else if(get((K)currentNode.value.key) == null){

            //     currentNode = null;
            //     currentIndex = 0;
            //     return null;
            // }
            else{
                return currentNode.value;
            }
        }   
        
    }

    public HashMap(int initCount){

        buckets = new Bucket[initCount];

    }

    public HashMap(){
        buckets = new Bucket[INIT_BUCKET_COUNT];
    }
    // опишем сущность, которая будет объединять ключ и значение -т.е. элемент хэш-таблицы

     class Entity{
        K key;
        V value;

        @Override
        public String toString(){
            return String.format("%s - %s",key,value);
        }
    }

     class Bucket<K, V>{ //создаем класс корзина, который будет описывать связанный список, в котором хранятся пары ключ-значение
        // указатель на первый элемент связанного списка
        Node head; 
        class Node{ //узел связанного списка

            // next - это указатель на следующей узел(элемент) связанного списка
            Node next;

            Entity value; //значение узла, указывающее на элемент хэш-таблицы
        }

        // добавление нового элемента в хэш-таблицу
        public V add(Entity entity){

            Node newNode = new Node();
            newNode.value = entity;
            if(head == null){
                head = newNode;
                return null;
            }else{
                Node currentNode = head;
                // создаем вечный цикл для того, чтобы пройти по всем элементам связанного списка и проверить у каждого элемента
                // значение ключа 
                while(true){
                    if(currentNode.value.key.equals(entity.key)){ //в случае, если такой ключ уже существует в текущем связанном списке,
                        // то необходимо произвести замену значений, при этом старое значение мы сохраняем

                        V buff = (V) currentNode.value.value;
                        currentNode.value.value = entity.value;
                        return buff;
                    }if(currentNode.next != null){ //в противном случае мы двигаемся дальше по связанному списку
                        currentNode = currentNode.next;
                    }else{
                        currentNode.next = newNode;
                        return null; //возвращаем в данном случае null, т.к. мы добавили новый элемент и ничего не перезаписали
                    }
                }
            }
        }

        // метод поиска значения по ключу

        public V findValue(K key){
            if(head == null){
                return null;
            }
            Node node = head;
            while(node != null){
                if(node.value.key.equals(key)){
                    return (V)node.value.value;
                }
                node = node.next;

            }

            return null;
        }

        public V removeValue(K key){
            if(head == null){
                return null;
            }
            if(head.value.key.equals(key)){
                V buf = (V) head.value.value;
                head = head.next;
                return buf;
            }else{
                Node currentNode = head;
                while(currentNode.next != null){
                    if(currentNode.next.value.key.equals(key)){

                        V buff = (V) currentNode.next.value.value;
                        currentNode.next = currentNode.next.next;
                        return buff;
                    }
                    currentNode = currentNode.next;
                }
            }


            return null;
        }
    }

    private int calculateBucketIndex(K key){

        return Math.abs(key.hashCode()) % buckets.length;
    }

    public V put(K key,V value){

        if(buckets.length * LOAD_FACTOR <= size){ //расширяем длину массива в два раза перед добавляем, если его длина в два раза 
            // меньше,чем кол-во элементов
            recalculate();
        }

        int index = calculateBucketIndex(key);
        // перед добавлением пары необходимо проинициализировать бакет, т.к. массив с бакетами является ссылочным типом, который был
        // создан на основе класса(изначально все элементы массива являются null)
        Bucket bucket = buckets[index];
        // проверяем, проиницилизирован ли объект по вычисленному индексу
        if(bucket == null){

             bucket = new Bucket();
            buckets[index] = bucket; // после инициализации бакет с необходимым нам индексом будет уже указывать не на null,а на новый
            // объект бакета

        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V buff =  (V) bucket.add(entity);
        if(buff == null){ //если нет повторяющихся ключей и мы добавляем пару как новое значение в массив (возвращаем null),
            // то увеличиваем размер массива на 1
            size++;
        }

        return buff;
    }


    private void recalculate(){
        // перед рекалькуляцией обнуляем кол-во элементов массива для того, чтобы не задвоить само кол-во этих элементов
        size = 0;
        Bucket<K,V>[] oldBuckets = buckets;
        buckets = new Bucket[oldBuckets.length *2];
        for(int i = 0; i < oldBuckets.length; i++){
            Bucket<K,V> oldBucket = oldBuckets[i];
            if(oldBucket != null){ //если бакет был проинициализирован(т.е. не равен null)
                    // нам нужно пройти по связанному списку, который лежит внутри текущего бакета
                Bucket.Node node = oldBucket.head;
                while(node != null){
                    // здесь мы вызываем метод put опять для того, чтобы перезаписать элемент массива уже с новым индексом
                    put((K)node.value.key,(V)node.value.value);
                    node = node.next;
                }

            }
        }
    }

    public V get(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            return null;
        }
        return (V)bucket.findValue(key);
    }


    public V remove(K key){

        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if(bucket == null){
            return null;
        }

        V buf = (V)bucket.removeValue(key);
        // если элемент будет равен null(т.е. не существует искомого нами элемента для удаления),то элемент не будет удален и размер
        // массива (size) останется прежнем, но если удаляемый объект не равен null,то нам необходимо уменьшить size на 1

        if(buf != null){
            size--;
        }
        return buf;
    }

}
