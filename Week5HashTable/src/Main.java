import java.util.Random;

public class Main {
    public static void main(String[] args) {
        MyHashTable<MyTestingClass, String> table = new MyHashTable<>();
        Random random = new Random();



        for (int i = 0; i < 10000; i++) {
            int id = random.nextInt(1000);
            String name = "Name" + i;
            MyTestingClass key = new MyTestingClass(id, name);
            table.put(key, "Value" + i);
        }


        printBucketSizes(table);
    }

    public static void printBucketSizes(MyHashTable<MyTestingClass, String> table) {
        int[] bucketSizes = new int[table.chainArray.length];
        for (int i = 0; i < table.chainArray.length; i++) {
            MyHashTable.HashNode<MyTestingClass, String> current = table.chainArray[i];
            while (current != null) {
                bucketSizes[i]++;
                current = current.next;
            }
        }

        System.out.println("Bucket Sizes:");
        for (int i = 0; i < bucketSizes.length; i++) {
            System.out.println("Bucket " + i + ": " + bucketSizes[i] + " elements");
        }
    }
}