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
		domainAxis.setAutoRange(false);
		domainAxis.setRange(0, domainLength);
	}

	public void populate(int seriesNumber, double x, double y) {
		dataset.getSeries(seriesNumber).add(this.x, y);
	}

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

	public double updateX() {
		x += tick / 1000.0; // tick in milliseconds
		return x;
	}
	
	public String toString() {
		return this.chart.getTitle().getText();
	}

}
