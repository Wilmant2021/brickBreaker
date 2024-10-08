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
        // Primero, verificar colisión con la barra
        if (pelota.getY() + pelota.getRadio() >= barra.getY() &&
                pelota.getX() + pelota.getRadio() >= barra.getX() &&
                pelota.getX() - pelota.getRadio() <= barra.getX() + barra.getAncho()) {

            // Verifica si la pelota golpea el borde izquierdo o derecho de la barra
            if (pelota.getX() < barra.getX() || pelota.getX() > barra.getX() + barra.getAncho()) {
                pelota.rebotarHorizontalmente();
            } else {
                pelota.rebotarVerticalmente();
                // Ajusta la posición de la pelota para evitar que se desplace sobre la barra
                pelota.setY(barra.getY() - pelota.getRadio()); // Coloca la pelota justo arriba de la barra
            }

            sonidoRebote.reproducir();
        }

        // Luego, verificar colisión con los bloques
        for (Bloque bloque : bloques) {
            if (colisionaConBloque(bloque)) {
                bloques.remove(bloque);
                pelota.rebotarVerticalmente(); // Asegúrate de que aquí sea verticalmente
                juego.incrementarPuntuacion(100);
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
