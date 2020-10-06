package sockmatchingrobot;

public class ShelfManagerRobot {
    private Integer numWhitePairs;
    private Integer numBlackPairs;
    private Integer numBluePairs;
    private Integer numGreyPairs;

    ShelfManagerRobot(){
        numWhitePairs = 0;
        numBlackPairs = 0;
        numBluePairs = 0;
        numGreyPairs = 0;
    }

    void increasePairCount(int sock){
        if(sock == Constants.WHITE_SOCK){
            synchronized(numWhitePairs){
                numWhitePairs++;
            }
        }
        else if(sock == Constants.BLACK_SOCK){
            synchronized(numBlackPairs){
                numBlackPairs++;
            }
        }
        else if(sock == Constants.BLUE_SOCK){
            synchronized(numBluePairs){
                numBluePairs++;
            }
        }
        else if(sock == Constants.GREY_SOCK){
            synchronized(numGreyPairs){
                numGreyPairs++;
            }
        }
    }

    void printOutput(){
        System.out.println("NUMBER OF SOCK PAIRS");
        System.out.println("White: " + numWhitePairs);
        System.out.println("Black: " + numBlackPairs);
        System.out.println("Blue: " + numBluePairs);
        System.out.println("Grey: " + numGreyPairs);
    }
}
