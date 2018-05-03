import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/*
public class Main {
  public static void main(String[] args) {
    Asdf asdf = new Asdf();
    System.out.println("1");
    asdf.main(args);
  }
}

class Asdf {
  public static void main(String[] args) {
    System.out.println("2");
  }
}
*/
public class Main {
  public static void main(String[] args) {
    Asdf as = new Asdf('I', 'x', '`', 'u', '`', 'a', 'x', 'd', 'q', 'f');
    System.out.printf("%s\n", as.getStringFromData());
  }
}

class Asdf {
  private ArrayList<Character> data;

  public Asdf(Character... a) {
    this.data = new ArrayList<Character>(data);
  }

  public String getStringFromData() {
    return this.data
           .stream()
           .filter(c -> c != 120)
           .map(c -> (char) (c + 1))
           .map(c -> "" + c)
           .reduce((a, b) -> a + b)
           .get();
  }

  public String getStringFromData(char... a) {
    return Arrays.asList(data)
      .stream()
      .filter(c -> c != 120)
      .map(c -> (char) (c + 1))
      .map(c -> "" + c)
      .reduce((a, b) -> a + b)
      .get();
  }
}
