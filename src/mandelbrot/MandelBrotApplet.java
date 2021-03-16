package mandelbrot;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;

public class MandelBrotApplet extends Applet implements MouseListener, MouseMotionListener {

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
    private Ponto p;

    /**
     * Inicializa recursos utilizados pelo Applet
     */
    public void init(Ponto p) {
        this.p = p;
        offscreen = this.createImage(TAMANHO_X, TAMANHO_Y);

        posicaoFractal = new Vetor2D(-0.8, 0);
        posicaoJanela = new Vetor2D((double) TAMANHO_X / 2, (double) TAMANHO_Y / 2);

        zoom = TAMANHO_X * 0.125296875;
        fatorZoom = 2.178;

        interacoes = (int) ((2048 - TAMANHO_X) * 0.049715909 * (Math.log(zoom) / Math
                .log(10)));

        this.resize(TAMANHO_X, TAMANHO_Y);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    /**
     * Inicia applet e pinta o fractal pela primeira vez
     */
    public void start() {
        Graphics i = offscreen.getGraphics();

        for (int x = 0; x < TAMANHO_X; x++) {
            for (int y = 0; y < TAMANHO_Y; y++) {
                try {
                    i.setColor(p.pintaPonto(x, y, interacoes, posicaoFractal, zoom));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                i.drawLine(x, y, x + 1, y);
            }
        }
    }

    /**
     * Recalcula imagem do fractal e desenha double buffer
     */
    public void update(Graphics g) {

        Graphics i = offscreen.getGraphics();
        Color corPixel;

        if (recalcular) {
            for (int y = 0; y < TAMANHO_Y; y++) {
                for (int x = 0; x < TAMANHO_X; x++) {
                    try {
                        corPixel = p.pintaPonto(x, y, interacoes, posicaoFractal, zoom);
                        i.setColor(corPixel);
                        i.drawLine(x, y, x + 1, y);
                        g.setColor(corPixel);
                        g.drawLine(x, y, x + 1, y);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        paint(g);
    }

    /**
     * Pinta double buffer na saida grafica e desenha o retangulo de zoom
     */
    public void paint(Graphics g) {

        g.drawImage(offscreen, 0, 0, this);

        /* Desenha retangulo de zoom */
        if (janelaZoom) {
            g.setColor(new Color(0xFFFF00));
             g.drawRect((int) (posicaoJanela.x - (MEIO_X / fatorZoom)),
                    (int) (posicaoJanela.y - (MEIO_Y / fatorZoom)),
                    (int) (2.0f * (MEIO_X / fatorZoom)),
                    (int) (2.0f * (MEIO_Y / fatorZoom)));
        }

    }

    /**
     * Metodo chamado no evento de clique do mouse. Efetua zoom in ou out dependendo do botao
     */
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

    /**
     * Quando o mouse entra na area do applet
     */
    public void mouseEntered(MouseEvent e) {
        janelaZoom = true;
    }

    /**
     * Quando o mouse sai da area do applet
     */
    public void mouseExited(MouseEvent e) {
        janelaZoom = false;

        recalcular = false;
        this.repaint();
    }

    /**
     * Metodo nao utilizado
     */
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Metodo nao utilizado
     */
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Metodo chamado no evento de arrastar do mouse. causa o redimensionamento do retangulo de zoom
     */
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

    /**
     * Metodo chamado no evento de movimento do mouse. Reposiciona o retangulo de zoom.
     */
    public void mouseMoved(MouseEvent e) {
        posicaoJanela.x = e.getX();
        py = e.getY();
        posicaoJanela.y = e.getY();

        recalcular = false;
        this.repaint();
    }

}
