package distributedsystem;

// Libraries required by the program
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Data Modification in Distributed System
public class DistributedSystem{
    // Map of the complete record containing all student entries
    public Map<String, ArrayList<String>> record;
    // List of all updation requests
    private ArrayList<ArrayList<String>> input;
    OutputWriter outputWriter;
    // Scanner to read input from terminal
    private static Scanner scanner = new Scanner(System.in);

    // Constructor for distributed system
    DistributedSystem(){
        record = new HashMap<>();
        input = new ArrayList<>();
        outputWriter = new OutputWriter(this);
    }

    // Reads the initial record from the input file
    private void readInput(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner file_scanner = new Scanner(file);
        System.out.println("Reading existing record...");
        while(file_scanner.hasNext()){
            String line = file_scanner.nextLine();
            System.out.println(line);
            String[] data = line.split(",");
            ArrayList<String> entry = new ArrayList<>();
            entry.add(data[1]);
            entry.add(data[2]);
            entry.add(data[3]);
            entry.add(data[4]);
            // Add the entry to the record
            record.put(data[0], entry);
        }
    }

    // Reads the teacher name from the terminal
    private String getTeacher(){
        System.out.println("Enter teacher name:");
        while(true){
            String teacher = scanner.next();
            if(teacher.equals("CC") || teacher.equals("TA1") || teacher.equals("TA2")){
                return teacher;
            }
            else{
                System.out.println("Invalid name. Try again.");
            }
        }
    }

    // Reads the roll number from the terminal
    private String getRollNumber() {
        System.out.println("Enter student roll number:");
        return scanner.next();
    }

    // Reads the marks update from the terminal
    private String getUpdate() {
        // Choose between increase or decrease
        System.out.println("Choose an option:");
        System.out.println("1. Increase marks");
        System.out.println("2. Decrease marks");

        while(true){
            int choice = scanner.nextInt();
            // If increase, return +update
            if(choice == 1){
                System.out.println("Increase marks by:");
                int update = scanner.nextInt();
                return String.valueOf(update);
            }
            // If decrease, return -update
            else if(choice == 2){
                System.out.println("Decrease marks by:");
                int update = scanner.nextInt();
                return String.valueOf(-update);
            }
            else{
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Reads updation request and appends it to the list
    private void appendRequest(){
        String teacher = getTeacher();
        String rollNumber = getRollNumber();
        String update = getUpdate();

        ArrayList<String> entry = new ArrayList<>();
        entry.add(teacher);
        entry.add(rollNumber);
        entry.add(update);
        input.add(entry);
        System.out.print("Added updation request: ");
        System.out.println(entry);
    }

    // Initializes the system to execute unfinished updation requests
    private void update() throws InterruptedException, IOException {
        /* Initializes three Teacher instances (threads) for each teacher. The
        CC instance is created with maximum priority and TA1 and TA2 instances
        are created with normal priorities. */
        Teacher cc = new Teacher(this, Constants.CC, Thread.MAX_PRIORITY);
        Teacher ta1 = new Teacher(this, Constants.TA1, Thread.NORM_PRIORITY);
        Teacher ta2 = new Teacher(this, Constants.TA2, Thread.NORM_PRIORITY);

        // Assigns the updation request to the specified teacher
        for(ArrayList<String> entry : input){
            String teacher = entry.get(0);
            String rollNumber = entry.get(1);
            String update = entry.get(2);
            switch(teacher){
                case Constants.TA1:
                    ta1.appendRequest(rollNumber, update);
                    break;
                case Constants.TA2:
                    ta2.appendRequest(rollNumber, update);
                    break;
                case Constants.CC:
                    cc.appendRequest(rollNumber, update);
                    break;
            }
        }
        
        // Starts the Teacher threads
        cc.start();
        ta1.start();
        ta2.start();

        // Wait for all teachers to finish the execution
        cc.join();
        ta1.join();
        ta2.join();

        // Clears the updation request list
        input.clear();

        // Writes the final output to the files
        outputWriter.writeOutput();
        System.out.println("Marks successfully updated!");
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, IOException {
        /* Initializes the distributed system and reads input from the existing
        record */
        DistributedSystem distributedSystem = new DistributedSystem();
        distributedSystem.readInput(Constants.STUD_INFO);

        while(true){
            // Choose an option to execute
            System.out.println("Choose an option:");
            System.out.println("1. Update marks");
            System.out.println("2. Execute");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            if(choice == 1){
                distributedSystem.appendRequest();
            }
            else if(choice == 2){
                distributedSystem.update();
            }
            else if(choice == 3){
                break;
            }
            else{
                System.out.println("Invalid option. Try again.");
            }
        }
    }
}