package sensorplot;

import java.util.Random;

public class RandomDataThread extends DataThreadDouble{

	private Random random;
	
	public RandomDataThread(double[] dataSources) {
		super(dataSources);
	}
	
	@Override
	public void onStart() {
		random = new Random();
	}

	@Override
	public void updateDataSources() {
		 getDataSources()[0] = random.nextInt(750);
	}

	@Override
	public DataThreadFactory getDataType() {
		return new RandomDataThreadFactory(dataSources);
	}

}
