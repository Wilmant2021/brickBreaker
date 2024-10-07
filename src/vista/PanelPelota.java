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
    private int anchoImg;
    private int altoImg;

    public PanelPelota(Pelota pelota, Barra barra,List<Bloque> bloques) {
        this.pelota = pelota;
        this.barra = barra;
        this.bloques =  bloques;


        // Cargar la imagen desde el classpath
        URL urlImagen = getClass().getClassLoader().getResource("resources/imagenes/pelota.png");

        if (urlImagen != null) {
            imagenPelota = new ImageIcon(urlImagen).getImage();
            anchoImg = imagenPelota.getWidth(null);
            altoImg = imagenPelota.getHeight(null);
        } else {
            System.err.println("No se pudo cargar la imagen de la pelota");
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
        g.setColor(Color.RED);

        //g.fillOval(pelota.getX() - pelota.getRadio(), pelota.getY() - pelota.getRadio(), pelota.getRadio() * 2, pelota.getRadio() * 2);
        if (imagenPelota != null) {
            g.drawImage(imagenPelota, pelota.getX(), pelota.getY() , null);
        }

        // Dibujar la barra
        g.setColor(Color.BLUE);
        g.fillRect(barra.getX(), barra.getY(), barra.getAncho(), barra.getAlto());


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