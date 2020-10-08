package sockmatchingrobot;

// Libraries required by the program
import java.util.Random;

/* The robotic arm picks a sock from the heap of socks and passes it the the
matching machine */
public class RoboticArm extends Thread {
    private SockMatchingRobot sockMatchingRobot;
    private MatchingMachine matchingMachine;
    // ID of the thread
    private int ID;
    // Random number generator
    private Random rand = new Random();

    // Constructor for robotic arm
    RoboticArm(SockMatchingRobot sockMatchingRobot, MatchingMachine matchingMachine, int ID){
        this.sockMatchingRobot = sockMatchingRobot;
        this.matchingMachine = matchingMachine;
        this.ID = ID;
    }

    // Overrides the method run of class Thread
    public void run() {
        while(true){
            int sock = Constants.NULL;
            try{
                sock = pickSock();
            }
            catch(InterruptedException e){
                System.out.println(e);
            }

            // If no sock found, stop the robotic arm (thread)
            if(sock == Constants.NULL){
                System.out.println("Thread " + this.ID + " stopped.");
                break;
            }
            // If sock found, pass it to the matching machine
            System.out.println("Sock of color " + sock + " picked by thread " + this.ID);
            matchingMachine.formPair(sock);
        }
    }

    // Picks a sock from the heap of socks
    int pickSock() throws InterruptedException {
        int id = -1;
        int sock = Constants.NULL;

        // Handle heap of socks synchronously
        synchronized(sockMatchingRobot.socks){
            // If socks is not empty, choose a random index to pick the sock
            if(!sockMatchingRobot.socks.isEmpty()){
                id = rand.nextInt(sockMatchingRobot.socks.size());
            }

            // If successfully picked
            if(id != -1){
                // Acquire semaphore on sock at index id
                sockMatchingRobot.semaphores.get(id).acquire();
                // Remove that sock from the heap
                sock = sockMatchingRobot.socks.get(id);
                sockMatchingRobot.socks.remove(id);
                // Release semaphore
                sockMatchingRobot.semaphores.get(id).release();
            }
        }

        // Return the picked sock
        return sock;
    }
}
