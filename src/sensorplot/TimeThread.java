package sensorplot;

public class TimeThread extends Thread {

	private long time = 0;

	private int tick = 0;

	private boolean running;

	public TimeThread(int tick) {
		this.tick = tick;
	}

	public void run() {
		setRunning(true);
		setPriority(10);
		updateTime();
	}

	public long getTime() {
		return this.time;
	}

	public synchronized void updateTime() {
		while (getRunning()) {
			time += tick;
			try {
				Thread.sleep(tick);
			} catch (Exception e) {
			}
		}
		
	}

	public int getTick() {
		return tick;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean getRunning() {
		return running;
	}


}
