package Lection4;

public class RedBlackTree {

    private Node root;

    // добавление новых нод в КЧД

    public boolean add(int value){
        if(root != null){
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return result;
        }else{
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }
    private boolean addNode(Node node,int value){
        if(node.value == value){ // так как в КЧД все значения должны быть уникальными, проверяем есть ли уже такое значение у нас в КЧД
            return false;
        }else{
            if(node.value > value){ //если добавляемое значение меньше, чем текущая нода, и при этом левый ребенок существует,то
                // мы запускаем рекурсивный поиск вниз по левому ребенку с проверкой можно ли создать ноду там
                if(node.leftChild != null){
                    boolean result = addNode(node.leftChild, value);
                    // на каждом этапе погружения в рекурсию при обходе в глубину производим ребалансировку
                    node.leftChild = rebalance(node.leftChild);
                    return result;
                }else{ //если левой ноды не существует, то мы считаем, что нашли место, куда можно вставить новое значение
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED; //все вновь созданные ноды всегда считаются красными
                    node.leftChild.value = value;
                    return true;
                }
            }else{
                if(node.rightChild != null){
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                }else{
                    node.rightChild = new Node();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }

    }

    // ОПЕРАЦИИ БАЛАНСИРОВКИ:

    // 1. Ребалансировка может выполняться несколько раз, до тех пор пока не будет ни одного условия, при котором требовалась бы балансировка
    // 2. После правостороннего поворота мы получим перемещение красной ноды справа налево
    // 3. После левостороннего поворота мы получим перемещение левой ноды направо,красная нода у нас станет правым ребенком, что недопустимо
    // в концепции левостороннего красно-черного дерева
    // 4. Такая операция производится только в момент, когда необходимо сменить цвет

    private Node rebalance(Node node){

        Node result = node;
        boolean needRebalance;
        do{
            needRebalance = false;
            // при условии, что есть правый ребенок и этот ребенок красный, а левый ребенок черный, то нужно производить правый поворот
            // (перемещаем правого красного ребенка налево относительно родителя) и установить флаг для повторной балансировки
            if (result.rightChild != null && result.rightChild.color == Color.RED && 
            (result.leftChild == null || result.leftChild.color == Color.BLACK)){

                needRebalance = true;
                result = rightSwap(result);
                // при условии, что есть левый ребенок и этот ребенок красный и также его левый ребенок красный, то необходимо произвести
                // левый поворот(перемещаем левого красного ребенка направо относительно родителя)
            }if(result.leftChild != null && result.leftChild.color == Color.RED &&
            result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED){
                needRebalance =true;
                result = leftSwap(result);
                // 
            }if(result.leftChild != null && result.leftChild.color == Color.RED &&
            result.rightChild != null && result.rightChild.color == Color.RED){
                needRebalance = true;
                colorSwap(result);

            }
        }while(needRebalance);


        return result;
    }

    private Node rightSwap(Node node){

        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    // левосторонний поворот

    private Node leftSwap(Node node){
        Node leftChild = node.leftChild; //берем левого ребенка
        // берем промежуточный элемент, который будет менять своего родителя
        Node betweenChild = leftChild.rightChild; // имеет значением между красной левой нодой и рутовой
        // при левостороннем повороте левая нода считается красной,поэтому вместо правого ребенка красной ноды мы назначем рутовый 
        // элемент
        leftChild.rightChild = node;
        // у родителя красной нодой становится промежуточный ребенок(ранее мы ссылались на красную левую ноду)
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED; // корень, который опустился ниже после обмена, обязательно становится красным
        return leftChild;
    }

    // смена цвета(когда  у ноды два красных ребенка)
    private void colorSwap(Node node){
        node.leftChild.color = Color.BLACK;
        node.rightChild.color = Color.BLACK;
        node.color = Color.RED;
    }



    public class Node{
        private int value;
        private Color color;
        private Node leftChild;
        private Node rightChild;

        @Override
        public String toString(){
            return "Node{ "+ "value = " + this.value + ", color = " + this.color;
        }
    }

    private enum Color{

        RED, BLACK
    }
    
}
