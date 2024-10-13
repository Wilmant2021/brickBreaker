package modelo;

import java.util.List;

public class Juego {
    private int vidas;
    private int puntuacion;
    private List<Bloque> bloques;

    public Juego(int vidas, List<Bloque> bloques) {
        this.vidas = vidas;
        this.bloques = bloques;
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

    public boolean nivelCompletado() {
        return bloques.stream().allMatch(Bloque::estaDestruido);
    }

    // Métodos getter
    public static int getVidas() {
        return 3;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}
