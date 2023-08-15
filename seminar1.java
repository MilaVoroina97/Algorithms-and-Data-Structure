package Seminar;



import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class seminar1{

    public static void main(String[] args) {

        int a = 7 % 4; // 3
        int b = 13 % 6; // 1

        int lastNumber = 44;
        AtomicInteger counter = new AtomicInteger();
        StringBuilder stringBuilder = new StringBuilder("AAAA");
        System.out.printf("(1) Сумма всех чисел от 1 до %d равна %d\n", lastNumber, sum(lastNumber, counter));
        System.out.println("Кол-во итераций: " + counter.get());

        System.out.printf("(2) Сумма всех чисел от 1 до %d равна %d\n", lastNumber, sum2(lastNumber));

        counter.set(0);
        System.out.printf("Простые числа в диапазоне от 1 до %d: \n%s",
                lastNumber, findSimpleNumbers(lastNumber, counter));
        System.out.println("Кол-во итераций: " + counter.get());

        f(4);

        int c = 0;
        while (true){
            c++;
            if (c == 100)
                break;
        }

        AtomicLong longCounter = new AtomicLong();
        long startTime = System.currentTimeMillis();
        System.out.printf("Число Фибоначчи для индекса %d равно %d (РЕКУРСИЯ)\n", lastNumber, fb1(lastNumber, longCounter));
        System.out.println("Кол-во итераций: " + longCounter.get());
        long endTime = System.currentTimeMillis();
        System.out.printf("Операция выполнена за  %d мс.\n", endTime - startTime);
        longCounter.set(0);
        startTime = System.currentTimeMillis();
        System.out.printf("Число Фибоначчи для индекса %d равно %d\n", lastNumber, fb2(lastNumber, longCounter));
        System.out.println("Кол-во итераций: " + longCounter.get());
        endTime = System.currentTimeMillis();
        System.out.printf("Операция выполнена за  %d мс.\n", endTime - startTime);

    }

    /**
     * [1] Необходимо написать алгоритм, считающий сумму всех чисел от 1 до N.
     */
    public static int sum(int lastNumber, AtomicInteger counter){
        int sum = 0;
        for (int i = 1; i <= lastNumber; i++){
            sum += i;
            counter.getAndIncrement(); // +1;
            // sum = sum + 1;
        }
        return sum;
    }

    public static int sum2(int lastNumber){
        return ((lastNumber+ 1 ) / 2 ) * lastNumber;
    }

    /**
     * [2] Написать алгоритм поиска простых чисел (делятся только на себя и на 1)
     *
     *  1 2 3 4 5
     *
     */
    public static ArrayList<Integer> findSimpleNumbers(int lastNumber, AtomicInteger counter){
       ArrayList<Integer> arrayList = new ArrayList<>();
       boolean simple;
       for (int i = 1; i <= lastNumber; i++){
           simple = true;
           for (int j = 2; j < i; j++){
               counter.getAndIncrement(); // +1
               if (i % j == 0) {
                   simple = false;
                   break;
               }
           }
           if (simple)
               arrayList.add(i);
       }
       return arrayList;
    }


    /**
     * 4
     * @param n
     */
    static void f(int n){
        System.out.println(n);
        if (n >= 3){
            f(n - 1);
            f(n - 2);
            f(n - 2);
        }
    }

    /**
     * [3] Разработать алгоритм поиска нужного числа последовательности Фибоначчи,
     *
     *
     * 0 1 2 3 4 5 6  7  8  9 10
     *
     * 0 1 1 2 3 5 8 13 21 34 55 ...
     */
    static long fb1(int num, AtomicLong counter){
        counter.getAndIncrement(); // +1
        if (num == 0 || num == 1)
            return num;
        return fb1(num - 1, counter) + fb1(num - 2, counter);
    }

    static long fb2(int num, AtomicLong counter){
        if (num == 0 || num == 1)
            return num;
        long[] numbers = new long[num + 1];
        numbers[0] = 0;
        numbers[1] = 1;
        for (int i = 2; i < numbers.length; i++){
            counter.getAndIncrement();
            numbers[i] = numbers[i - 1] + numbers[i - 2];
        }
        return numbers[num];
    }
}




    
