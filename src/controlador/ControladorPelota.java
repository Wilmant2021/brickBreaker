package controlador;

import modelo.Barra;
import modelo.Juego;
import modelo.Pelota;
import modelo.Bloque;
import utils.Sonido;
import vista.PanelPelota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorPelota {
    private Pelota pelota;
    private Barra barra;
    private List<Bloque> bloques;
    private PanelPelota panelPelota;
    private Timer temporizador;
    private Sonido sonidoRebote;
    private Sonido sonidoPartida;
    private Juego juego;
    private CardLayout cardLayout;
    private JPanel contenedorPaneles;

    public ControladorPelota(Pelota pelota, Barra barra, List<Bloque> bloques, PanelPelota panelPelota, Juego juego, CardLayout cardLayout, JPanel contenedorPaneles) {
        this.pelota = pelota;
        this.barra = barra;
        this.bloques = bloques;
        this.panelPelota = panelPelota;
        this.juego = juego;
        this.cardLayout = cardLayout;
        this.contenedorPaneles = contenedorPaneles;
        sonidoPartida = new Sonido("resources/sonidos/level1.wav");
        sonidoRebote = new Sonido("resources/sonidos/rebotar.wav");

        temporizador = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pelota.mover();
                sonidoPartida.reproducirMusica();
                verificarColision();
                verificarDerrota();
                panelPelota.actualizarPanel();
            }
        });
    }

    private void verificarColision() {
        // Colisión con la barra
        if (pelota.getY() + pelota.getRadio() >= barra.getY() &&
                pelota.getY() - pelota.getRadio() <= barra.getY() + barra.getAlto() &&
                pelota.getX() + pelota.getRadio() >= barra.getX() &&
                pelota.getX() - pelota.getRadio() <= barra.getX() + barra.getAncho()) {

            // Cálculo del punto de impacto en la barra
            int puntoImpacto = pelota.getX() - barra.getX();
            int mitadAncho = barra.getAncho() / 2;

            // Ajustar la velocidad en X según la posición de impacto
            int nuevaVelocidadX = (puntoImpacto - mitadAncho) / (barra.getAncho() / 5);
            pelota.setVelocidadX(nuevaVelocidadX);
            pelota.rebotarVerticalmente();
            sonidoRebote.reproducir();
        }

        // Colisión con los bloques
        for (Bloque bloque : bloques) {
            if (colisionaConBloque(bloque)) {
                int colisionLado = detectarLadoColision(bloque);

                // Ajuste de la dirección según el lado de colisión
                switch (colisionLado) {
                    case 1: // Colisión arriba
                    case 3: // Colisión abajo
                        pelota.rebotarVerticalmente();
                        break;
                    case 2: // Colisión a la izquierda
                        pelota.setX(bloque.getX() - pelota.getRadio()); // Ajustar posición
                        pelota.rebotarHorizontalmente();
                        break;
                    case 4: // Colisión a la derecha
                        pelota.setX(bloque.getX() + bloque.getAncho() + pelota.getRadio()); // Ajustar posición
                        pelota.rebotarHorizontalmente();
                        break;
                }

                // Asegúrate de que el bloque se elimine después de la colisión
                bloques.remove(bloque);
                juego.incrementarPuntuacion(100);
                sonidoRebote.reproducir();
                break;
            }
        }
    }


    // Método para detectar el lado de colisión en el bloque
    private int detectarLadoColision(Bloque bloque) {
        int margen = 5;

        boolean colisionArriba = pelota.getY() - pelota.getRadio() <= bloque.getY() + margen &&
                pelota.getY() + pelota.getRadio() >= bloque.getY();
        boolean colisionAbajo = pelota.getY() + pelota.getRadio() >= bloque.getY() + bloque.getAlto() - margen &&
                pelota.getY() - pelota.getRadio() <= bloque.getY() + bloque.getAlto();
        boolean colisionIzquierda = pelota.getX() - pelota.getRadio() <= bloque.getX() + margen &&
                pelota.getX() + pelota.getRadio() >= bloque.getX();
        boolean colisionDerecha = pelota.getX() + pelota.getRadio() >= bloque.getX() + bloque.getAncho() - margen &&
                pelota.getX() - pelota.getRadio() <= bloque.getX() + bloque.getAncho();

        if (colisionArriba) return 1; // Colisión por arriba
        if (colisionAbajo) return 3;   // Colisión por abajo
        if (colisionIzquierda) return 2; // Colisión por la izquierda
        if (colisionDerecha) return 4; // Colisión por la derecha

        return 0; // No hay colisión
    }


    private void verificarDerrota() {
        if (pelota.getY() + pelota.getRadio() > pelota.getAltoPanel()) {
            juego.reducirVida();
            if (juego.estaTerminado()) {
                temporizador.stop();
                cardLayout.show(contenedorPaneles, "Derrota");
            } else {
                // Reiniciar la posición de la pelota
                pelota.setX(panelPelota.getWidth() / 2);
                pelota.setY(panelPelota.getHeight() / 2);
                pelota.setVelocidadY(Math.abs(pelota.getVelocidadY()));
                pelota.setVelocidadX(0);
                barra.mover(350);
            }
        }
    }

    private boolean colisionaConBloque(Bloque bloque) {
        return pelota.getX() + pelota.getRadio() > bloque.getX() &&
                pelota.getX() - pelota.getRadio() < bloque.getX() + bloque.getAncho() &&
                pelota.getY() + pelota.getRadio() > bloque.getY() &&
                pelota.getY() - pelota.getRadio() < bloque.getY() + bloque.getAlto();
    }

    public void iniciar() {
        temporizador.start();
    }

    public void detener() {
        temporizador.stop();
    }
}
