import bag_of_tasks.*;
import java.util.*;

class userProgram {
    public static void main(String[] args) throws Exception {
        List<Task> futures = new ArrayList<Task>(){};

        BagOfTasks bag = new BagOfTasks(1);
        /*
        Task t5 = new squareTask(2);
        bag.submitTask(t5);
        */

        Task t1 = new squareTask(2);
        Task t2 = bag.continueWith(t1,(result) -> 3+(int)result);
        Task t8 = bag.combineWith(t1,t2,(res1,res2)->(int)res1*(int)res2);
        /*
        Task t3 = bag.continueWith(t1,(result) -> 3+(int)result);
        Task t4 = bag.continueWith(bag.continueWith(t1,(result) -> 4+(int)result),(result) ->4*(int)result);

        Task t6 = new stringTask("The string of this task: ");
        Task t7 = bag.continueWith(t6, result -> (String)result+5);





        futures.add(t3);
        futures.add(t4);
        futures.add(t5);
        futures.add(t7);

         */
        futures.add(t1);
        futures.add(t2);
        futures.add(t8);

        //bag.submitTask(t6);
        bag.submitTask(t1);

        for (Task t : futures) {
            try {
                System.out.println("The result is: " + t.getResult());
                System.out.println("The ID of this task is: "+t.getID().toString());
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


class stringTask extends Task{

    public String str;

    public stringTask(String str){
        this.str = str;
    }
    public String call(){
        return str;
    }

}




