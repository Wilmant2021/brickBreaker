package vista;

import modelo.Barra;
import modelo.Bloque;
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
    private List<Bloque> bloques;

    private Image imagenPelota;
    private Image imagenBarra;
    private int anchoImgPelota;
    private int altoImgPelota;
    private int anchoImgBarra;
    private int altoImgBarra;

    public PanelPelota(Pelota pelota, Barra barra, List<Bloque> bloques) {
        this.pelota = pelota;
        this.barra = barra;
        this.bloques = bloques;

        // Cargar la imagen de la pelota
        URL urlImagenPelota = getClass().getClassLoader().getResource("resources/imagenes/pelota.png");
        if (urlImagenPelota != null) {
            imagenPelota = new ImageIcon(urlImagenPelota).getImage();
            imagenPelota = imagenPelota.getScaledInstance(pelota.getRadio()  * 2, pelota.getRadio()  * 2, Image.SCALE_SMOOTH);
            anchoImgPelota = imagenPelota.getWidth(null);
            altoImgPelota = imagenPelota.getHeight(null);
        } else {
            System.err.println("No se pudo cargar la imagen de la pelota");
        }

        // Cargar la imagen de la barra
        URL urlImagenBarra = getClass().getClassLoader().getResource("resources/imagenes/barra1.png");
        if (urlImagenBarra != null) {
            imagenBarra = new ImageIcon(urlImagenBarra).getImage();
            anchoImgBarra = imagenBarra.getWidth(null);
            altoImgBarra = imagenBarra.getHeight(null);
        } else {
            System.err.println("No se pudo cargar la imagen de la barra");
        }

        // Añadir un MouseMotionListener para detectar el movimiento del ratón
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Mover la barra al mover el ratón
                barra.mover(e.getX());
                repaint(); // Redibujar la barra
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // No es necesario en este caso
            }
        });

        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la pelota
        if (imagenPelota != null) {
            g.drawImage(imagenPelota, pelota.getX() - pelota.getRadio(), pelota.getY() - pelota.getRadio(), null);
        }

        // Dibujar la barra usando la imagen
        if (imagenBarra != null) {
            g.drawImage(imagenBarra, barra.getX(), barra.getY(), null);
        }

        // Dibujar los bloques
        g.setColor(Color.GREEN);
        for (Bloque bloque : bloques) {
            if (!bloque.estaDestruido()) {
                g.fillRect(bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto());
            }
        }
    }

    public void actualizarPanel() {
        repaint();
    }
}
