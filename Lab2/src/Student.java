public class Student  implements Runnable{

    private static int nextStudentNumber = 1;

    //Student id
    private int studentNumber;
    //Amount of food student need
    private int needFoodQuantity = 1;

    //Student waiting time
    private int sleepTime = 10;


    //Ref to student canteen
    private Canteen canteen = null;

    private String name = "Max";

    @Override
    public void run() {
        while(canteen.feedStudent(needFoodQuantity, studentNumber, this.name)) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Student(String name, int needFoodQuantity, Canteen canteen) {
        super();
        this.name = name;
        this.studentNumber = nextStudentNumber;
        nextStudentNumber++;
        this.needFoodQuantity = needFoodQuantity;
        this.canteen = canteen;
    }

}
