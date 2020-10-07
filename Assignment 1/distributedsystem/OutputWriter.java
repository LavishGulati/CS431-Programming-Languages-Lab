package distributedsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWriter {
    private DistributedSystem distributedSystem;

    OutputWriter(DistributedSystem distributedSystem){
        this.distributedSystem = distributedSystem;
    }

    private void writeStudInfo() throws IOException {
        File file = new File(Constants.STUD_INFO);
        FileWriter writer = new FileWriter(file, false);
        for(Map.Entry<String, ArrayList<String>> entry : distributedSystem.record.entrySet()){
            writer.append(entry.getKey());
            for(String value : entry.getValue()){
                writer.append(',');
                writer.append(value);
            }
            writer.append('\n');
        }
        writer.flush();
        writer.close();
    }

    private void writeSortedRoll() throws IOException {
        File file = new File(Constants.SORTED_ROLL);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        ArrayList<String> rolls = new ArrayList<>(distributedSystem.record.keySet());
        Collections.sort(rolls);
        for(String key : rolls){
            writer.append(key);
            for(String value : distributedSystem.record.get(key)){
                writer.append(',');
                writer.append(value);
            }
            writer.append('\n');
        }
        writer.flush();
        writer.close();
    }

    private static Comparator<ArrayList<String>> NameComparator = new Comparator<ArrayList<String>>(){
        public int compare(ArrayList<String> a1, ArrayList<String> a2){
            String name1 = a1.get(1);
            String name2 = a2.get(1);
            return name1.compareTo(name2);
        }
    };

    private void writeSortedName() throws IOException {
        File file = new File(Constants.SORTED_NAME);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for(Map.Entry<String, ArrayList<String>> entry : distributedSystem.record.entrySet()){
            ArrayList<String> newEntry = new ArrayList<>();
            newEntry.add(entry.getKey());
            for(String value : entry.getValue()){
                newEntry.add(value);
            }
            data.add(newEntry);
        }
        Collections.sort(data, NameComparator);

        for(ArrayList<String> entry : data){
            writer.append(entry.get(0));
            for(int i = 1; i < 5; i++){
                writer.append(',');
                writer.append(entry.get(i));
            }
            writer.append('\n');
        }
        writer.flush();
        writer.close();
    }

    public void writeOutput() throws IOException {
        writeStudInfo();
        writeSortedRoll();
        writeSortedName();
    }
}
