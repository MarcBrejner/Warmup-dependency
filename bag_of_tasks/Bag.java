package bag_of_tasks;

import java.util.*;
import java.util.concurrent.*;

class Bag {

    protected Graph dependencies;
    private BlockingQueue<Task> taskBag;
    private List<Worker> workers;

    protected Bag(int numberOfWorkers, int graphSize){
        this.taskBag = new LinkedBlockingQueue<Task>(){};
        this.workers = new ArrayList<Worker>(){};
        this.dependencies = new Graph(graphSize);
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

    protected Task getTask() {
        Task task = null;
        try {
            while(true) {
                task = taskBag.take();
                if (dependencies.hasDependencies(task)) {
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
            bag.dependencies.removeTask(task);

        }
    }
}

