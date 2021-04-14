package bag_of_tasks;

public class BagOfTasks {
    private Bag bag;

    public BagOfTasks(int numberOfWorkers, int graphSize){
        bag = new Bag(numberOfWorkers, graphSize);
    }

    public void submitTask(Task task, Task[] deps){
        bag.addTask(task, deps);
    }
}