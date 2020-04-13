package sensorplot;

import java.awt.MouseInfo;

public class MouseDataThread extends DataThreadDouble{

	public MouseDataThread(double[] dataSources) {
		super(dataSources);
	}

	@Override
	public void onStart() {

	}
	
	@Override
	public void updateDataSources() {
		getDataSources()[0] = MouseInfo.getPointerInfo().getLocation().getX();
		getDataSources()[1] = MouseInfo.getPointerInfo().getLocation().getY();
	}

	@Override
	public DataThreadFactory getDataType() {
		return new MouseDataThreadFactory(dataSources);
	}




}
