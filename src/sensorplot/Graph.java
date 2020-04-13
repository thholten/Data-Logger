package sensorplot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph {

	protected JFreeChart chart;
	protected XYSeriesCollection dataset = new XYSeriesCollection();
	protected double domainUnit;
	
	private Window window;

	public Graph(Window window, String[] seriesNames, String chartTitle, String xAxisLabel, String yAxisLabel, double domainUnit) {

		this.window = window;
		for (String s : seriesNames) {
			dataset.addSeries(new XYSeries(s));
		}

		this.chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset,
				PlotOrientation.VERTICAL, true, false, false);

		NumberAxis xAxis = new NumberAxis();
		xAxis.setTickUnit(new NumberTickUnit(domainUnit));
		chart.getXYPlot().setDomainAxis(xAxis);
		
	}

	public JFreeChart getChart() {
		return this.chart;
	}

	public void populate(int seriesNumber, double x, double y) {
		dataset.getSeries(seriesNumber).add(x, y);
	}

	public Window getWindow() {
		return window;
	}
	
	public XYSeriesCollection getDataset() {
		return dataset;
	}
}
