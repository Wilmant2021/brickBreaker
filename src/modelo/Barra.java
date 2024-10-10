package modelo;

public class Barra {
    private int x, y; // Posición de la barra
    private int ancho, alto; // Dimensiones de la barra
    private int anchoPanel; // Ancho del área de juego (para limitar el movimiento)

    public Barra(int x, int y, int ancho, int alto, int anchoPanel) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.anchoPanel = anchoPanel;
    }



    // Método para mover la barra a una nueva posición, respetando los límites
    public void mover(int nuevaX) {
        // Asegurarse que la barra no salga del área de juego
        if (nuevaX < 0) {
            x = 0;
        } else if (nuevaX + ancho > anchoPanel) {
            x = anchoPanel - ancho;
        } else {
            x = nuevaX;
        }
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

    public int getVelocidadX() {
        return 10;
    }

}
