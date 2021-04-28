package bag_of_tasks;

public class BagOfTasks {
    private Bag bag;

    public BagOfTasks(int numberOfWorkers){
        bag = new Bag(numberOfWorkers);
    }

    public void submitTask(Task task){
        bag.addTask(task);
    }

    public Task continueWith(Task predecessor, ContinueInput inputFunction) throws Exception{
        return bag.continueWith(predecessor, inputFunction);
    }

    public Task combineWith(Task predecessor1,Task predecessor2, CombineInput inputFunction)throws Exception{
        return bag.combineWith(predecessor1,predecessor2, inputFunction);
    }
}