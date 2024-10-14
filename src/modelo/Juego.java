package modelo;

import java.util.List;

public class Juego {
    private int vidas;
    private int puntuacion;
    private List<Nivel> niveles;
    private int nivelActual;

    public Juego(int vidas, List<Nivel> niveles) {
        this.vidas = vidas;
        this.niveles = niveles;
        this.nivelActual = 0;
        this.puntuacion = 0;
    }

    public void reducirVida() {
        if (vidas > 0) {
            vidas--;
        }
    }

    public void incrementarPuntuacion(int puntos) {
        puntuacion += puntos;
    }

    public boolean estaTerminado() {
        return vidas <= 0;
    }

    // Métodos getter y setter
    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Nivel getNivelActual() {
        if (nivelActual < niveles.size()) {
            return niveles.get(nivelActual);
        }
        return null;
    }

    public List<Nivel> getNiveles() {
        return niveles;
    }

    public void reiniciarVidas(int nuevasVidas) {
        this.vidas = nuevasVidas;
    }

}
