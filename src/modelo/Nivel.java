package modelo;

import utils.Sonido;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Nivel {
    private List<Bloque> bloques;
    private int indiceNivel;
    private int puntuacion;
    private int vidas;
    private int velocidadPelota;
    private Image fondo;
    private Sonido sonido;
    private int anchoImgFondo;
    private int altoImgFondo;
    private static int ancho = 800;
    private static int alto = 600;
    private static String[] imagenesFondo = {
            "resources/imagenes/fondo/nivel1.png", "resources/imagenes/fondo/nivel2.jpeg",
            "resources/imagenes/fondo/nivel3.jpeg"
    };

    private static String[] musicaFondo = {
            "resources/sonidos/fondo/level1.wav", "resources/sonidos/fondo/level2.wav",
            "resources/sonidos/fondo/level3.wav"
    };

    public Nivel(int indiceNivel, int puntuacionInicial, int vidasIniciales, int velocidadPelota) {
        this.indiceNivel = indiceNivel;
        this.puntuacion = puntuacionInicial;
        this.vidas = vidasIniciales;
        this.velocidadPelota = velocidadPelota;
        this.bloques = new ArrayList<>();

        URL urlFondo = getClass().getClassLoader().getResource(imagenesFondo[indiceNivel - 1]);

        if (urlFondo != null) {
            fondo = new ImageIcon(urlFondo).getImage();
            anchoImgFondo = fondo.getWidth(null);
            altoImgFondo = fondo.getHeight(null);
        } else {
            System.err.println("No se pudo cargar la imagen del fondo");
        }

        cargarNivel();
    }

    public void cargarNivel() {
        if (indiceNivel == 1) {
            velocidadPelota = 7;
            bloques = crearBloquesNivel1();
        } else if (indiceNivel == 2) {
            velocidadPelota = 8;
            bloques = crearBloquesNivel2();
        } else if (indiceNivel == 3) {
            velocidadPelota = 9;
            bloques = crearBloquesNivel3();
        }
    }

    // Método para reiniciar el nivel
    public void reiniciarNivel() {
        this.puntuacion = 0; // O el valor que desees
        this.vidas = 3; // Restablece las vidas a un valor inicial
        cargarNivel(); // Vuelve a cargar los bloques
    }

    private List<Bloque> crearBloquesNivel1() {
        ArrayList<modelo.Bloque> bloques = new ArrayList<>();
        int fila = 4;
        int columna = 10;
        int anchoBloque = ancho / columna;
        int altoBloque = 30;

        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                bloques.add(new modelo.Bloque(1, j * anchoBloque, i * altoBloque, anchoBloque - 5, altoBloque - 5, 1));
            }
        }
        return bloques;
    }

    private ArrayList<modelo.Bloque> crearBloquesNivel2() {
        ArrayList<modelo.Bloque> bloques = new ArrayList<>();
        int fila = 5;  // Más filas que el nivel 1
        int columna = 10;  // Misma cantidad de columnas
        int anchoBloque = ancho / columna;
        int altoBloque = 30;

        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                // Crear resistencia variable
                int resistencia = (i % 2 == 0) ? 1 : 2;  // Bloques alternos con resistencia 1 y 2

                // Bloques de los bordes son más fuertes
                if (i == 0 || i == fila - 1) {
                    resistencia = 3;  // Bloques de los bordes son más fuertes
                }

                bloques.add(new modelo.Bloque(resistencia, j * anchoBloque, i * altoBloque, anchoBloque - 5, altoBloque - 5, resistencia));
            }
        }
        return bloques;
    }

    private List<Bloque> crearBloquesNivel3() {
        ArrayList<modelo.Bloque> bloques = new ArrayList<>();
        int fila = 6;  // Más filas que el nivel 1
        int columna = 10;  // Misma cantidad de columnas
        int anchoBloque = ancho / columna;
        int altoBloque = 30;

        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < columna; j++) {
                // Crear resistencia variable
                int resistencia = (i % 2 == 0) ? 1 : 2;  // Bloques alternos con resistencia 1 y 2

                // Bloques de los bordes son más fuertes
                if (i == 0 || i == fila - 1) {
                    resistencia = 3;  // Bloques de los bordes son más fuertes
                }

                bloques.add(new modelo.Bloque(resistencia, j * anchoBloque, i * altoBloque, anchoBloque - 5, altoBloque - 5, resistencia));
            }
        }
        return bloques;
    }

    public Boolean nivelCompletado() {
        return bloques.isEmpty();
    }

    // Métodos getter
    public List<Bloque> getBloques() {
        return bloques;
    }

    public String getFondo() {
        return imagenesFondo[indiceNivel - 1];
    }

    public String getMusicaFondo() {
        return musicaFondo[indiceNivel - 1];
    }

    public Sonido getSonido() {
        return sonido;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public int getVidas() {
        return vidas;
    }

    public int getVelocidadPelota() {
        return velocidadPelota;
    }

    public int getIndiceNivel() {
        return indiceNivel;
    }

    // Métodos setter
    public void setPuntuacion(int puntuacion) {
        this.puntuacion += puntuacion;
    }

    public void setVelocidadPelota(int velocidadPelota) {
        this.velocidadPelota = velocidadPelota;
    }

    public void setVidas() {
        this.vidas -= 1;
    }

    public void setIndiceNivel(int indiceNivel) {
        this.indiceNivel = indiceNivel;
    }
}
