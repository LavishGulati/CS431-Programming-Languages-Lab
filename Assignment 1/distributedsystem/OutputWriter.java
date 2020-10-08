package distributedsystem;

// Libraries required by the program
import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* Output Writer writes the final record into the specified files:
stud_info.txt, sorted_roll.txt and sorted_name.txt */
public class OutputWriter {
    private DistributedSystem distributedSystem;

    // Constructor for output writer
    OutputWriter(DistributedSystem distributedSystem){
        this.distributedSystem = distributedSystem;
    }

    // Writes stud_info.txt
    private void writeStudInfo() throws IOException {
        File file = new File(Constants.STUD_INFO);
        // Clear the existing file
        FileWriter writer = new FileWriter(file, false);
        // Iterate over each record entry and write it to the file
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

    // Writes sorted_roll.txt
    private void writeSortedRoll() throws IOException {
        File file = new File(Constants.SORTED_ROLL);
        // Creates new output file
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        // Collects all the roll numbers from the record and sorts it
        ArrayList<String> rolls = new ArrayList<>(distributedSystem.record.keySet());
        Collections.sort(rolls);
        /* Iterates over all sorted roll numbers and writes the specified entry
        to the file */
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

    // Custom comparator to sort student entries based on names
    private static Comparator<ArrayList<String>> NameComparator = new Comparator<ArrayList<String>>(){
        public int compare(ArrayList<String> a1, ArrayList<String> a2){
            String name1 = a1.get(1);
            String name2 = a2.get(1);
            return name1.compareTo(name2);
        }
    };

    // Writes sorted_name.txt
    private void writeSortedName() throws IOException {
        File file = new File(Constants.SORTED_NAME);
        // Creates new output file
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        /* Collects all the entries from the record into a list and sorts it
        according to student names */
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

        /* Iterates over all sorted entries (based on names) and writes the
        specified entry to the file */
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

    // Writes the final output to the specified files
    public void writeOutput() throws IOException {
        writeStudInfo();
        writeSortedRoll();
        writeSortedName();
    }
}
