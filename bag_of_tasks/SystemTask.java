package bag_of_tasks;

import java.util.UUID;

public abstract class SystemTask<A,T> extends Task<T> {

    UUID predecessor_1_ID;
    UUID predecessor_2_ID;

    A parameter1;
    A parameter2;

    public void setParameter(UUID predecessorID, A parameter){
        if(predecessorID == predecessor_1_ID){
            this.parameter1 = parameter;
        }else if(predecessorID == predecessor_2_ID){
            this.parameter2 = parameter;
        }
    }

    public void setPredecessor_1_ID(UUID predecessor_1_ID) {
        this.predecessor_1_ID = predecessor_1_ID;
    }

    public void setPredecessor_2_ID(UUID predecessor_2_ID) {
        this.predecessor_2_ID = predecessor_2_ID;
    }
}
