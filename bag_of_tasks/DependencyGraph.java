
package bag_of_tasks;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class DependencyGraph {
    Map<Task, Set<SystemTask>> dependencyMap;
    //Map<Task, Set<Task>> inverseMap;
    BlockingQueue<Task> taskBag;

    public DependencyGraph(BlockingQueue<Task> taskBag) {
        dependencyMap = new HashMap<Task, Set<SystemTask>>();
        //inverseMap = new HashMap<Task, Set<Task>>();
        this.taskBag = taskBag;
    }

    public synchronized void addContinuation(Task dependant, SystemTask continueTask) {
        if (dependencyMap.containsKey(dependant)) {
            dependencyMap.get(dependant).add(continueTask);
        } else {
            Set continueSet = new HashSet<SystemTask>();
            continueSet.add(continueTask);
            dependencyMap.put(dependant, continueSet);
        }
        /*
        for (Task t : dependencies) {
            if (inverseMap.containsKey(t)) {
                inverseMap.get(t).add(dependant);
            } else {
                Set inverseSet = new HashSet<Task>();
                inverseSet.add(dependant);
                inverseMap.put(t, inverseSet);
            }
        }

         */
    }

    public synchronized void releaseContinuations(Task task) throws InterruptedException, Exception {
        if (!dependencyMap.containsKey(task)) {
            return; //If no key task in InverseMap, nothing depends on task, therefore return
        }

        Set<SystemTask> continueSet = dependencyMap.remove(task);
        for(SystemTask continueTask : continueSet){
            continueTask.setParameter(task.getID(), task.getResult());
            taskBag.add(continueTask);
        }

        /*
        for (Task t : inverseMap.get(task)) {     //For each Task t in the set of tasks that depends on task.
            dependencyMap.get(t).remove(task);  //Remove the task from the set of dependencies mapped to task t.

            if (dependencyMap.get(t).isEmpty()) { //if any of the tasks that depended on task no longer has any dependencies
                taskBag.put(t);                 //Add it to the taskBag for execution
            }
        }
        inverseMap.remove(task); //Remove task from the inverseMap since nothing can depend on a task that has already been run.

         */



    }
}

    /*
    public synchronized boolean hasDependencies(Task t) {
        boolean result = false;
        for (LinkedList<Task> taskList : dependants) {
            if (taskList.getFirst() == t) {
                result = true;
                break;
            }
        }
        return result;
    }

     */



