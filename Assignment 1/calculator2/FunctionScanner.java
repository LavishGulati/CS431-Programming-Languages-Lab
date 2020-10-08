package calculator2;

// Libraries required by the program
import javax.swing.SwingWorker;

// Function Scanner highlights the buttons in a periodic fashion
public class FunctionScanner extends SwingWorker {
    private Calculator2 calculator;
    // ID of the current function button
    public int id;

    FunctionScanner(Calculator2 calculator){
        this.calculator = calculator;
        // Initial ID point to first button in the panel
        id = 0;
    }

    // Do in background
    @Override
    protected String doInBackground() throws Exception {
        while(true){
            try{
                /* Highlight the button represented by function ID for
                SLEEP_TIME ms */
                switch(id){
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
                id = (id+1)%6;
            }
            catch(Exception e){
                e.printStackTrace();
                break;
            }
        }
        return null;
    }
}
