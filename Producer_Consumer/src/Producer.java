
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
    private final BlockingQueue sharedQueue;
    private final int threadNumber;
    private final ProducerConsumerExample pce;
    public Producer(int threadNumber, ProducerConsumerExample pce,BlockingQueue sharedQueue) {
        this.pce = pce;
        this.sharedQueue = sharedQueue;
        this.threadNumber = threadNumber;
    }
    @Override
    public void run() {
        String filePath = "texts/";
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        for(int i = threadNumber; i < listOfFiles.length; i += pce.getNumProducers()) {
            System.out.println("Producer opens " + listOfFiles[i].getName());
            try {
                BufferedReader reader = new BufferedReader(new FileReader(listOfFiles[i]));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                while( (line = reader.readLine()) != null) {
                    sharedQueue.put(line);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        pce.setReadingData(false);
    }
}
