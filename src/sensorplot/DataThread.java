package sensorplot;

public abstract class DataThread extends Thread{

	public abstract DataThreadFactory getDataType();

	public abstract double[] getDataSources();

}
