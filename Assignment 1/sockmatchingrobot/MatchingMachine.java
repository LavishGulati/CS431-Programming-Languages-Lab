package sockmatchingrobot;

public class MatchingMachine {
    private ShelfManagerRobot shelfManagerRobot;
    private Boolean isWhiteSock;
    private Boolean isBlackSock;
    private Boolean isBlueSock;
    private Boolean isGreySock;

    MatchingMachine(ShelfManagerRobot shelfManagerRobot){
        this.shelfManagerRobot = shelfManagerRobot;
        isWhiteSock = false;
        isBlackSock = false;
        isBlueSock = false;
        isGreySock = false;
    }

    void matchSock(int sock){
        if(sock == Constants.WHITE_SOCK){
            synchronized(isWhiteSock){
                if(isWhiteSock){
                    shelfManagerRobot.increasePairCount(Constants.WHITE_SOCK);
                    isWhiteSock = false;
                }
                else{
                    isWhiteSock = true;
                }
            }
        }
        else if(sock == Constants.BLACK_SOCK){
            synchronized(isBlackSock){
                if(isBlackSock){
                    shelfManagerRobot.increasePairCount(Constants.BLACK_SOCK);
                    isBlackSock = false;
                }
                else{
                    isBlackSock = true;
                }
            }
        }
        else if(sock == Constants.BLUE_SOCK){
            synchronized(isBlueSock){
                if(isBlueSock){
                    shelfManagerRobot.increasePairCount(Constants.BLUE_SOCK);
                    isBlueSock = false;
                }
                else{
                    isBlueSock = true;
                }
            }
        }
        else if(sock == Constants.GREY_SOCK){
            synchronized(isGreySock){
                if(isGreySock){
                    shelfManagerRobot.increasePairCount(Constants.GREY_SOCK);
                    isGreySock = false;
                }
                else{
                    isGreySock = true;
                }
            }
        }
    }
}
