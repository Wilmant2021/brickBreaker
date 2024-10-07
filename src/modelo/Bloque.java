package modelo;

public class Bloque {
    private int x, y; // Posición del bloque
    private int ancho, alto; // Dimensiones del bloque
    private int resistencia; // Número de golpes que puede soportar

    public Bloque(int x, int y, int ancho, int alto, int resistencia) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.resistencia = resistencia;
    }

    // Método para manejar la colisión con la pelota
    public void reducirResistencia() {
        if (resistencia > 0) {
            resistencia--;
        }
    }

    public boolean estaDestruido() {
        return resistencia <= 0;
    }

    // Métodos getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}
