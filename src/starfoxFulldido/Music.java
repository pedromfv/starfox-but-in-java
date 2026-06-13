package starfoxFulldido;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music {

	private static Clip clip;
	
	public static void playLoop(String path) {
		try {
			InputStream audioSrc = Music.class.getResourceAsStream("/corneria.wav");

			if (audioSrc == null) {
			    System.out.println("ERRO: arquivo nao encontrado: " + path);
			    return;
			}
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(5.0f);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void stop() {
		if(clip != null) {
			clip.stop();
			clip.close();
		}
	}
	
}
