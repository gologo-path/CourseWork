import GUI.MainFrame;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
