import java.io.File;

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
    saveRecords();
  }

  private void parseArgs() {
  try {

    this.dataDir = new File(args[0]);

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
        newRecords.add(Record.parse(inputFileName, attrNames, line));
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

  private void saveRecords() {
  }

  private String parseDisease(String fileName) {
    return fileName.substring(0, fileName.indexOf("."));
  }

  private String[] args = null;
  private Map<String, List<Record>> records =
      new HashMap<String, List<Record>>();
  private SortedSet<Record> result = new TreeSet<Record>();
  private File dataDir = null;
}

