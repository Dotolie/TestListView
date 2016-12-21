package arabiannight.tistroy.com.listview.data;

import java.io.Serializable;

public class Sound implements Serializable {
	public byte type;
	public byte delay_time;
	
	public Sound() {}
	
	public Sound(int type, int delay) {
		this.type = (byte)type;
		this.delay_time = (byte)delay;
	}
}
