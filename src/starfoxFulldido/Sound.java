package starfoxFulldido;

import javax.sound.sampled.*;
import java.io.InputStream;

public class Sound {

    public static void play(String path, float volume) {
        new Thread(() -> {
            try {
                InputStream audioSrc = Sound.class.getResourceAsStream("/" + path);

                if (audioSrc == null) {
                    System.out.println("ERRO: som nao encontrado: " + path);
                    return;
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);

                FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gain.setValue(volume); 

                clip.start();

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}