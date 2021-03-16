package rmi;

import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;

public class MandelbrotClient {
    public static void main(String[] args) {
        try {
            Mandelbrot mandelbrot = (Mandelbrot) Naming.lookup("//localhost:2020/MandelbrotService");
            MandelBrotApplet applet = new MandelBrotApplet();

            JFrame frame = new JFrame();
            frame.setSize(new Dimension(640, 480));
            frame.setTitle("MandelBrot");
            frame.add(applet);
            frame.setFocusable(true);
            frame.setVisible(true);

            applet.init(mandelbrot);
            applet.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
