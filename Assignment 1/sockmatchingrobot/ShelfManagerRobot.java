package sockmatchingrobot;

/* Picks the sock pair from matching machine and puts it on the shelf
(increases the sock pair count) */ 
public class ShelfManagerRobot {
    // Number of sock pairs of different colors
    private Integer numWhitePairs;
    private Integer numBlackPairs;
    private Integer numBluePairs;
    private Integer numGreyPairs;

    // Constructor for shelf manager robot
    ShelfManagerRobot(){
        // Initially, zero pairs of all colors
        numWhitePairs = 0;
        numBlackPairs = 0;
        numBluePairs = 0;
        numGreyPairs = 0;
    }

    // Increases the sock pair count
    void increasePairCount(int sock){
        if(sock == Constants.WHITE){
            // Handle numWhitePairs variable synchronously
            synchronized(numWhitePairs){
                numWhitePairs++;
            }
        }
        else if(sock == Constants.BLACK){
            // Handle numBlackPairs variable synchronously
            synchronized(numBlackPairs){
                numBlackPairs++;
            }
        }
        else if(sock == Constants.BLUE){
            // Handle numBluePairs variable synchronously
            synchronized(numBluePairs){
                numBluePairs++;
            }
        }
        else if(sock == Constants.GREY){
            // Handle numGreyPairs variable synchronously
            synchronized(numGreyPairs){
                numGreyPairs++;
            }
        }
    }

    /* Prints the final output by reporting the total number of sock pairs of
    each color */
    void printOutput(){
        System.out.println("NUMBER OF SOCK PAIRS");
        System.out.println("White: " + numWhitePairs);
        System.out.println("Black: " + numBlackPairs);
        System.out.println("Blue: " + numBluePairs);
        System.out.println("Grey: " + numGreyPairs);
    }
}
