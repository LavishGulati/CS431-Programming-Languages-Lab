package calculator;

import javax.swing.SwingWorker;

public class Scanner extends SwingWorker {
    private Calculator calculator;
    int stage;
    int num_id;
    int func_id;

    Scanner(Calculator calculator){
        stage = 0;
        num_id = 0;
        func_id = 0;
        this.calculator = calculator;
    }

    @Override
    protected String doInBackground() throws Exception {
        while(true){
            try{
                if(stage == 1){
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
                    func_id = (func_id+1)%6;
                }
                else if(stage == 0){
                    calculator.numberArea.numberKeys.get(num_id).setOpaque(true);
                    calculator.repaint();
                    Thread.sleep(Constants.SLEEP_TIME);
                    calculator.numberArea.numberKeys.get(num_id).setOpaque(false);
                    calculator.repaint();
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
