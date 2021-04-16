import bag_of_tasks.*;
import java.util.*;

class userProgram {
    public static void main(String[] args) {
        int primesToFind;
        List<Task> futures = new ArrayList<Task>(){};

        BagOfTasks bag = new BagOfTasks(3);

        Task t1 = new squareTask(3);
        Task t2 = new addToPreviousTask(2);
        Task t3 = new addToPreviousTask(10);
        Task t4 = new squareTask(2);
        Task t5 = new dependentTask();

        futures.add(t1);
        futures.add(t2);
        futures.add(t3);
        futures.add(t4);
        futures.add(t5);

        Task[] t2Deps = {t1};
        Task[] t3Deps = {t2};
        Task[] t4Deps = {t1,t2,t3};
        Task[] t5Deps = {t1,t2,t3,t4};

        bag.submitTask(t2,t2Deps);
        bag.submitTask(t5,t5Deps);
        bag.submitTask(t3,t3Deps);
        bag.submitTask(t4,t4Deps);
        bag.submitTask(t1);



        for (Task t : futures) {
            try {
                System.out.println("The result is: " + t.getResult());
            } catch (Exception e) {
                System.out.print(e);
            }
        }
    }
}

class squareTask extends Task {
    public int numberToSquare;

    public squareTask(int numberToSquare){
        this.numberToSquare = numberToSquare;
    }

    public Integer call() throws InterruptedException{
        Thread.sleep(1500);
        return numberToSquare*numberToSquare;
    }
}

class addToPreviousTask extends Task {
    public int numberToAdd;

    public addToPreviousTask(int numberToAdd){
        this.numberToAdd = numberToAdd;
    }

    public Integer call() throws InterruptedException{
        int antecedents = (int) getParameters();
        return numberToAdd+antecedents;
    }
}

class dependentTask extends Task{

    public String call() throws InterruptedException{
        return "all other tasks are done";
    }

}




