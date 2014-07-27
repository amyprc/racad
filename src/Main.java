import java.io.File;
import java.io.PrintWriter;

import java.lang.String;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Main {
  public static void main(String[] args) {
    Main p = new Main(args);
    p.run();
  }

  private Main(String[] args) { this.args = args; }

  private void run() {
    parseArgs();
    loadRecords();
    joinRecords();
    saveRecords();
  }

  private void parseArgs() {
  try {

    this.dataDir = new File(args[0]);
    this.outputFile = new File(args[1]);

  } catch (Exception e) {
    e.printStackTrace();
  }
  }

  private void loadRecords() {
    for (String inputFileName : Constant.DATA_FILE_NAMES) {
    try {

      Scanner fin = new Scanner(new File(dataDir, inputFileName));
      String line = fin.nextLine();
      String[] attrNames = line.split(Constant.DELIMITER);

      List<Record> newRecords = new ArrayList<Record>();
      while (fin.hasNextLine()) {
        line = fin.nextLine();
        Record record = Record.parse(inputFileName, attrNames, line);
        if (record == null) continue;
        newRecords.add(record);
      }
      fin.close();

      String diseaseName = parseDisease(inputFileName);
      List<Record> diseaseRecords = records.get(diseaseName);
      if (diseaseRecords == null) {
        diseaseRecords = new ArrayList<Record>();
        records.put(diseaseName, diseaseRecords);
      }
      diseaseRecords.addAll(newRecords);

    } catch (Exception e) {
      e.printStackTrace();
    }
    }
  }

  private void joinRecords() {
    String[] diseaseNames = records.keySet().toArray(new String[0]);
    int numDisease = diseaseNames.length;
    for (int i = 0; i < numDisease - 1; ++i) {
      List<Record> lrecords = records.get(diseaseNames[i]);
      for (Record lrecord : lrecords) {
        for (int j = i + 1; j < numDisease; ++j) {
          List<Record> rrecords = records.get(diseaseNames[j]);
          for (Record rrecord : rrecords) {
            if (Record.join(lrecord, rrecord)) {
              result.add(lrecord);
              result.add(rrecord);
            }
          }
        }
      }
    }
  }

  private void saveRecords() {
  try {

    PrintWriter fout = new PrintWriter(outputFile);
    for (Record record : result) {
      fout.println(record.toString());
    }
    fout.close();

  } catch (Exception e) {
    e.printStackTrace();
  }
  }

  private String parseDisease(String fileName) {
    return fileName.substring(0, fileName.indexOf("."));
  }

  private String[] args = null;
  private Map<String, List<Record>> records =
      new HashMap<String, List<Record>>();
  private SortedSet<Record> result = new TreeSet<Record>();
  private File dataDir = null, outputFile = null;
}

