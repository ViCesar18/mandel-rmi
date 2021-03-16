package mandelbrot;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MandelBrotServer {
  MandelBrotServer() {
    try{
      LocateRegistry.createRegistry(2020);
      Ponto p = new PontoImp();
      Naming.rebind("//localhost:2020/MandelBrot", p);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new MandelBrotServer();

    System.out.println("Server iniciado.");
  }
}
