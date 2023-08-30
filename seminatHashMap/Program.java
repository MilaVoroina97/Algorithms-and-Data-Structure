package Seminar.seminatHashMap;

public class Program {
    public static void main(String[] args) {
        HashMap<String,String> hashMap = new HashMap<>(4);
        String oldvalue = hashMap.put("+79001234567", "AAAAA");
        oldvalue = hashMap.put("+79001234563", "MMMMM");
        oldvalue = hashMap.put("+79001234560", null);
        oldvalue = hashMap.put("+79001234562", "CCC1");
        oldvalue = hashMap.put("+79001234572", "CCC2");
        oldvalue = hashMap.put("+79001234582", "CCC3");
        oldvalue = hashMap.put("+79001234592", "CCC4");
        oldvalue = hashMap.put("+79001234662", "CCC5");
        oldvalue = hashMap.put("+79001234762", "CCC6");
        oldvalue = hashMap.put("+79001234862", "CCC7");
        oldvalue = hashMap.put("+79001234962", "CCC8");
        for (HashMap.Entity entity: hashMap) {
            System.out.println(entity);
    }
    }
    
}
