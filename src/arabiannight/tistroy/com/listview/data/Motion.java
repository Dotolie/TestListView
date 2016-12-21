package arabiannight.tistroy.com.listview.data;

import java.io.Serializable;


public class Motion implements Serializable {
	public int no;
	public String title;
	public int image;

	public Sensor Sensor;
	public Motor[] Motors;
	public Sound Sound;
	public Led Led;
	

	public Motion() { }
	
	public Motion(int no, String title, int image, Sensor sensor, Motor[] motors, Sound sound, Led led   ){
		this.no = no;
		this.title = title;
		this.image = image;
		
		this.Sensor = sensor;
		this.Motors = motors;
		this.Sound = sound;
		this.Led = led;
	}

		
}
