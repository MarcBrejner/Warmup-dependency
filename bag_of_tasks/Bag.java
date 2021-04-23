package bag_of_tasks;

import java.util.*;
import java.util.concurrent.*;

class Bag {

    //protected DependencyGraph dependencies;
    private static ConcurrentHashMap<Task,Task> continuations;
    private static BlockingQueue<Task> taskBag;
    private List<Worker> workers;

    protected Bag(int numberOfWorkers){
        this.taskBag = new LinkedBlockingQueue<Task>(){};
        this.workers = new ArrayList<Worker>(){};
        this.continuations = new ConcurrentHashMap<Task,Task>(){};
        //this.dependencies = new DependencyGraph(taskBag);
        for (int i = 0; i < numberOfWorkers; ++i) {
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

    protected static void addContinuation(Task task, Task sysTask){
        continuations.put(task,sysTask);
    }

    protected void submitIfPresent(Task task) throws Exception{
        if(continuations.containsKey(task)){
            continuations.get(task).setParameters(task.getResult());
            addTask(continuations.get(task));
        }
    }

    protected static void addTask(Task task){
        try{
            taskBag.put(task);
        } catch(InterruptedException e){}
    }

    protected Task getTask() {
        Task task = null;
        try{
            task = taskBag.take();
        }catch(InterruptedException e){

        }
        return task;
    }
}

class Worker extends Thread {
    Bag bag;

    protected Worker(Bag bag) {
        this.bag = bag;
    }

    public void run() {
        while (true) {
            Task task = bag.getTask();
            task.run();
            try{
                bag.submitIfPresent(task);
            }catch(Exception e){

            }


            /*
            try {
                bag.dependencies.removeTask(task);
            }catch(InterruptedException e){

            }
            */

        }
    }
}

