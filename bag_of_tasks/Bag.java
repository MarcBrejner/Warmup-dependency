package bag_of_tasks;

import java.util.*;
import java.util.concurrent.*;

class Bag {

    protected DependencyGraph continuations;
    private BlockingQueue<Task> taskBag;
    private List<Worker> workers;

    protected Bag(int numberOfWorkers){
        this.taskBag = new LinkedBlockingQueue<Task>(){};
        this.workers = new ArrayList<Worker>(){};
        this.continuations = new DependencyGraph(taskBag);
        for (int i = 0; i < numberOfWorkers; ++i) {
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

    protected void addTask(Task task){
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

    protected synchronized Task continueWith(Task predecessor, ContinueInput inputFunction) throws Exception{
        SystemTask sysTask = new SystemTask() {
            @Override
            public Object call() throws Exception {
                return inputFunction.exec(parameter1);
            }
        };
        sysTask.setPredecessor_1_ID(predecessor.getID());

        submitIfReady(sysTask, predecessor);

        return sysTask;
    }

    protected synchronized Task combineWith(Task predecessor1, Task predecessor2, CombineInput inputFunction) throws Exception{
        SystemTask sysTask = new SystemTask() {
            @Override
            public Object call() throws Exception {
                return inputFunction.combine(parameter1, parameter2);
            }
        };
        sysTask.setPredecessor_1_ID(predecessor1.getID());
        sysTask.setPredecessor_2_ID(predecessor2.getID());

        submitIfReady(sysTask, predecessor1, predecessor2);

        return sysTask;
    }

    public void submitIfReady(SystemTask sysTask, Task predecessor)throws Exception{
        if(predecessor.isDone){
            sysTask.setParameter(predecessor.getID(),predecessor.getResult());
            addTask(sysTask);
        }else{
            continuations.addContinuation(predecessor,sysTask);
        }
    }

    public void submitIfReady(SystemTask sysTask, Task predecessor1, Task predecessor2)throws Exception{
        if(predecessor1.isDone && predecessor2.isDone){
            sysTask.setParameter(predecessor1.getID(),predecessor1.getResult());
            sysTask.setParameter(predecessor2.getID(),predecessor2.getResult());
            addTask(sysTask);
        }else if(predecessor1.isDone){
            sysTask.setParameter(predecessor1.getID(),predecessor1.getResult());
            continuations.addContinuation(predecessor2,sysTask);
        }else if(predecessor2.isDone){
            sysTask.setParameter(predecessor2.getID(),predecessor2.getResult());
            continuations.addContinuation(predecessor1,sysTask);
        }else{
            continuations.addContinuation(predecessor1,sysTask);
            continuations.addContinuation(predecessor2,sysTask);
        }
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
                bag.continuations.releaseContinuations(task);
            }catch(Exception e){

            }


        }
    }
}

