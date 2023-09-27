import java.util.Random;

public class Canteen {

    //Amount of food
    private int foodBank = 100;
    // Number of serving dishes
    public int portionsQuantity = 0;

    //Constructor
    public Canteen() {
    }
    public Canteen(int foodCount) {
        this.foodBank = foodCount;
    }
    //method for feed student
    public synchronized boolean feedStudent(int foodQuantity, int studentNumber, String name) {
        if(foodBank-foodQuantity>=0) {
            foodBank -= foodQuantity;
            System.out.println("Student \""+name+"\" ("+ studentNumber +") ate "+ foodQuantity +" amount of food");
            portionsQuantity++;
            return true;
        } else {
            return false;
        }
    }

    //Program start point
    public static void main (String [] arguments) {
        Random random = new Random();
        String[] names = {"Max", "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hannah", "Oleh", "Victoria", "Steve", "Abdul"};
        int totalStudents = 5;
        int totalFood = 120;
        Canteen khaiCanteen = new Canteen(totalFood);

        Thread[] studentThreads = new Thread[totalStudents];
        for (int i = 0; i < totalStudents; i++) {
            int numOfFood = random.nextInt(5) + 1;
            Student student = new Student(names[random.nextInt(names.length)], numOfFood, khaiCanteen);
            studentThreads[i] = new Thread(student);
            studentThreads[i].start();
        }
        try {
            for (Thread thread : studentThreads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Serving " + khaiCanteen.portionsQuantity + " portions. Food left: " + khaiCanteen.foodBank);
    }
}
