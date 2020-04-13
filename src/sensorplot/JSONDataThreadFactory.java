package sensorplot;

import websockets.DataSocketServer;

public class JSONDataThreadFactory extends DataThreadFactory{

	String[] objectNames;
	DataSocketServer dataStream;
	double[] dataSources;

	public JSONDataThreadFactory(double[] dataSources, String[] objectNames, DataSocketServer dataStream) {
		super();
		this.objectNames = objectNames;
		this.dataStream = dataStream;
		this.dataSources = dataSources;
	}

	public DataThread createThread(Object[] parameters) {
			return new JSONDataThread((double[]) parameters[0], (String[])parameters[1], (DataSocketServer)parameters[2]);
	}

	@Override
	public Object[] getParameters() {
		return new Object[] {dataSources, objectNames, dataStream};
	}

}
