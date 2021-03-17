package rmi;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Mandelbrot extends Remote {
    Color pintaPonto(int x, int y, int interacoes, Vetor2D posicaoFractal, double zoom) throws RemoteException;
}
