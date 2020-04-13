package sensorplot;


public class RandomDataThreadFactory extends DataThreadFactory{

	double[] dataSources;
	
	public RandomDataThreadFactory(double[] dataSources) {
		super();
		this.dataSources = dataSources;
	}




	@Override
	public DataThread createThread(Object[] parameters) {
		return new RandomDataThread(dataSources);
	}




	@Override
	public Object[] getParameters() {
		return null;
	}

}
