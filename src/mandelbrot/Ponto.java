package mandelbrot;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Ponto extends Remote {
  Color pintaPonto(int x, int y, int interacoes, Vetor2D posicaoFractal, double zoom) throws RemoteException;
}
