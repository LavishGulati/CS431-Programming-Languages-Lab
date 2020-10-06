package calculator2;

import javax.swing.SwingWorker;

public class NumberScanner extends SwingWorker {
    private Calculator2 calculator;
    public int id;

    NumberScanner(Calculator2 calculator){
        this.calculator = calculator;
        id = 0;
    }

    @Override
    protected String doInBackground() throws Exception {
        while(true){
            try{
                calculator.numberArea.numberKeys.get(id).setOpaque(true);
                calculator.repaint();
                Thread.sleep(Constants.SLEEP_TIME);
                calculator.numberArea.numberKeys.get(id).setOpaque(false);
                calculator.repaint();
                id = (id+1)%10;
            }
            catch(Exception e){
                e.printStackTrace();
                break;
            }
        }
        return null;
    }
}
