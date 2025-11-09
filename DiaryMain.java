package mappe_2025_solveignatvig;

import java.util.Scanner;

public class DiaryMain {
    private DiaryUI UI;

    private DiaryMain(){
        this.UI = new DiaryUI();
    }

    public static void main(String[] args) {
        Scanner scan = new java.util.Scanner(System.in);
        DiaryMain diary1 = new DiaryMain();
        diary1.UI.start(scan);
    }

}
