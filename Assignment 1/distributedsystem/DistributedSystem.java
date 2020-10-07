package distributedsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DistributedSystem{
    public Map<String, ArrayList<String>> record;
    private ArrayList<ArrayList<String>> input;
    OutputWriter outputWriter;
    private static Scanner scanner = new Scanner(System.in);

    DistributedSystem(){
        record = new HashMap<>();
        input = new ArrayList<>();
        outputWriter = new OutputWriter(this);
    }

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
            record.put(data[0], entry);
        }
    }

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

    private String getRollNumber() {
        System.out.println("Enter student roll number:");
        return scanner.next();
    }

    private String getUpdate() {
        System.out.println("Choose an option:");
        System.out.println("1. Increase marks");
        System.out.println("2. Decrease marks");

        while(true){
            int choice = scanner.nextInt();
            if(choice == 1){
                System.out.println("Increase marks by:");
                int update = scanner.nextInt();
                return String.valueOf(update);
            }
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

    private void appendInput(){
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

    private void update() throws InterruptedException, IOException {
        System.out.println("Choose an option:");
        System.out.println("1. Record level updation");
        System.out.println("2. File level updation");

        Teacher cc = new Teacher(this, Constants.CC, Thread.MAX_PRIORITY);
        Teacher ta1 = new Teacher(this, Constants.TA1, Thread.NORM_PRIORITY);
        Teacher ta2 = new Teacher(this, Constants.TA2, Thread.NORM_PRIORITY);

        while(true){
            int choice = scanner.nextInt();
            if(choice == 1){
                cc.isSynchronize = true;
                ta1.isSynchronize = true;
                ta2.isSynchronize = true;
                break;
            }
            else if(choice == 2){
                cc.isSynchronize = false;
                ta1.isSynchronize = false;
                ta2.isSynchronize = false;
                break;
            }
            else{
                System.out.println("Invalid option. Try again.");
            }
        }

        for(ArrayList<String> entry : input){
            String teacher = entry.get(0);
            String rollNumber = entry.get(1);
            String update = entry.get(2);
            switch(teacher){
                case Constants.TA1:
                    ta1.appendInput(rollNumber, update);
                    break;
                case Constants.TA2:
                    ta2.appendInput(rollNumber, update);
                    break;
                case Constants.CC:
                    cc.appendInput(rollNumber, update);
                    break;
            }
        }

        input.clear();
        cc.start();
        ta1.start();
        ta2.start();

        cc.join();
        ta1.join();
        ta2.join();

        outputWriter.writeOutput();
        System.out.println("Marks successfully updated!");
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, IOException {
        if(args.length < 1){
            System.out.println("Path of input file not supplied.");
            return;
        }

        DistributedSystem distributedSystem = new DistributedSystem();
        distributedSystem.readInput(args[0]);

        while(true){
            System.out.println("Choose an option:");
            System.out.println("1. Update marks");
            System.out.println("2. Execute");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            if(choice == 1){
                distributedSystem.appendInput();
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