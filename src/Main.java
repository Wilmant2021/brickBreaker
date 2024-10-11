import controlador.ControladorPelota;
import modelo.*;
import vista.PanelPelota;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame marco = new JFrame("Brick Breaker");
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setSize(800, 600);

        // Layout para gestionar las pantallas
        CardLayout cardLayout = new CardLayout();
        JPanel contenedorPaneles = new JPanel(cardLayout);

        // Pantalla de inicio
        JPanel pantallaInicio = new JPanel();
        pantallaInicio.setLayout(new BorderLayout());
        JButton botonIniciar = new JButton("Iniciar Juego");
        pantallaInicio.add(botonIniciar, BorderLayout.CENTER);
        JLabel etiquetaPuntaje = new JLabel("Puntaje: 0");
        JLabel etiquetaVidas = new JLabel("Vidas: " + Juego.getVidas());

        // Crear el juego
        int ancho = 800;
        int alto = 600;
        int vidas = 3;

        //Crear niveles
        Nivel nivel = new Nivel(2, 0, vidas, 6);

        // Barra
        Barra barra = new Barra(350, alto-70,100, 10, ancho);

        // Ajustar la posición inicial de la pelota en relación con la barra
        int radioPelota = 10;
        int posicionXPelota = barra.getX() + (barra.getAncho() / 2) - radioPelota;  // Centrar la pelota en la barra
        int posicionYPelota = barra.getY() - radioPelota - 5;  // Colocar la pelota justo encima de la barra

        Pelota pelota = new Pelota(posicionXPelota, posicionYPelota, radioPelota, 5, 5, ancho, alto);
        List<Bloque> bloques = nivel.getBloques();
        Juego juego = new Juego(vidas, bloques);

        PanelPelota panelPelota = new PanelPelota(pelota, barra, bloques, nivel);
        ControladorPelota controlador = new ControladorPelota(pelota, barra, panelPelota, juego, cardLayout, contenedorPaneles, nivel);

        // Pantalla de victoria
        JPanel pantallaVictoria = new JPanel();
        pantallaVictoria.setLayout(new BorderLayout());
        JLabel textoVictoria = new JLabel("¡Victoria!", JLabel.CENTER);
        JButton botonReiniciar = new JButton("Reiniciar");
        pantallaVictoria.add(textoVictoria, BorderLayout.CENTER);
        pantallaVictoria.add(botonReiniciar, BorderLayout.SOUTH);

        // Pantalla de derrota
        JPanel pantallaDerrota = new JPanel();
        pantallaDerrota.setLayout(new BorderLayout());
        JLabel textoDerrota = new JLabel("GAME OVER ", JLabel.CENTER);
        pantallaDerrota.add(textoDerrota, BorderLayout.CENTER);

        // Añadir los paneles al contenedor
        contenedorPaneles.add(pantallaInicio, "Inicio");
        contenedorPaneles.add(panelPelota, "Juego");
        contenedorPaneles.add(pantallaVictoria, "Victoria");
        contenedorPaneles.add(pantallaDerrota, "Derrota");

        marco.add(contenedorPaneles);
        marco.setVisible(true);

        // Acción al hacer clic en "Iniciar Juego"
        botonIniciar.addActionListener(e -> {
            controlador.iniciar();
            cardLayout.show(contenedorPaneles, "Juego");
        });

        // Acción al hacer clic en "Reiniciar"
        botonReiniciar.addActionListener(e -> {
            reiniciarJuego(pelota, barra, bloques, controlador, panelPelota, nivel);
            cardLayout.show(contenedorPaneles, "Juego");
        });
    }

    private static void reiniciarJuego(Pelota pelota, Barra barra, List<Bloque> bloques, ControladorPelota controlador, PanelPelota panelPelota, Nivel nivel) {
        int anchoPanel = 800; // Ancho del panel
        int altoPanel = 600;  // Alto del panel

        int radioPelota = pelota.getRadio();
        int posicionXPelota = (anchoPanel / 2) - radioPelota;  // Centrar la pelota en el ancho
        int posicionYPelota = (altoPanel / 2) - radioPelota;   // Centrar la pelota en el alto

        pelota.setX(posicionXPelota);
        pelota.setY(posicionYPelota);

        // Opcional: ajustar la velocidad de la pelota si es necesario
        // pelota.setVelocidadX(5);
        // pelota.setVelocidadY(5);

        barra.mover(350);

        bloques.clear();
        bloques.addAll(nivel.getBloques());

        controlador.iniciar();
        panelPelota.actualizarPanel();
    }
}