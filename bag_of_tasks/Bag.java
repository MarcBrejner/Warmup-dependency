package bag_of_tasks;

import java.util.*;
import java.util.concurrent.*;

class Bag {

    protected Graph dependencies;
    private BlockingQueue<Task> taskBag;
    private List<Worker> workers;

    protected Bag(int numberOfWorkers){
        this.taskBag = new LinkedBlockingQueue<Task>(){};
        this.workers = new ArrayList<Worker>(){};
        this.dependencies = new Graph();
        for (int i = 0; i < numberOfWorkers; ++i) {
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

    protected void addTask(Task task, Task[] deps) {
        try {
            taskBag.put(task);
            dependencies.addDependency(task, deps);
        } catch (InterruptedException e) {}
    }

    protected void addTask(Task task){
        try{
            taskBag.put(task);
        } catch(InterruptedException e){}
    }

    protected Task getTask() {
        Task task = null;
        try {
            while(true) {
                task = taskBag.take();
                if (dependencies.hasDependencies(task)) {
                    Thread.sleep(100);
                    System.out.println("didn't work");
                    taskBag.put(task);
                }else{
                    break;
                }
            }
        } catch (InterruptedException e) {}

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

            System.out.println("Started working on a task");

            task.run();
            System.out.println("Finished working on a task");
            bag.dependencies.removeTask(task);

        }
    }
}

