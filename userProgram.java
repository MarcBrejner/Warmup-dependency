import bag_of_tasks.*;
import java.util.*;

class userProgram {
    public static void main(String[] args) {
        int primesToFind;
        List<Task> futures = new ArrayList<Task>(){};

        BagOfTasks bag = new BagOfTasks(3);

        /*

        Task t2 = new addToPreviousTask(2);
        Task t3 = new addToPreviousTask(10);
        Task t4 = new squareTask(2);
        Task t5 = new addToPreviousTask(0);
        Task t6 = new dependentTask();
        Task t7 = new divideTwoAndAddOutString();

        futures.add(t1);
        futures.add(t2);
        futures.add(t3);
        futures.add(t4);
        futures.add(t5);
        futures.add(t6);
        futures.add(t7);

        Task[] t2Deps = {t1};
        Task[] t3Deps = {t2};
        Task[] t4Deps = {t1,t2,t3};
        Task[] t5Deps = {t1,t2,t3,t4};
        Task[] t6Deps = {t1,t2,t3,t4,t5};
        Task[] t7Deps = {t5,t1,t6,t6};

        bag.submitTask(t7,t7Deps);
        bag.submitTask(t2,t2Deps);
        bag.submitTask(t5,t5Deps);
        bag.submitTask(t6,t6Deps);
        bag.submitTask(t3,t3Deps);
        bag.submitTask(t4,t4Deps);
        bag.submitTask(t1);

         */
        Task t1 = new squareTask(3);
        t1.continueWith((result) -> 2+result);

        futures.add(t1);
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
        return numberToSquare*numberToSquare;
    }
}

class addToPreviousTask extends Task {
    public int numberToAdd;


    public addToPreviousTask(int numberToAdd){

        this.numberToAdd = numberToAdd;
    }

    public Integer call() throws InterruptedException{
        int antecedents = 0;
        for(Task ant : dependencies){
            try {
                antecedents += (int) ant.getResult();
            }catch(Exception e){
                System.out.println("RIP");
            }
        }
        return numberToAdd+antecedents;
    }
}

class dependentTask extends Task{

    public String call() throws InterruptedException{

        return "all other tasks are done";
    }

}

class divideTwoAndAddOutString extends Task {

    public String call() throws InterruptedException{
        String r = "";
        try {
            r =  (int) dependencies[0].getResult()/ (int) dependencies[1].getResult() +" "+ (String) dependencies[2].getResult();
        } catch (Exception e){
            System.out.println("dividend failed");
        }
        return r;
    }
}



