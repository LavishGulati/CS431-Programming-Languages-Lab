package calculator2;

import javax.swing.SwingWorker;

public class FunctionScanner extends SwingWorker {
    private Calculator2 calculator;
    public int id;

    FunctionScanner(Calculator2 calculator){
        this.calculator = calculator;
        id = 0;
    }

    @Override
    protected String doInBackground() throws Exception {
        while(true){
            try{
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
