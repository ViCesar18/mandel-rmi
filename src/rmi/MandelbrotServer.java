package rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MandelbrotServer {
    MandelbrotServer() {
        try {
            LocateRegistry.createRegistry(2020);
            Mandelbrot mandelbrot = new MandelbrotImple();
            Naming.rebind("//localhost:2020/MandelbrotService", mandelbrot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MandelbrotServer();
        System.out.println("Server iniciado.");
    }
}
