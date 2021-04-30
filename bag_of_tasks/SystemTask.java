package bag_of_tasks;

import java.util.InputMismatchException;
import java.util.UUID;

public abstract class SystemTask<A,T> extends Task<T> {

    UUID predecessor_1_ID;
    UUID predecessor_2_ID;

    A parameter1 = null;
    A parameter2 = null;

    SystemTaskType type;
    boolean isReady = false;

    public void setParameter(UUID predecessorID, A parameter) throws Exception {
        if(predecessorID == predecessor_1_ID){
            this.parameter1 = parameter;
            checkIfReady();
        }else if(predecessorID == predecessor_2_ID){
            this.parameter2 = parameter;
            checkIfReady();
        } else {
           throw new Exception("The given ID didn't match any of the predecessor ID's");
        }
    }

    private void checkIfReady() {
        if (this.getType() == SystemTaskType.CONTINUE && this.parameter1 != null) {
            isReady = true;
        } else if (this.getType() == SystemTaskType.COMBINE && this.parameter1 != null && this.parameter2 != null) {
            isReady = true;
        }
    }

    public void setPredecessor_1_ID(UUID predecessor_1_ID) { this.predecessor_1_ID = predecessor_1_ID; }

    public void setPredecessor_2_ID(UUID predecessor_2_ID) { this.predecessor_2_ID = predecessor_2_ID; }

    protected SystemTaskType getType() { return type; }

    protected void setType(SystemTaskType type) { this.type = type; }

    protected boolean getIsReady() { return isReady; }
}
