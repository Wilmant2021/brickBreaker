package modelo;

public class Bloque {
    private int idBloque;
    private int x, y; // Posición del bloque
    private int ancho, alto; // Dimensiones del bloque
    private int resistencia; // Número de golpes que puede soportar

    private static String[] bloques = {
            "resources/imagenes/bloques/Level1_bloque.png", "resources/imagenes/bloques/level2_bloque1.png",
            "resources/imagenes/bloques/level2_bloque2.png", "resources/imagenes/bloques/Level3_bloque1.png",
            "resources/imagenes/bloques/Level3_bloque2.png", "resources/imagenes/bloques/Level3_bloque3.png"
    };

    public Bloque(int idBloque, int x, int y, int ancho, int alto, int resistencia) {
        this.idBloque = idBloque;
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

    // Método que verifica si el bloque está destruido
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

    public int getResistencia() {
        return resistencia; // Método para obtener la resistencia actual
    }

    public int getIdBloque() {
        return idBloque;
    }

    public String getImagen(int idBloque, int idResistencia) {
        switch (idBloque) {
            case 1:
                return bloques[0];
            case 2:
                if(resistencia == 2){
                    return bloques[1];
                }else{
                    return bloques[2];
                }
            case 3:
                if(resistencia == 3){
                    return bloques[3];
                }else if(resistencia == 2){
                    return bloques[4];
                }else{
                    return bloques[5];
                }
            default:
                break;
        }
        return bloques[idBloque-1];
    }
}
