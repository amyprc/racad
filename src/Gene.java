public class Gene implements Comparable {
  private Gene() { }

  /**Parse a gene.
    *
    * e.g. 1q2.2, Xp1.1, 3p11.1-p11.0
    */
  public static Gene parse(String line) {
    line = _init(line);
    if (line == "") return null;

    Gene result = new Gene();

    int pos = line.indexOf('-');
    if (pos == -1) {
      result._parse(line);
    } else {
      result._parse(line.substring(0, pos));
      result._parse(line.substring(pos + 1));
    }

    return result;
  }

  @Override
  public int compareTo(Object obj) {
    Gene o = (Gene) obj;

    int result = index.compareTo(o.index);
    if (result != 0) return result;
    if (arm != o.arm) return arm - o.arm;
    if (left < o.left) return -1;
    if (left > o.left) return 1;
    return 0;
  }

  private static String _init(String line) {
    char c = line.charAt(0);
    if (c == '"') {
      line = line.substring(1);
      c = line.charAt(0);
    }

    do {
      if ((c >= '0') && (c <= '9')) break;
      if ((c == 'X') || (c == 'Y')) break;
      if ((c == 'p') || (c == 'q')) break;
      return "";
    } while (false);

    return line;
  }

  private void _parse(String line) {
    int pos = line.indexOf('p');
    if (pos == -1) {
      pos = line.indexOf('q');
      arm = 'q';
    } else {
      arm = 'p';
    }

    int rpos = line.indexOf('[');
    if (rpos == -1) rpos = line.length();
    double value = Double.parseDouble(line.substring(pos + 1, rpos));
    if (pos != 0) {
      index = line.substring(0, pos);
      left = value;
      right = value;
    } else {
      // parse tail
      if (value < left) {
        right = left;
        left = value;
      } else {
        right = value;
      }
    }
  }

  private String index = "";
  private char arm = ' ';
  private double left = 0, right = 0;
}
