package rmi;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MandelbrotImple extends UnicastRemoteObject implements Mandelbrot {
    private static final int TAMANHO_X = 640;
    private static final int TAMANHO_Y = 480;
    private static final int MEIO_X = TAMANHO_X / 2;
    private static final int MEIO_Y = TAMANHO_Y / 2;

    protected MandelbrotImple() throws RemoteException {
        super();
    }

    @Override
    public Color pintaPonto(int x, int y, int interacoes, Vetor2D posicaoFractal, double zoom) throws RemoteException {
        Vetor2D z = new Vetor2D();
        int n = 0;
        double a;

        for (; n < interacoes && z.comprimento() < 3; n++) {
            a = z.x * z.x - (z.y * z.y) + posicaoFractal.x
                    + ((x - MEIO_X) / zoom);
            z.y = z.x * z.y * 2 + posicaoFractal.y + ((y - MEIO_Y) / zoom);
            z.x = a;
        }

        return (n == interacoes) ? new Color(0x000000) :
                new Color((int) ((Math.cos(n / 10.0f) + 1.0f) * 127.0f),
                        (int) ((Math.cos(n / 20.0f) + 1.0f) * 127.0f),
                        (int) ((Math.cos(n / 300.0f) + 1.0f) * 127.0f));
    }


}
