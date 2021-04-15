package bag_of_tasks;

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

    public synchronized void addDependency(Task dependant, Task[] dependencies) {
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
            return;
        }

        for(Task t : inverseMap.get(task)){
            dependencyMap.get(t).remove(task);
            if(dependencyMap.get(t).isEmpty()){
                taskBag.put(t);
            }
        }
        inverseMap.remove(task);

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
}
