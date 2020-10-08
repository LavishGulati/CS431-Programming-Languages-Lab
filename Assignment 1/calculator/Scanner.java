package calculator;

// Libraries required by the program
import javax.swing.SwingWorker;

// Scanner highlights the buttons in a periodic fashion
public class Scanner extends SwingWorker {
    private Calculator calculator;
    // Indicates whether number area or function area is being highlighted
    int stage;
    // ID of the current numerical button
    int num_id;
    // ID of the current function button
    int func_id;

    Scanner(Calculator calculator){
        // Initially stage is set to number area
        stage = 0;
        // Initial IDs point to first button in the respective panels
        num_id = 0;
        func_id = 0;
        this.calculator = calculator;
    }

    // Do in background
    @Override
    protected String doInBackground() throws Exception {
        while(true){
            try{
                // If function area stage
                if(stage == 1){
                    /* Highlight the button represented by function ID for
                    SLEEP_TIME ms */
                    switch(func_id){
                        case 0:
                            calculator.functionArea.addKey.setOpaque(true);
                            calculator.repaint();
                            Thread.sleep(Constants.SLEEP_TIME);
                            calculator.functionArea.addKey.setOpaque(false);
                            calculator.repaint();
                            break;
                        case 1:
                            calculator.functionArea.subtractKey.setOpaque(true);
                            calculator.repaint();
                            Thread.sleep(Constants.SLEEP_TIME);
                            calculator.functionArea.subtractKey.setOpaque(false);
                            calculator.repaint();
                            break;
                        case 2:
                            calculator.functionArea.productKey.setOpaque(true);
                            calculator.repaint();
                            Thread.sleep(Constants.SLEEP_TIME);
                            calculator.functionArea.productKey.setOpaque(false);
                            calculator.repaint();
                            break;
                        case 3:
                            calculator.functionArea.divisionKey.setOpaque(true);
                            calculator.repaint();
                            Thread.sleep(Constants.SLEEP_TIME);
                            calculator.functionArea.divisionKey.setOpaque(false);
                            calculator.repaint();
                            break;
                        case 4:
                            calculator.functionArea.evaluateKey.setOpaque(true);
                            calculator.repaint();
                            Thread.sleep(Constants.SLEEP_TIME);
                            calculator.functionArea.evaluateKey.setOpaque(false);
                            calculator.repaint();
                            break;
                        case 5:
                            calculator.functionArea.clearKey.setOpaque(true);
                            calculator.repaint();
                            Thread.sleep(Constants.SLEEP_TIME);
                            calculator.functionArea.clearKey.setOpaque(false);
                            calculator.repaint();
                            break;
                    }
                    // Increment function ID to highlight next button
                    func_id = (func_id+1)%6;
                }
                // If number area stage
                else if(stage == 0){
                    /* Highlight the button represented by number ID for
                    SLEEP_TIME ms */
                    calculator.numberArea.numberKeys.get(num_id).setOpaque(true);
                    calculator.repaint();
                    Thread.sleep(Constants.SLEEP_TIME);
                    calculator.numberArea.numberKeys.get(num_id).setOpaque(false);
                    calculator.repaint();
                    // Increment number ID to highlight next button
                    num_id = (num_id+1)%10;
                }
            }
            catch(Exception e){
                e.printStackTrace();
                break;
            }
        }
        return null;
    }
}
