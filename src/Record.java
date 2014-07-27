import java.lang.String;

import java.util.HashMap;
import java.util.Map;

public class Record {

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
      Object value = parts[i];
      if (Constant.KEY_ATTR_NAMES.contains(key)) {
        value = Gene.parse(parts[i]);
      }
      if (value != null) result.attributes.put(attrNames[i], value);
    }

    return result;
  }

  private String fileName = "";
  private Map<String, Object> attributes = new HashMap<String, Object>();
}
