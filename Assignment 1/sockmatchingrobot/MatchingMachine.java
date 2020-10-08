package sockmatchingrobot;

/* The matching machine takes a single sock from a robotic arm, pairs it with
the matching color and passes the sock pair to the shelf manager robot */
public class MatchingMachine {
    private ShelfManagerRobot shelfManagerRobot;
    // To indicate whether single sock or pair of socks is formed
    private Boolean isWhite;
    private Boolean isBlack;
    private Boolean isBlue;
    private Boolean isGrey;

    // Constructor for matching machine
    MatchingMachine(ShelfManagerRobot shelfManagerRobot){
        this.shelfManagerRobot = shelfManagerRobot;
        // Initially, there is no sock pair formed
        isWhite = false;
        isBlack = false;
        isBlue = false;
        isGrey = false;
    }

    /* Pairs the socks with the matching color and passes it to the shelf 
    manager robot */
    void formPair(int color){
        if(color == Constants.WHITE){
            // Handle isWhite variable synchronously
            synchronized(isWhite){
                // Single sock of white color already present, so pair the socks
                if(isWhite){
                    shelfManagerRobot.increasePairCount(Constants.WHITE);
                    isWhite = false;
                    System.out.println("Pair of white socks formed by matching machine");
                }
                // No sock of white color present, so receive it
                else{
                    isWhite = true;
                }
            }
        }
        else if(color == Constants.BLACK){
            // Handle isBlack variable synchronously
            synchronized(isBlack){
                // Single sock of black color already present, so pair the socks
                if(isBlack){
                    shelfManagerRobot.increasePairCount(Constants.BLACK);
                    isBlack = false;
                    System.out.println("Pair of black socks formed by matching machine");
                }
                // No sock of black color present, so receive it
                else{
                    isBlack = true;
                }
            }
        }
        else if(color == Constants.BLUE){
            // Handle isBlue variable synchronously
            synchronized(isBlue){
                // Single sock of blue color already present, so pair the socks
                if(isBlue){
                    shelfManagerRobot.increasePairCount(Constants.BLUE);
                    isBlue = false;
                    System.out.println("Pair of blue socks formed by matching machine");
                }
                // No sock of blue color present, so receive it
                else{
                    isBlue = true;
                }
            }
        }
        else if(color == Constants.GREY){
            // Handle isGrey variable synchronously
            synchronized(isGrey){
                // Single sock of grey color already present, so pair the socks
                if(isGrey){
                    shelfManagerRobot.increasePairCount(Constants.GREY);
                    isGrey = false;
                    System.out.println("Pair of grey socks formed by matching machine");
                }
                // No sock of grey color present, so receive it
                else{
                    isGrey = true;
                }
            }
        }
    }
}
