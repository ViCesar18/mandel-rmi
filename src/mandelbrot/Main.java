package mandelbrot;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) {
    MandelBrotApplet applet = new MandelBrotApplet();

    JFrame frame = new JFrame();
    frame.setSize(new Dimension(640, 480));
    frame.setTitle("MandelBrot");
    frame.add(applet);
    frame.setFocusable(true);
    frame.setVisible(true);

    applet.init();
    applet.start();
  }
}
