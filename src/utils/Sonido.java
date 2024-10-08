package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Sonido {
    private Clip clip;

    public Sonido(String archivoSonido) {
        try {
            // Usa getResource para obtener la ruta relativa desde el classpath
            URL url = getClass().getClassLoader().getResource(archivoSonido);
            if (url == null) {
                System.err.println("No se encontró el archivo de sonido: " + archivoSonido);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void reproducir() {
        if (clip != null) {
            clip.setFramePosition(0); // Reinicia el sonido desde el principio
            clip.start();
        }
    }

    public void reproducirMusica(){
        if (clip != null) {
            clip.start();
        }
    }

    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir el sonido continuamente
            clip.start();
        }
    }
}