package sensorplot;

import websockets.DataSocketServer;

public class JSONDataThread extends DataThreadDouble {

	DataSocketServer dataStream;
	String[] objectNames;

	public JSONDataThread(double[] dataSources, String[] objectNames, DataSocketServer dataStream) {
		super(dataSources);

		this.dataStream = dataStream;
		this.objectNames = objectNames;
	}

	@Override
	public void onStart() {

	}

	@Override
	public void updateDataSources() {
		for (int i = 0; i < objectNames.length; i++) {
			try {
				//get data from JSON stream
				dataSources[i] = dataStream.getObject().get(objectNames[i]).getAsDouble();
			} catch (Exception e) {
				System.out.println("No data");
			}

		}
	}

	@Override
	public DataThreadFactory getDataType() {
		return new JSONDataThreadFactory(dataSources, objectNames, dataStream);
	}

}
