package controlador;

import modelo.*;
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
    private Nivel nivel;
    private List<Bloque> bloques;
    private PanelPelota panelPelota;
    private Timer temporizador;
    private Sonido sonidoRebote;
    private Sonido sonidoPartida;
    private Juego juego;
    private CardLayout cardLayout;
    private JPanel contenedorPaneles;

    public ControladorPelota(Pelota pelota, Barra barra, PanelPelota panelPelota, Juego juego, CardLayout cardLayout, JPanel contenedorPaneles, Nivel nivel) {
        this.pelota = pelota;
        this.barra = barra;
        this.nivel = nivel;
        this.panelPelota = panelPelota;
        this.juego = juego;
        this.cardLayout = cardLayout;
        this.contenedorPaneles = contenedorPaneles;
        pelota.setVelocidadY(nivel.getVelocidadPelota());
        this.bloques = nivel.getBloques();
        this.sonidoPartida = new Sonido(nivel.getMusicaFondo());
        this.sonidoRebote = new Sonido("resources/sonidos/rebotar.wav");
        inicializarTemporizador();
    }

    private void inicializarTemporizador() {
        temporizador = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pelota.mover();
                sonidoPartida.reproducirMusica();
                verificarColision();
                verificarDerrota();
                verificarVictoria();
                panelPelota.actualizarPanel();
            }
        });
    }

    private void verificarColision() {
        verificarColisionConBarra();
        verificarColisionConBloques();
    }

    private void verificarColisionConBarra() {
        if (pelota.getY() + pelota.getRadio() >= barra.getY() &&
                pelota.getY() - pelota.getRadio() <= barra.getY() + barra.getAlto() &&
                pelota.getX() + pelota.getRadio() >= barra.getX() &&
                pelota.getX() - pelota.getRadio() <= barra.getX() + barra.getAncho()) {

            int puntoImpacto = pelota.getX() - barra.getX();
            int mitadAncho = barra.getAncho() / 2;

            int nuevaVelocidadX = (puntoImpacto - mitadAncho) / (barra.getAncho() / 5);
            pelota.setVelocidadX(nuevaVelocidadX);
            pelota.rebotarVerticalmente();
            sonidoRebote.reproducir();
        }
    }

    private void verificarColisionConBloques() {
        for (int i = 0; i < bloques.size(); i++) {
            Bloque bloque = bloques.get(i);
            if (colisionaConBloque(bloque)) {
                manejarColisionConBloque(bloque);
                break; // Salir del bucle después de una colisión
            }
        }
    }

    private void manejarColisionConBloque(Bloque bloque) {
        int colisionLado = detectarLadoColision(bloque);

        switch (colisionLado) {
            case 1:
            case 3:
                pelota.rebotarVerticalmente();
                break;
            case 2:
                pelota.setX(bloque.getX() - pelota.getRadio());
                pelota.rebotarHorizontalmente();
                break;
            case 4:
                pelota.setX(bloque.getX() + bloque.getAncho() + pelota.getRadio());
                pelota.rebotarHorizontalmente();
                break;
        }

        bloque.reducirResistencia();
        if (bloque.estaDestruido()) {
            bloques.remove(bloque);
            juego.incrementarPuntuacion(100);
        }

        sonidoRebote.reproducir();
    }

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

        if (colisionArriba) return 1;
        if (colisionAbajo) return 3;
        if (colisionIzquierda) return 2;
        if (colisionDerecha) return 4;

        return 0;
    }

    private void verificarDerrota() {
        if (pelota.getY() + pelota.getRadio() > pelota.getAltoPanel()) {
            juego.reducirVida();
            if (juego.estaTerminado()) {
                temporizador.stop();
                sonidoPartida.detener(); // Detener música de fondo

                cardLayout.show(contenedorPaneles, "Derrota");
                JOptionPane.showMessageDialog(null, "¡Lo siento, perdiste!", "Derrota", JOptionPane.INFORMATION_MESSAGE);

                int opcion = JOptionPane.showConfirmDialog(null,
                        "¿Quieres volver al menú principal?",
                        "Regresar", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    reiniciarJuegoCompleto();
                } else {
                    mostrarPanelFinalJuego();
                }
            } else {
                reiniciarPelota();
            }
        }
    }


    private void verificarVictoria() {
        if (nivel.nivelCompletado()) {
            temporizador.stop(); // Detener el temporizador

            // Comprobar si el índice del nivel actual es el último
            if (juego.getNivelActual().getIndiceNivel() == juego.getNiveles().size() || nivel.getIndiceNivel()==juego.getNiveles().size() ) {
                // Si es el último nivel, mostrar un mensaje de victoria final
                JOptionPane.showMessageDialog(null, "¡Ganaste el juego! Felicitaciones.", "Victoria", JOptionPane.INFORMATION_MESSAGE);
                // Mostrar opción para regresar al menú principal
                int opcion = JOptionPane.showConfirmDialog(null,
                        "¿Quieres volver al menú principal?",
                        "Regresar", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    nivel.reiniciarNivel();
                    finalizarJuego();
                }else{
                    mostrarPanelFinalJuego();
                }
            } else {
                // Si no es el último nivel, preguntar si quiere avanzar
                int opcion = JOptionPane.showConfirmDialog(null,
                        "¡Ganaste! ¿Quieres pasar al siguiente nivel?",
                        "Victoria", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    avanzarAlSiguienteNivel();
                } else {
                    nivel.reiniciarNivel();
                    finalizarJuego();

                }
            }
        }
    }

    private void avanzarAlSiguienteNivel() {
        nivel.reiniciarNivel();
        juego.setVidas(3);
        int nivelActual = nivel.getIndiceNivel();
        nivel.setIndiceNivel(nivelActual+1);// Avanza al siguiente nivel
        nivel.cargarNivel();
        bloques = nivel.getBloques(); // Actualiza la lista de bloques
        panelPelota.setFondo(nivel.getFondo()); // Cambia el fondo del panel
        sonidoPartida.cambiarSonido(nivel.getMusicaFondo()); // Cambia la música del fondo
        reiniciarPelota(); // Reinicia la posición de la pelota
        pelota.setVelocidadY(nivel.getVelocidadPelota());
        panelPelota.setBloques(bloques); // Actualiza los bloques en el panel
        panelPelota.repaint(); // Redibuja el panel
        iniciar(); // Inicia el nuevo nivel
    }

    private void finalizarJuego() {
        sonidoPartida.detener(); // Detiene el sonido
        cardLayout.show(contenedorPaneles, "Inicio"); // Regresa al menú principal

    }


    private void mostrarPanelFinalJuego() {
        JPanel finalJuego = new JPanel();
        finalJuego.setLayout(new BorderLayout());

        JLabel textoFinalJuego = new JLabel("¡GRACIAS POR JUGAR!", JLabel.CENTER);
        finalJuego.add(textoFinalJuego, BorderLayout.CENTER);

        // Agregar un botón para salir del juego
        JButton salirButton = new JButton("Salir");
        salirButton.addActionListener(e -> System.exit(0)); // Salir del juego
        finalJuego.add(salirButton, BorderLayout.SOUTH);

        // Crear un JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Fin del Juego");
        dialog.setModal(true); // Modal para que el usuario deba interactuar con el diálogo
        dialog.setContentPane(finalJuego);
        dialog.pack(); // Ajusta el tamaño del diálogo al contenido
        dialog.setLocationRelativeTo(null); // Centrar el diálogo en la pantalla
        dialog.setVisible(true); // Mostrar el diálogo
    }



    private boolean colisionaConBloque(Bloque bloque) {
        return pelota.getX() + pelota.getRadio() > bloque.getX() &&
                pelota.getX() - pelota.getRadio() < bloque.getX() + bloque.getAncho() &&
                pelota.getY() + pelota.getRadio() > bloque.getY() &&
                pelota.getY() - pelota.getRadio() < bloque.getY() + bloque.getAlto();
    }

    private void reiniciarPelota() {
        pelota.setX(panelPelota.getWidth() / 2);
        pelota.setY(panelPelota.getHeight() / 2);
        pelota.setVelocidadY(Math.abs(pelota.getVelocidadY()));
        pelota.setVelocidadX(0);
        barra.mover(350);
    }

    private void reiniciarJuegoCompleto() {
        nivel.setIndiceNivel(1); // Reiniciar al primer nivel
        juego.setVidas(3);
        nivel.cargarNivel(); // Recargar los datos del nivel
        bloques = nivel.getBloques(); // Actualizar los bloques del nivel inicial

        panelPelota.setBloques(bloques); // Actualizar bloques en el panel
        panelPelota.setFondo(nivel.getFondo()); // Restaurar el fondo

        reiniciarPelota(); // Reiniciar posición de la pelota
        sonidoPartida.cambiarSonido(nivel.getMusicaFondo()); // Cambiar a la música del nivel inicial

        finalizarJuego(); // Volver al menú principal
    }


    public void iniciar() {
        temporizador.start();
    }

    public void detener() {
        temporizador.stop();
    }
}
