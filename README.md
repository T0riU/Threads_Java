# Java Threads — Lab Work

Three Java projects exploring multithreading: parallel π calculation, a synchronized canteen simulation, and a Producer-Consumer word counter.

---

## Project Structure

```
Lab1/                         π calculation — sequential vs parallel
  src/
    SequentialPi.java         Single-threaded π, 100M steps
    SequentialPi2.java        Single-threaded π, 1B steps with timing
    ParallelPi.java           2-thread π, 10M steps
    ParallelPi2.java          2-thread π, 1B steps with timing
    FourThread.java           4-thread π, 1M steps with timing

Lab2/                         Canteen simulation with synchronized access
  src/
    Canteen.java              Shared food bank, synchronized feedStudent()
    Student.java              Runnable student that keeps eating until food runs out

Producer_Consumer/            Parallel word counter using BlockingQueue
  src/
    ProducerConsumerExample.java   Entry point, shared state, thread orchestration
    Producer.java                  Reads HTML files and puts lines into the queue
    Consumer.java                  Polls lines from queue and counts word occurrences
  texts/                           95 HTML files used as input corpus
```

---

## Lab 1 — π Calculation

Approximates π using the rectangle integration method: splitting the range [0,1] into N steps and summing 4/(1+x²).

| Class | Threads | Steps | Timing |
|-------|---------|-------|--------|
| SequentialPi | 1 | 100 000 000 | No |
| SequentialPi2 | 1 | 1 000 000 000 | Yes |
| ParallelPi | 2 | 10 000 000 | No |
| ParallelPi2 | 2 | 1 000 000 000 | Yes |
| FourThread | 4 | 1 000 000 | Yes |

Each parallel class extends Thread. The step range is evenly split across threads; partial sums are joined and combined after all threads finish.

---

## Lab 2 — Canteen Simulation

Models a university canteen where multiple students compete for a shared food supply.

Canteen holds a food bank (default 100 units). feedStudent() is synchronized — only one student can eat at a time, preventing race conditions. Each Student implements Runnable and repeatedly calls feedStudent() with a random portion size (1–5 units) until the food runs out. The main thread spawns 5 student threads against a 120-unit food bank and prints a summary of portions served and food remaining.

---

## Producer-Consumer — Word Counter

Counts occurrences of a user-supplied word across 95 HTML files using parallel producers and consumers.

- 2 Producer threads read files from the texts/ folder (interleaved by thread index) and push each line into a shared LinkedBlockingQueue.
- 2 Consumer threads poll lines from the queue, apply a regex match for the target word, and accumulate the count into a synchronized shared counter.
- The main thread joins all threads and prints the total word count and elapsed time.

### Running

```
cd Producer_Consumer
javac -d out src/*.java
java -cp out ProducerConsumerExample
```

When prompted, enter any word to search across the corpus.

---

## Requirements

- Java 11 or later
- IntelliJ IDEA (each lab has its own .iml / .idea config)

## Running Lab1 / Lab2

Each class has its own main() and can be run directly from the IDE or command line:

```bash
# Example for Lab1
javac Lab1/src/ParallelPi2.java
java -cp Lab1/src ParallelPi2

# Example for Lab2
javac Lab2/src/Canteen.java Lab2/src/Student.java
java -cp Lab2/src Canteen
```
