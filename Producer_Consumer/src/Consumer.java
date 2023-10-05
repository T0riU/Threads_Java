import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Consumer implements Runnable {
    private final BlockingQueue sharedQueue;
    private final String word;
    private final ProducerConsumerExample pce;

    public Consumer(String word, ProducerConsumerExample pce, BlockingQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
        this.pce = pce;
        this.word = word;
    }

    private int countWords(String s) {
        int i = 0;
        Pattern p = Pattern.compile(word);
        Matcher m = p.matcher(s);
        while (m.find()) {
            i++;
        }
        return i;
    }

    @Override
    public void run() {
        while (true) {
            String line = (String) sharedQueue.poll();
            if (line != null) pce.addWordCount(countWords(line));
            //End condition
            if (sharedQueue.size() == 0 && !pce.isReadingData()) break;
        }
    }
}
