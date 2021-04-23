package bag_of_tasks;
/*
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class DependencyGraph {
    Map<Task, Set<Task>> dependencyMap;
    Map<Task, Set<Task>> inverseMap;
    BlockingQueue<Task> taskBag;

    public DependencyGraph(BlockingQueue<Task> taskBag) {
        dependencyMap = new HashMap<Task,Set<Task>>();
        inverseMap = new HashMap<Task,Set<Task>>();
        this.taskBag = taskBag;
    }

    public synchronized void addDependency(Task dependant) {
        Task[] dependencies = dependant.getDependencies();

        if(dependencyMap.containsKey(dependant)){
            System.out.println("Duplicate task dependency entry");
            return;
        }

        Set dependencySet = new HashSet<Task>();
        dependencySet.addAll(Arrays.asList(dependencies));
        dependencyMap.put(dependant,dependencySet);

        for(Task t : dependencies){
            if(inverseMap.containsKey(t)){
                inverseMap.get(t).add(dependant);
            }else{
                Set inverseSet = new HashSet<Task>();
                inverseSet.add(dependant);
                inverseMap.put(t,inverseSet);
            }
        }
    }

    public synchronized void removeTask(Task task) throws InterruptedException{

        if(!inverseMap.containsKey(task)){
            return; //If no key task in InverseMap, nothing depends on task, therefore return
        }

        for(Task t : inverseMap.get(task)){     //For each Task t in the set of tasks that depends on task.
            dependencyMap.get(t).remove(task);  //Remove the task from the set of dependencies mapped to task t.

            if(dependencyMap.get(t).isEmpty()){ //if any of the tasks that depended on task no longer has any dependencies
                taskBag.put(t);                 //Add it to the taskBag for execution
            }
        }
        inverseMap.remove(task); //Remove task from the inverseMap since nothing can depend on a task that has already been run.
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
//}
