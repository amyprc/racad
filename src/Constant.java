import java.lang.String;

import java.util.HashSet;
import java.util.Set;

public class Constant {
  public static final String[] DATA_FILE_NAMES = new String[] {
    "CAD.txt", "CAD.tab", "RA.txt", "RA.tab",
  };

  public static final String DELIMITER = "\t";

  public static final Set<String> KEY_ATTR_NAMES = new HashSet<String>() {{
    add("Map");
    add("Region");
  }};
}
