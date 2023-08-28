public class MinHeap {

    private int[] heapArray;
    private int capacity;
    private int current_heap_size;

    public MinHeap(int n) {
        capacity = n;
        heapArray = new int[capacity];
        current_heap_size = 0;
    }

    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private int getParent(int key){
        return (key -1 ) / 2;
    }

    private int getLeftChild(int key){
        return (key + 1) * 2;
    }

      private int getRightChild(int key){
        return (key + 2) * 2;
    }

    public boolean insertKey(int value){
        if(current_heap_size == capacity){
            return false; // heap is full
        }

        // First insert the new key at the end

        int i = current_heap_size;
        heapArray[i] = value;
        current_heap_size++;

        // Fix the min heap property if it is violated

        while(i != 0 && heapArray[i] < heapArray[getParent(i)]){
            swap(heapArray, i, value);
            i = getParent(i);
        }

        return true;
    }
    // Decreases value of given key to new_val.
    // It is assumed that new_val is smaller
    // than heapArray[key].

    public void decreaseKey(int key, int value){

        heapArray[key] = value;

        while(key != 0 && heapArray[key] < heapArray[getParent(key)]){
            swap(heapArray, key,getParent(key));
            key = getParent(key);
        }

    }

    public int getMin(){
        return heapArray[0];
    }

    // Method to remove minimum element
    // (or root) from min heap

    public int extractMin(){
        if(current_heap_size <= 0){
            return Integer.MAX_VALUE;
        }

        if(current_heap_size == 1){
            current_heap_size--;
            return heapArray[0];
        }

          // Store the minimum value,
        // and remove it from heap

        int root = heapArray[0];
        heapArray[0] = heapArray[current_heap_size -1];
        current_heap_size--;
        minHeapify(0);
        return root;
    }

    // A recursive method to heapify a subtree
    // with the root at given index
    // This method assumes that the subtrees
    // are already heapified

    public void minHeapify(int key){  // displace down

        int l = getLeftChild(key);
        int r = getRightChild(key);
        int smallest = key;
        if(l < current_heap_size && heapArray[l] < heapArray[smallest]){
            smallest = l;
        }if(r < current_heap_size && heapArray[r] < heapArray[smallest]){
            smallest = r;
        }if(smallest != key){
            swap(heapArray, key, smallest);
            minHeapify(smallest);
        }

    }

     // This function deletes key at the
    // given index. It first reduced value
    // to minus infinite, then calls extractMin()

    public void deleteKey(int key){
        decreaseKey(key, Integer.MAX_VALUE);
        extractMin();
    }

    // Increases value of given key to new_val.
    // It is assumed that new_val is greater
    // than heapArray[key].
    // Heapify from the given key

    public void increaseKey(int key,int new_val){
        heapArray[key] = new_val;
        minHeapify(key);
    }

     // Changes value on a key

     public void changeValue(int key, int new_val){
        if(heapArray[key] == new_val) return;
        if(heapArray[key] < new_val){
            increaseKey(key, new_val);
        }else{
            decreaseKey(key, new_val);
        }
     }



    
}
