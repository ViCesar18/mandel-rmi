package mandelbrot;

import java.awt.Dimension;
import java.rmi.Naming;
import javax.swing.JFrame;

public class MandelBrotClient {
    public static void main(String[] args) {
        Ponto p = null;

        try{
            p = (Ponto) Naming.lookup("//localhost:2020/MandelBrot");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MandelBrotApplet applet = new MandelBrotApplet();

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(640, 480));
        frame.setTitle("MandelBrot");
        frame.add(applet);
        frame.setFocusable(true);
        frame.setVisible(true);

        applet.init(p);
        applet.start();
    }
}
