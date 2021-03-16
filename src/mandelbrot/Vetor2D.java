package mandelbrot;

import java.io.Serializable;

public class Vetor2D implements Serializable {
    public double x;
    public double y;

    Vetor2D() {
        this.x = 0;
        this.y = 0;
    }

    Vetor2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double comprimento() {
        double soma = Math.pow(x, 2) + Math.pow(y, 2);
        return Math.sqrt(soma);
    }
}
