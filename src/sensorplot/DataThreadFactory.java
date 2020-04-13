package sensorplot;

import websockets.DataSocketServer;

public abstract class DataThreadFactory {

	String[] objectNames;
	DataSocketServer dataStream;

	public DataThreadFactory() {

	}


	public abstract DataThread createThread(Object[] parameters);

	public abstract Object[] getParameters();
}
