import java.util.HashMap;
import java.util.Map;

public class Record implements Comparable {

  private Record(String fileName) {
    this.fileName = fileName;
  }

  public static Record parse(
      String fileName, String[] attrNames, String line) {

    Record result = new Record(fileName);

    String[] parts = line.split(Constant.DELIMITER);
    int L = parts.length;
    if (attrNames.length != L) return null;

    for (int i = 0; i < L; ++i) {
      String key = attrNames[i];
      result.attributes.put(key, parts[i]);
      if (Constant.KEY_ATTR_NAMES.contains(key)) {
        result.gene = Gene.parse(parts[i]);
        if (result.gene == null) return null;
      }
    }

    return result;
  }

  public static boolean join(Record left, Record right) {
    return left.gene.compareTo(right.gene) == 0;
  }

  @Override
  public int compareTo(Object obj) {
    Record o = (Record) obj;
    int result = gene.compareTo(o.gene);
    if (result != 0) return result;
    for (String key : attributes.keySet()) {
      String value = attributes.get(key);
      String rvalue = o.attributes.get(key);
      if (rvalue == null) return -1;
      result = value.compareTo(rvalue);
      if (result != 0) return result;
    }
    return 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(fileName);
    for (String value : attributes.values()) {
      sb.append(Constant.DELIMITER);
      sb.append(value);
    }
    return sb.toString();
  }

  private Gene gene = null;
  private String fileName = "";
  private Map<String, String> attributes = new HashMap<String, String>();
}
