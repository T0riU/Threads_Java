import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerExample {
    private long wordCount = 0;
    private int numProducers = 2;
    private boolean readingData = true;
    public synchronized void addWordCount(long addCount) {
        wordCount += addCount;
    }
    public synchronized long getWordCount() {
        return wordCount;
    }
    public synchronized long getNumProducers() {
        return numProducers;
    }
    public synchronized void setReadingData(boolean reading) {
        readingData = reading;
    }
    public boolean isReadingData() {
        return readingData;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String word = scanner.nextLine();
        long startTime = System.currentTimeMillis();
        int totalCons = 2;
        ProducerConsumerExample pce = new ProducerConsumerExample();
        //Creating shared object
        BlockingQueue sharedQueue = new LinkedBlockingQueue();
        //Creating Producer and Consumer Thread
        Thread[] prodThreads = new Thread[pce.numProducers];
        Thread[] consThreads = new Thread[totalCons];


        //Starting producer and Consumer thread
        for (int i = 0; i < pce.getNumProducers(); i++) {
            Producer prod = new Producer(i, pce, sharedQueue);
            prodThreads[i] = new Thread(prod);
            prodThreads[i].start();
        }
        for (int i = 0; i < totalCons; i++) {
            Consumer cons = new Consumer(word, pce, sharedQueue);
            consThreads[i] = new Thread(cons);
            consThreads[i].start();
        }
        try {
            for (Thread thread : prodThreads) {
                thread.join();
            }
            for (Thread thread : consThreads) {
                thread.join();
            }

            System.out.println("Word count: "+ String.valueOf(pce.getWordCount()));
            long endTime = System.currentTimeMillis();
            System.out.println("Calculated in " + (endTime - startTime) + " milliseconds");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
