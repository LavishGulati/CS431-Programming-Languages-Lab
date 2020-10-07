package distributedsystem;

import java.util.ArrayList;

public class Teacher extends Thread {
    private DistributedSystem distributedSystem;
    private ArrayList<ArrayList<String>> input;
    public boolean isSynchronize;

    Teacher(DistributedSystem distributedSystem, String name, int priority){
        this.distributedSystem = distributedSystem;
        input = new ArrayList<>();
        setName(name);
        setPriority(priority);
        isSynchronize = false;
    }

    private void updateEntry(String rollNumber, ArrayList<String> entry, int update, String teacher){
        if(entry.get(3).equals(Constants.CC) && !teacher.equals(Constants.CC)){
            System.out.println("Entry with roll number " + rollNumber + " cannot be updated by " + teacher);
            return;
        }
        int marks = Integer.parseInt(entry.get(2).trim());
        marks = marks + update;
        entry.set(2, String.valueOf(marks));
        entry.set(3, teacher);
    }

    public void run(){
        while(input.size() > 0){
            String rollNumber = input.get(0).get(0);
            int update = Integer.parseInt(input.get(0).get(1));
            String teacher = getName();
            if(distributedSystem.record.get(rollNumber) != null){
                if(isSynchronize){
                    synchronized(distributedSystem.record.get(rollNumber)){
                        updateEntry(rollNumber, distributedSystem.record.get(rollNumber), update, teacher);
                    }
                }
                else{
                    updateEntry(rollNumber, distributedSystem.record.get(rollNumber), update, teacher);
                }
            }
            input.remove(0);
        }
    }

    void appendInput(String rollNumber, String update){
        ArrayList<String> entry = new ArrayList<>();
        entry.add(rollNumber);
        entry.add(update);
        input.add(entry);
    }
}
