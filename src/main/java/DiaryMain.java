import java.util.Scanner;

/**
 * The Diary application class.
 * */
public class DiaryMain {
  private DiaryUserInterface ui;

  /**
  * constructor.
  */
  private DiaryMain() {
    this.ui = new DiaryUserInterface();
  }

  /**
  * The method which runs the program.
  */
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    DiaryMain diary1 = new DiaryMain();
    diary1.ui.start(scan);
    // Her skulle init() vært
    scan.close();
  }
}
