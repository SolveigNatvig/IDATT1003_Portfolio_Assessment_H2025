package Mappevurdering_hovedprosjekt;

public class DiaryMain {
    private DiaryUI UI;

    private DiaryMain(){
        this.UI = new DiaryUI();
    }

    public static void main(String[] args) {
        DiaryMain diary1 = new DiaryMain();
        diary1.UI.start();
    }

}
