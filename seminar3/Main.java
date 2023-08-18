
public class Main {

    public static void main(String[] args) {
        MyLinkedList<Employee> linkedList = new MyLinkedList<>();

        linkedList.addFirst(new Employee("AAAA", 40));
        linkedList.addFirst(new Employee("BBB", 41));
        linkedList.addFirst(new Employee("CC", 33));
        linkedList.addFirst(new Employee("BBB", 41));
        linkedList.addFirst(new Employee("BBB", 55));
        linkedList.addFirst(new Employee("BBB", 21));
        linkedList.addFirst(new Employee("BBB", 33));
        linkedList.addFirst(new Employee("DDDDD", 24));
        System.out.println("Список до разворота : ");
        System.out.println(linkedList);
        System.out.println();
        System.out.println("Список после разворота : ");
        linkedList.revert();
        System.out.println(linkedList);
    }

}
