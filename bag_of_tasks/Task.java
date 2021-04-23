package bag_of_tasks;

import java.util.concurrent.Callable;

public abstract class Task<A,T> implements Callable<T>, Runnable {
    Boolean isDone = false;
    String errorMsg = null;

    protected Task[] dependencies;
    protected A parameters;
    protected T result;
    protected boolean continueWithFlag = false;
    protected continueWithInput cwFunction;

    public void run(){
        T r;
        try{
          r = call();
          setResult(r);
        } catch(Exception e){
            setFailure(e);
        }
    }

    public synchronized T getResult() throws Exception {

        while(!isDone){
            try{
                wait();
            }catch(InterruptedException e){}
        }

        if (errorMsg == null) {
            return result;
        } else {
            throw new Exception(errorMsg);
        }
    }

    public synchronized void setResult(T result) {
        if(isDone){
            return;
        }
        this.result = result;
        isDone = true;
        notifyAll();
    }

    public synchronized void setFailure(Exception e){
        if(isDone){
            return;
        }
        this.errorMsg = "Task failed with: "+e;
        isDone = true;
        notifyAll();
    }

    /*public A getParameters() {
        return parameters;
    }



     */

    public void setParameters(A parameters) {
        this.parameters = parameters;
    }

    public synchronized Task continueWith(continueWithInput inputFunction) throws Exception{
        Task sysTask = new Task() {
            @Override
            public Object call() throws Exception {
                return inputFunction.exec(parameters);
            }
        };

        if(isDone){
            sysTask.setParameters(getResult());
            Bag.addTask(sysTask);
        }else{
            Bag.addContinuation(this, sysTask);
        }

        return sysTask;

        //continueWithFlag = true;
        //cwFunction = inputFunction;
    }

    public interface continueWithInput<T> {
        T exec(T result);
    }

    /*
    public Task[] getDependencies() {
        return dependencies;
    }

    public void setDependencies(Task[] dependencies) {
        this.dependencies = dependencies;
    }

     */

}