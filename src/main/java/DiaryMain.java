import java.util.Scanner;

/**
 * The Diary application class
 * */
public class DiaryMain {
    private DiaryUI UI;

    /**
     * constructor
     */
    private DiaryMain() {
        this.UI = new DiaryUI();
    }

    /**
     * The method which runs the program
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DiaryMain diary1 = new DiaryMain();
        diary1.UI.start(scan);
        // Her skulle init() vært
        scan.close();
    }
}
