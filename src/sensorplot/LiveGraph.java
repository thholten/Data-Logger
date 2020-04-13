package sensorplot;

import org.jfree.chart.axis.ValueAxis;


public class LiveGraph extends Graph {

	private long tick;
	private double x = 0;

	public LiveGraph(Window window, String[] seriesNames, String chartTitle, String xAxisLabel, String yAxisLabel,
			double domainUnit, double domainLength, long tick) {
		super(window, seriesNames, chartTitle, xAxisLabel, yAxisLabel, domainUnit);

		this.tick = tick;

		ValueAxis domainAxis = chart.getXYPlot().getDomainAxis();
		
		//allows setting of domain manually for live data stream
		//TODO: add an option to keep whole domain on screen
		domainAxis.setAutoRange(false);
		
		domainAxis.setRange(0, domainLength);
	}

	//add data point to graph
	public void populate(int seriesNumber, double x, double y) {
		dataset.getSeries(seriesNumber).add(this.x, y);
	}

	//move domain over if data is added outside of current domain
	public void updateDomain(double x) {
		double prevX = this.x;
		setX(x);
		double entryDifference = this.x - prevX;
		ValueAxis domainAxis = chart.getXYPlot().getDomainAxis();
		if (domainAxis.getUpperBound() - 1 <= x) {
			double currentMax = domainAxis.getUpperBound();
			double currentMin = domainAxis.getLowerBound();

			domainAxis.setRange(currentMin + entryDifference, currentMax + entryDifference);
		}

	}

	public long getTick() {
		return tick;
	}


	public void setX(double x) {
		this.x = x;
	}
	
	
	public String toString() {
		return this.chart.getTitle().getText();
	}

}
