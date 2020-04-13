package sensorplot;

public abstract class DataThreadDouble extends DataThread {

	protected double[] dataSources;
	private String name;
	private TimeThread timeThread;
	private boolean running;
	

	public DataThreadDouble(double[] dataSources) {
		this.dataSources = dataSources;
		onStart();
	}

	public abstract void onStart();

	public void run() {
		updateDataSources();
	}

	public double[] getDataSources() {
		return dataSources;
	}

	public void logName() {
		System.out.println("Tracking " + name + "Data on Thread " + this.getId());
	}

	public String toString() {
		return name;
	}
	
	public abstract DataThreadFactory getDataType();

	public TimeThread getTimeThread() {
		return timeThread;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean getRunning() {
		return running;
	}

	public abstract void updateDataSources();
	
	

}
