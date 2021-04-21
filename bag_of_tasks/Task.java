package bag_of_tasks;

import java.util.concurrent.Callable;

public abstract class Task<A,T> implements Callable<T>, Runnable {
    Boolean isDone = false;
    String errorMsg = null;

    protected Task[] dependencies;
    //protected A parameters;
    protected T result;

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

    public void setParameters(A parameters) {
        this.parameters = parameters;
    }

     */

    public Task[] getDependencies() {
        return dependencies;
    }

    public void setDependencies(Task[] dependencies) {
        this.dependencies = dependencies;
    }

}