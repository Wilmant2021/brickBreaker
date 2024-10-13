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
    private int anchoImgPelota;
    private int altoImgPelota;
    private int anchoImgBarra;
    private int altoImgBarra;
    private int anchoImgNivel;
    private int altoImgNivel;

    public PanelPelota(Pelota pelota, Barra barra, List<Bloque> bloques, Nivel nivel) {
        this.pelota = pelota;
        this.barra = barra;
        this.nivel = nivel;
        this.bloques = bloques;

        // Cargar la imagen desde el classpath
        URL urlFondo = getClass().getClassLoader().getResource(nivel.getFondo());
        if (urlFondo != null) {
            imagenNivel = new ImageIcon(urlFondo).getImage();
            anchoImgNivel = imagenNivel.getWidth(null);
            altoImgNivel = imagenNivel.getHeight(null);
        } else {
            System.err.println("No se pudo cargar la imagen del fondo");
        }

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

        //Cargar imagen
        if(imagenNivel != null) {
            g.drawImage(imagenNivel, 0, 0, getWidth(), getHeight(), null);
        }

        // Dibujar la pelota
        if (imagenPelota != null) {
            g.drawImage(imagenPelota, pelota.getX() - pelota.getRadio(), pelota.getY() - pelota.getRadio(), null);
        }

        // Dibujar la barra usando la imagen
        if (imagenBarra != null) {
            g.drawImage(imagenBarra, barra.getX(), barra.getY(), null);
        }

        // Dibujar los bloques con sus imágenes correspondientes
        for (Bloque bloque : bloques) {
            if (!bloque.estaDestruido()) {
                // Obtener la imagen del bloque según su id y resistencia
                String rutaImagenBloque = bloque.getImagen(bloque.getIdBloque(), bloque.getResistencia());
                System.out.println("Cargando bloque con id: " + bloque.getIdBloque() + " y resistencia: " + bloque.getResistencia());
                System.out.println("Ruta de imagen: " + rutaImagenBloque);

                URL urlImagenBloque = getClass().getClassLoader().getResource(rutaImagenBloque);

                if (urlImagenBloque != null) {
                    // Cargar la imagen del bloque
                    Image imagenBloque = new ImageIcon(urlImagenBloque).getImage();
                    System.out.println("Imagen cargada correctamente para el bloque");
                    // Prueba dibujando la imagen sin escalado
                    g.drawImage(imagenBloque, bloque.getX(), bloque.getY(), bloque.getAncho(), bloque.getAlto(), null);
                } else {
                    System.err.println("No se pudo cargar la imagen para el bloque con id " + bloque.getIdBloque());
                }
            } else {
                System.out.println("El bloque con id " + bloque.getIdBloque() + " está destruido y no se dibujará.");
            }
        }
    }

    public void actualizarPanel() {
        repaint();
    }




}
