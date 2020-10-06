package sockmatchingrobot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class SockMatchingRobot{
    private int numRobots;
    private ArrayList<Integer> socks;
    private ShelfManagerRobot shelfManagerRobot;
    private MatchingMachine matchingMachine;
    private ArrayList<RoboticArm> roboticArms;
    private Random rand = new Random();

    private SockMatchingRobot(int numRobots, ArrayList<Integer> socks){
        this.numRobots = numRobots;
        this.socks = socks;

        shelfManagerRobot = new ShelfManagerRobot();
        matchingMachine = new MatchingMachine(shelfManagerRobot);

        roboticArms = new ArrayList<>();
        for(int i = 0; i < this.numRobots; i++){
            RoboticArm roboticArm = new RoboticArm(this, matchingMachine, i);
            roboticArms.add(roboticArm);
        }
    }

    int pickSock(){
        int id = -1;
        int sock = Constants.NULL_SOCK;

        synchronized(socks){
            if(!socks.isEmpty()){
                id = rand.nextInt(socks.size());
            }

            if(id != -1){
                sock = socks.get(id);
                socks.remove(id);
            }
        }

        return sock;
    }

    void start() throws InterruptedException {
        for(RoboticArm roboticArm : roboticArms){
            roboticArm.start();
        }

        for(RoboticArm roboticArm : roboticArms){
            roboticArm.join();
        }

        shelfManagerRobot.printOutput();
    }

    public static void main(String []args) throws FileNotFoundException, InterruptedException {
        if(args.length < 1){
            System.out.println("Path of input file not supplied.");
            return;
        }

        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);

        int numRobots = scanner.nextInt();

        ArrayList<Integer> socks = new ArrayList<>();
        while(scanner.hasNextInt()){
            socks.add(scanner.nextInt());
        }

        SockMatchingRobot sockMatchingRobot = new SockMatchingRobot(numRobots, socks);
        sockMatchingRobot.start();
    }
}
