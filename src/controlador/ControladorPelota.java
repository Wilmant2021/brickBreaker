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

        sonidoRebote = new Sonido("resources/sonidos/rebotar.wav");

        temporizador = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pelota.mover();
                verificarColision();
                verificarDerrota();
                panelPelota.actualizarPanel();
            }
        });
    }

    private void verificarColision() {
        // Colisión con la barra
        if (pelota.getY() + pelota.getRadio() >= barra.getY() &&
                pelota.getX() >= barra.getX() &&
                pelota.getX() <= barra.getX() + barra.getAncho()) {
            pelota.rebotarVerticalmente();
            sonidoRebote.reproducir();
        }

        // Colisión con los bloques
        for (Bloque bloque : bloques) {
            if (colisionaConBloque(bloque)) {
                bloques.remove(bloque);
                pelota.rebotarVerticalmente();
                juego.incrementarPuntuacion(100);  // Aumentar puntuación al romper un bloque
                sonidoRebote.reproducir();
                break;
            }
        }
    }

    private void verificarDerrota() {
        if (pelota.getY() + pelota.getRadio() > pelota.getAltoPanel()) {
            juego.reducirVida();
            if (juego.estaTerminado()) {
                temporizador.stop();
                cardLayout.show(contenedorPaneles, "Derrota");
            } else {
                // Reiniciar la posición de la pelota
                pelota.setX(100);
                pelota.setY(100);
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
