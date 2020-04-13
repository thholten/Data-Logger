package sensorplot;

import websockets.DataSocketServer;

public class MouseDataThreadFactory extends DataThreadFactory{

	String dataThread;
	String[] objectNames;
	DataSocketServer dataStream;
	double[] dataSources;

	public MouseDataThreadFactory(double[] dataSources) {
		super();
		this.dataSources = dataSources;
	}


	@Override
	public DataThread createThread(Object[] parameters) {
		return new MouseDataThread(dataSources);
	}


	@Override
	public Object[] getParameters() {
		return null;
	}

}
