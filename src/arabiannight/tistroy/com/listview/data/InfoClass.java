package arabiannight.tistroy.com.listview.data;

import android.graphics.drawable.Drawable;


public class InfoClass {
	public String no;
	public String title;
	public Drawable image;
	public Sensor mSensor;
	public Motor mMotor1;
	public Motor mMotor2;
	public Motor mMotor3;
	public Motor mMotor4;
	public Sound mSound;
	public Led mLed;
	

	public InfoClass() { }
	
	public InfoClass(String title, Drawable image, String no){
		this.no = no;
		this.title = title;
		this.image = image;
		
		this.mMotor1 = new Motor();
		this.mMotor2 = new Motor();
		this.mMotor3 = new Motor();
		this.mMotor4 = new Motor();
		
		this.mSound = new Sound();
		this.mLed = new Led();
		
		this.mMotor1.angle_default = 5;
		this.mMotor1.angle_start = 0;
		this.mMotor1.angle_end = 10;
		this.mMotor1.delay_time = 100;
		this.mMotor1.hold_time = 25;
		this.mMotor1.repeat_count = 5;

		this.mMotor2.angle_default = 5;
		this.mMotor2.angle_start = 0;
		this.mMotor2.angle_end = 10;
		this.mMotor2.delay_time = 100;
		this.mMotor2.hold_time = 25;
		this.mMotor2.repeat_count = 5;
		
		this.mMotor3.angle_default = 5;
		this.mMotor3.angle_start = 0;
		this.mMotor3.angle_end = 10;
		this.mMotor3.delay_time = 100;
		this.mMotor3.hold_time = 25;
		this.mMotor3.repeat_count = 5;
		
		this.mMotor4.angle_default = 5;
		this.mMotor4.angle_start = 0;
		this.mMotor4.angle_end = 10;
		this.mMotor4.delay_time = 100;
		this.mMotor4.hold_time = 25;
		this.mMotor4.repeat_count = 5;

		this.mSound.index = 0;
		this.mSound.delay_time = 100;
		
		this.mLed.blink = 1;
		this.mLed.delay_time = 120;
	}
	
	public class Sensor {
		public byte type;
		public byte value;
	}
	public class Motor {
		public byte angle_default;
		public byte angle_start;
		public byte angle_end;
		public byte delay_time;
		public byte hold_time;
		public byte repeat_count;
	}
	public class Sound {
		public byte index;
		public byte delay_time;
	}
	public class Led {
		public byte blink;
		public byte delay_time;
	}	
}
