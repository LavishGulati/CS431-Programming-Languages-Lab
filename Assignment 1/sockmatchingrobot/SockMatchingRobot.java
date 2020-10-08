package sockmatchingrobot;

// Libraries required by the program
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

// Sock matching robot
public class SockMatchingRobot{
    // To indicate the number of Robotic Arms
    private int numRoboticArms;
    // Heap of socks
    public ArrayList<Integer> socks;
    // List of semaphores for each sock
    public ArrayList<Semaphore> semaphores;
    private ShelfManagerRobot shelfManagerRobot;
    private MatchingMachine matchingMachine;
    // List of robotic arms
    private ArrayList<RoboticArm> roboticArms;

    // Constructor for sock matching robot
    private SockMatchingRobot(int numRoboticArms, ArrayList<Integer> socks){
        this.numRoboticArms = numRoboticArms;
        this.socks = socks;

        shelfManagerRobot = new ShelfManagerRobot();
        matchingMachine = new MatchingMachine(shelfManagerRobot);

        /* Initialize the required number of robotic arms and add them to the
        list */
        roboticArms = new ArrayList<>();
        for(int i = 0; i < this.numRoboticArms; i++){
            RoboticArm roboticArm = new RoboticArm(this, matchingMachine, i);
            roboticArms.add(roboticArm);
        }

        // Initialize all semaphores and add them to the list
        semaphores = new ArrayList<>();
        for(int i = 0; i < socks.size(); i++){
            semaphores.add(new Semaphore(1));
        }
    }

    // Starts the sock matching robot
    void start() throws InterruptedException {
        // Starts all the robotic arms
        for(RoboticArm roboticArm : roboticArms){
            roboticArm.start();
        }

        // Wait for all robotic arms to complete their execution
        for(RoboticArm roboticArm : roboticArms){
            roboticArm.join();
        }

        // Print the final output
        shelfManagerRobot.printOutput();
    }

    public static void main(String []args) throws FileNotFoundException, InterruptedException {
        // If input file not supplied, throw error
        if(args.length < 1){
            System.out.println("Path of input file not supplied.");
            return;
        }

        // Scanner to read input from the file
        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);

        // Read number of robotic arms
        int numRoboticArms = scanner.nextInt();

        // Read the heap of socks
        ArrayList<Integer> socks = new ArrayList<>();
        while(scanner.hasNextInt()){
            socks.add(scanner.nextInt());
        }

        // Initialize the sock matching robot and start it
        SockMatchingRobot sockMatchingRobot = new SockMatchingRobot(numRoboticArms, socks);
        sockMatchingRobot.start();
    }
}
