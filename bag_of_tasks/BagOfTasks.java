package bag_of_tasks;

public class BagOfTasks {
    private Bag bag;

    public BagOfTasks(int numberOfWorkers){
        bag = new Bag(numberOfWorkers);
    }

    public void submitTask(Task task, Task[] deps){
        System.out.println("Submitting");
        bag.addTask(task, deps);
        System.out.println("Submitted");
    }

    public void submitTask(Task task){
        bag.addTask(task);
    }
}