import bag_of_tasks.*;
import java.util.*;

class userProgram {
    public static void main(String[] args) {
        int primesToFind;
        List<Task> futures = new ArrayList<Task>(){};

        BagOfTasks bag = new BagOfTasks(1);

        Task t1 = new primeTask(1);
        Task t2 = new primeTask(2);
        Task t3 = new primeTask(3);

        Task[] t1Deps = {};
        Task[] t2Deps = {t1};
        Task[] t3Deps = {t2,t1};

        bag.submitTask(t2,t2Deps);
        bag.submitTask(t3,t3Deps);
        bag.submitTask(t1);

        /*for (Task t : futures) {
            try {
                System.out.println("The result is: " + t.getResult());
            } catch (Exception e) {
                System.out.print(e);
            }

        }
        */
    }

    public static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid positive integer.");
            return false;
        }
        return true;
    }
}

class primeTask extends Task {
    int numberToFind;

    public primeTask(int numberToFind){
        this.numberToFind = numberToFind;
    }

    public Integer call() throws InterruptedException{
        if(numberToFind == 1){
            Thread.sleep(1000);
        }
        System.out.println(numberToFind);
        return numberToFind;
    }

    public int getInput() {
        return numberToFind;
    }
}






