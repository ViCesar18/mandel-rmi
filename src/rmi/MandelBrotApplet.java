package rmi;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serial;

public class MandelBrotApplet extends Applet implements MouseListener, MouseMotionListener {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int TAMANHO_X = 640;
    private static final int TAMANHO_Y = 480;
    private static final int MEIO_X = TAMANHO_X / 2;
    private static final int MEIO_Y = TAMANHO_Y / 2;
    private Vetor2D posicaoFractal;
    private Vetor2D posicaoJanela;
    private Image offscreen;
    private boolean recalcular = true;
    private boolean janelaZoom = true;
    private double zoom;
    private double fatorZoom;
    private int interacoes;
    private int py;
    private Mandelbrot mandelbrot;
    private Mandelbrot mandelbrot2;

    public void init(Mandelbrot mandelbrot, Mandelbrot mandelbrot2) {
        offscreen = this.createImage(TAMANHO_X, TAMANHO_Y);
        posicaoFractal = new Vetor2D(-0.8, 0);
        posicaoJanela = new Vetor2D((double) TAMANHO_X / 2, (double) TAMANHO_Y / 2);
        zoom = TAMANHO_X * 0.125296875;
        fatorZoom = 2.178;
        interacoes = (int) ((2048 - TAMANHO_X) * 0.049715909 * (Math.log(zoom) / Math.log(10)));
        this.resize(TAMANHO_X, TAMANHO_Y);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.mandelbrot = mandelbrot;
        this.mandelbrot2 = mandelbrot2;
    }

    public void start() {
        Graphics i = offscreen.getGraphics();
        Runnable mandelbrotThread1 = new MandelbrotThread(0, 0, TAMANHO_X, MEIO_Y, i, null, mandelbrot, interacoes, posicaoFractal, zoom);
        Runnable mandelbrotThread2 = new MandelbrotThread(0, MEIO_Y, TAMANHO_X, TAMANHO_Y, i, null, mandelbrot2, interacoes, posicaoFractal, zoom);

        Thread thread1 = new Thread(mandelbrotThread1);
        Thread thread2 = new Thread(mandelbrotThread2);

        thread1.start();
        thread2.start();

    }

    public void update(Graphics g) {
        Graphics i = offscreen.getGraphics();
        if (recalcular) {
            Runnable mandelbrotThread1 = new MandelbrotThread(0, 0, TAMANHO_X, MEIO_Y, i, g, mandelbrot, interacoes, posicaoFractal, zoom);
            Runnable mandelbrotThread2 = new MandelbrotThread(0, MEIO_Y, TAMANHO_X, TAMANHO_Y, i, g, mandelbrot2, interacoes, posicaoFractal, zoom);

            Thread thread1 = new Thread(mandelbrotThread1);
            Thread thread2 = new Thread(mandelbrotThread2);

            thread1.start();
            thread2.start();
        }
        paint(g);
    }

    public void paint(Graphics g) {
        g.drawImage(offscreen, 0, 0, this);
        if (janelaZoom) {
            g.setColor(new Color(0xFFFF00));
            g.drawRect((int) (posicaoJanela.x - (MEIO_X / fatorZoom)),
                    (int) (posicaoJanela.y - (MEIO_Y / fatorZoom)),
                    (int) (2.0f * (MEIO_X / fatorZoom)),
                    (int) (2.0f * (MEIO_Y / fatorZoom)));
        }

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            posicaoFractal.x += ((posicaoJanela.x - MEIO_X) / zoom);
            posicaoFractal.y += ((posicaoJanela.y - MEIO_Y) / zoom);
            zoom *= fatorZoom;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            posicaoFractal.x += (((MEIO_X - posicaoJanela.x) / zoom) * fatorZoom);
            posicaoFractal.y += (((MEIO_Y - posicaoJanela.y) / zoom) * fatorZoom);
            zoom /= fatorZoom;

        } else if (e.getButton() == MouseEvent.BUTTON2) {
            posicaoFractal.x = -0.8;
            posicaoFractal.y = 0;
            zoom = TAMANHO_X * 0.125296875;
        }
        interacoes = (int) ((2048 - TAMANHO_X) * 0.049715909 * (Math.log(zoom) / Math
                .log(10)));
        recalcular = true;
        this.repaint();
    }

    public void mouseEntered(MouseEvent e) {
        janelaZoom = true;
    }

    public void mouseExited(MouseEvent e) {
        janelaZoom = false;
        recalcular = false;
        this.repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (py > e.getY()) {
            fatorZoom *= 0.98f;
        } else if (py < e.getY()) {
            fatorZoom *= 1.02f;
        }
        py = e.getY();
        recalcular = false;
        this.repaint();
    }

    public void mouseMoved(MouseEvent e) {
        posicaoJanela.x = e.getX();
        py = e.getY();
        posicaoJanela.y = e.getY();
        recalcular = false;
        this.repaint();
    }
}
