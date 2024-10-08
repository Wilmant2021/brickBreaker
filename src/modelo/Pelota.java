package modelo;

public class Pelota {
    private int x, y; // Posición de la pelota
    private int velocidadX, velocidadY; // Velocidad de la pelota
    private int radio; // Radio de la pelota
    private int anchoPanel, altoPanel; // Dimensiones del área de dibujo

    int color;

    //Constructor de Pelota
    public Pelota(int x, int y, int radio, int velocidadX, int velocidadY, int anchoPanel, int altoPanel) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.anchoPanel = anchoPanel;
        this.altoPanel = altoPanel;
    }

    public int getVelocidadX() {
        return velocidadX;
    }

    public int getVelocidadY() {
        return velocidadY;
    }

    public void setAnchoPanel(int anchoPanel) {
        this.anchoPanel = anchoPanel;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    public void setAltoPanel(int altoPanel) {
        this.altoPanel = altoPanel;
    }

    public void mover() {
        x += velocidadX;
        y += velocidadY;

        // Detectar los bordes y cambiar la dirección si es necesario
        //Izquierda y Derecha
        if (x - radio < 0 || x + radio > anchoPanel) {
            velocidadX = -velocidadX;
        }

        //superior e inferior
        if (y - radio < 0 || y + radio > altoPanel) {
            velocidadY = -velocidadY;
        }
    }

    public void rebotarHorizontalmente() {
        velocidadX = -velocidadX;
    }

    public void rebotarVerticalmente() {
        velocidadY = -velocidadY;
    }

    // Métodos getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadio() {
        return radio;
    }

    public int getAnchoPanel(){
        return anchoPanel;
    }

    public int getAltoPanel(){
        return altoPanel;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }

    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }



}

