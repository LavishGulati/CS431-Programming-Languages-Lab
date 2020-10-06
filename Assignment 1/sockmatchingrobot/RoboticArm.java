package sockmatchingrobot;

public class RoboticArm extends Thread {
    private SockMatchingRobot sockMatchingRobot;
    private MatchingMachine matchingMachine;
    private int id;

    RoboticArm(SockMatchingRobot sockMatchingRobot, MatchingMachine matchingMachine, int id){
        this.sockMatchingRobot = sockMatchingRobot;
        this.matchingMachine = matchingMachine;
        this.id = id;
    }

    public void run(){
        while(true){
            int sock = sockMatchingRobot.pickSock();
            if(sock == Constants.NULL_SOCK){
                System.out.println("Thread " + this.id + " stopped.");
                break;
            }
            System.out.println("Sock of color " + sock + " recevied by thread " + this.id);
            matchingMachine.matchSock(sock);
        }
    }
}
