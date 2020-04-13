package sensorplot;


// creates new data threads using specified parameters (defined as needed in subclasses)
public abstract class DataThreadFactory {

	String[] objectNames;

	public DataThreadFactory() {

	}


	public abstract DataThread createThread(Object[] parameters);

	public abstract Object[] getParameters();
}
