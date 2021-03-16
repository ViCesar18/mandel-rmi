package rmi;

import java.awt.*;
import java.rmi.RemoteException;

public class MandelbrotThread implements Runnable {

    private final int x_inicial;
    private final int y_inicial;
    private final int x_final;
    private final int y_final;
    private final Graphics graphics;
    private final Mandelbrot mandelbrot;
    private final int interacoes;
    private final double zoom;
    private final Vetor2D posicaoFractal;


    public MandelbrotThread(int x_inicial, int y_inicial, int x_final, int y_final, Graphics graphics, Mandelbrot mandelbrot, int interacoes, Vetor2D posicaoFractal, double zoom) {
        this.x_inicial = x_inicial;
        this.y_inicial = y_inicial;
        this.x_final = x_final;
        this.y_final = y_final;
        this.graphics = graphics;
        this.mandelbrot = mandelbrot;
        this.interacoes = interacoes;
        this.zoom = zoom;
        this.posicaoFractal = posicaoFractal;
    }

    @Override
    public void run() {
        try {
            for (int y = y_inicial; y < y_final; y++) {
                for (int x = x_inicial; x < x_final; x++) {
                    graphics.setColor(mandelbrot.pintaPonto(x, y, interacoes, posicaoFractal, zoom));
                    graphics.drawLine(x, y, x + 1, y);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
