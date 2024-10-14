package utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sonido {
    private Clip clip;
    private FloatControl volumeControl;

    public Sonido(String archivoSonido) {
        cargarSonido(archivoSonido);
    }

    private void cargarSonido(String archivoSonido) {
        try {
            URL url = getClass().getClassLoader().getResource(archivoSonido);
            if (url == null) {
                System.err.println("No se encontró el archivo de sonido: " + archivoSonido);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            if (clip != null && clip.isOpen()) {
                clip.close(); // Cierra el clip anterior si está abierto
            }
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void reproducir() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void reproducirMusica() {
        if (clip != null) {
            clip.start();
        }
    }

    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    public void pausar() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void detener() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    public void setVolume(float value) {
        if (volumeControl != null) {
            if (value < volumeControl.getMinimum()) {
                value = volumeControl.getMinimum();
            }
            if (value > volumeControl.getMaximum()) {
                value = volumeControl.getMaximum();
            }
            volumeControl.setValue(value);
        }
    }

    public void cambiarSonido(String nuevoArchivoSonido) {
        cargarSonido(nuevoArchivoSonido);
    }
}
