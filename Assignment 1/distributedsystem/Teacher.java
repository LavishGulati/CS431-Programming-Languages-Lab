package distributedsystem;

// Libraries required by the program
import java.util.ArrayList;

/* Teacher updates the marks of the students and prints the final output in two
files: sorted_roll.txt and sorted_name.txt */
public class Teacher extends Thread {
    private DistributedSystem distributedSystem;
    // List of updation requests assigned to this teacher
    private ArrayList<ArrayList<String>> input;

    // Constructor for teacher
    Teacher(DistributedSystem distributedSystem, String name, int priority){
        this.distributedSystem = distributedSystem;
        input = new ArrayList<>();
        // Sets the thread name and priority
        setName(name);
        setPriority(priority);
    }

    // Updates the student entry with the new marks
    private void updateEntry(String rollNumber, ArrayList<String> entry, int update, String teacher){
        /* If the entry is already modified by CC, do not allow TA1 and TA2 to
        modify it */
        if(entry.get(3).equals(Constants.CC) && !teacher.equals(Constants.CC)){
            System.out.println("Entry with roll number " + rollNumber + " cannot be updated by " + teacher);
            return;
        }

        // Update the marks of the entry
        int marks = Integer.parseInt(entry.get(2).trim());
        marks = marks + update;
        entry.set(2, String.valueOf(marks));
        // Set the new teacher for this entry
        entry.set(3, teacher);
    }

    // Overrides run method of Thread class
    public void run(){
        // Do the work till no updation request left
        while(input.size() > 0){
            String rollNumber = input.get(0).get(0);
            int update = Integer.parseInt(input.get(0).get(1));
            String teacher = getName();
            // If entry exists in the record
            if(distributedSystem.record.get(rollNumber) != null){
                // Handle synchronization on the student entry
                synchronized(distributedSystem.record.get(rollNumber)){
                    // Update the entry after the semaphore is acquired
                    updateEntry(rollNumber, distributedSystem.record.get(rollNumber), update, teacher);
                }
            }
            // If entry does not exist in the record, throw error
            else{
                System.out.println("Entry with roll number " + rollNumber + " not found");
            }
            // Remove the updation request after completion
            input.remove(0);
        }
    }

    // Appends updation request to the list
    void appendRequest(String rollNumber, String update){
        ArrayList<String> entry = new ArrayList<>();
        entry.add(rollNumber);
        entry.add(update);
        input.add(entry);
    }
}
