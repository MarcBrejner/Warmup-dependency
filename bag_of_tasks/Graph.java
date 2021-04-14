package bag_of_tasks;

import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    int vertices;
    int count;
    LinkedList<LinkedList<Task>> dependants;

    public Graph() {
        dependants = new LinkedList<>();
    }

    public synchronized void addDependency(Task dependant, Task[] dependencies) {
        int index = dependants.indexOf(dependant);                      // get the index of the task, if it doesn't exist return -1
        if (index != -1)  {                                             // The dependant already exists
            dependants.get(index).addAll(Arrays.asList(dependencies));  // Add all dependencies to the existing dependant
        } else {
            dependants.addLast(new LinkedList<>());                     // Create new entry for the dependant
            dependants.getLast().add(dependant);
            dependants.getLast().addAll(Arrays.asList(dependencies));
        }
    }

    public synchronized void removeTask(Task task) {
        // Outer linked list
        for (int outer = 0; outer < dependants.size(); outer++) {
            // Inner linked list
            for (int inner = 0; inner < dependants.get(outer).size(); inner++) {
                if (dependants.get(outer).get(inner) == task && inner == 0){
                    dependants.remove(outer);
                } else if (dependants.get(outer).get(inner) == task) {
                    dependants.get(outer).remove(inner);
                    if(dependants.get(outer).size() >= 1){
                        dependants.remove(outer);
                        break;
                    }
                }
            }
        }
    }

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
}
