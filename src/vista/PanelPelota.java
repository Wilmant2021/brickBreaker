package vista;

import modelo.Barra;
import modelo.Bloque;
import modelo.Nivel;
import modelo.Pelota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.List;

public class PanelPelota extends JPanel {
    private Pelota pelota;
    private Barra barra;
    private Nivel nivel;
    private List<Bloque> bloques;
    private Image imagenPelota;
    private Image imagenBarra;
    private Image imagenNivel;

    public PanelPelota(Pelota pelota, Barra barra, List<Bloque> bloques, Nivel nivel) {
        this.pelota = pelota;
        this.barra = barra;
        this.nivel = nivel;
        this.bloques = bloques;

        // Cargar las imágenes
        cargarImagenNivel(nivel.getFondo());
        cargarImagenPelota("resources/imagenes/pelota.png");
        cargarImagenBarra("resources/imagenes/barra1.png");

        // Añadir un MouseMotionListener para mover la barra con el ratón
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                barra.mover(e.getX());
                repaint(); // Redibujar la barra
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // No se utiliza en este caso
            }
        });

        setPreferredSize(new Dimension(800, 600));
    }

    private void cargarImagenNivel(String fondo) {
        URL urlFondo = getClass().getClassLoader().getResource(fondo);
        if (urlFondo != null) {
            imagenNivel = new ImageIcon(urlFondo).getImage();
        } else {
            System.err.println("No se pudo cargar la imagen del fondo");
        }
    }

    private void cargarImagenPelota(String ruta) {
        URL urlImagenPelota = getClass().getClassLoader().getResource(ruta);
        if (urlImagenPelota != null) {
            imagenPelota = new ImageIcon(urlImagenPelota).getImage();
            imagenPelota = imagenPelota.getScaledInstance(pelota.getRadio() * 2, pelota.getRadio() * 2, Image.SCALE_SMOOTH);
        } else {
            System.err.println("No se pudo cargar la imagen de la pelota");
        }
    }

    private void cargarImagenBarra(String ruta) {
        URL urlImagenBarra = getClass().getClassLoader().getResource(ruta);
        if (urlImagenBarra != null) {
            imagenBarra = new ImageIcon(urlImagenBarra).getImage();
        } else {
            System.err.println("No se pudo cargar la imagen de la barra");
        }
    }

    public void setFondo(String fondo) {
        cargarImagenNivel(fondo);
        repaint(); // Redibujar el panel con el nuevo fondo
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar el fondo del nivel
        if (imagenNivel != null) {
            g.drawImage(imagenNivel, 0, 0, getWidth(), getHeight(), null);
        }

        // Dibujar la pelota
        if (imagenPelota != null) {
            g.drawImage(imagenPelota, pelota.getX() - pelota.getRadio(), pelota.getY() - pelota.getRadio(), null);
        }

        // Dibujar la barra
        if (imagenBarra != null) {
            g.drawImage(imagenBarra, barra.getX(), barra.getY(), null);
        }

        // Dibujar los bloques
        for (Bloque bloque : bloques) {
            if (!bloque.estaDestruido()) {
                String rutaImagenBloque = bloque.getImagen(bloque.getIdBloque(), bloque.getResistencia());
                URL urlImagenBloque = getClass().getClassLoader().getResource(rutaImagenBloque);
                if (urlImagenBloque != null) {
                    Image imagenBloque = new ImageIcon(urlImagenBloque).getImage();
                    g.drawImage(imagenBloque, bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto(), null);
                } else {
                    System.err.println("No se pudo cargar la imagen para el bloque con id " + bloque.getIdBloque());
                }
            }
        }
    }

    public void actualizarPanel() {
        repaint();
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
        repaint(); // Redibujar el panel para mostrar los nuevos bloques
    }
}
