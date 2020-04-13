//package main;
//
//import java.awt.BorderLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//
//import org.jfree.chart.ChartPanel;
//
//import sensorplot.DataThread;
//import sensorplot.DataThreadDouble;
//import sensorplot.DataThreadFactory;
//import sensorplot.LiveGraph;
//import sensorplot.MouseDataThreadFactory;
//import sensorplot.RandomDataThreadFactory;
//import sensorplot.Window;
//
//public class TestDataGraph {
//
//	static double x = 0;
//	static boolean isStarted = false;
//
//	public static void main(String[] args) throws InterruptedException {
//
//		Window window = new Window(750, 900, "Sensor Data", new BorderLayout());
//
//		JButton start = new JButton("Start");
//
//		LiveGraph mouseGraph = new LiveGraph(window, new String[] { "Mouse X", "Mouse Y" }, "Mouse Position",
//				"Time (seconds)", "Position (px)", 1.0, 10, 10);
//
//		LiveGraph randomGraph = new LiveGraph(window, new String[] { "Random" }, "Random Data", "Time (seconds)",
//				"Random Number", 0.5, 10, 1000);
//
//		LiveGraph[] liveGraphs = {mouseGraph, randomGraph };
//
//		window.getWindow().add(new ChartPanel(mouseGraph.getChart()), BorderLayout.CENTER);
//		window.getWindow().add(new ChartPanel(randomGraph.getChart()), BorderLayout.AFTER_LAST_LINE);
//
//		DataThreadFactory mouseType = new MouseDataThreadFactory();
//		DataThreadFactory randomType = new RandomDataThreadFactory();
//
//		start.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				if (start.getText().equals("Start")) {
//					start.setText("Stop");
//					isStarted = true;
//
//				} else {
//					isStarted = false;
//					start.setText("Start");
//				}
//			}
//
//		});
//
//		window.getWindow().add(start, BorderLayout.EAST);
//		window.show();
//
//		DataThreadFactory[] dataThreadFactories = new DataThreadFactory[] {mouseType, randomType };
//		
//		DataThread[] dataThreads = initDataThreads(dataThreadFactories);
//		long prevTime = System.currentTimeMillis(), startTime = System.currentTimeMillis();
////		timer.start();
//		while (System.currentTimeMillis() - startTime < 60000) {
//			long currentTime = System.currentTimeMillis();
//			if (currentTime - prevTime > 10) {
//				for (int i = 0; i < dataThreadFactories.length; i++) {
//					liveGraphs[i].updateDomain((currentTime - startTime) / 1000.0);
//					if (!dataThreads[i].isAlive()) {
//						dataThreads[i] = dataThreads[i].getDataType().createThread(null);
//						dataThreads[i].start();
//						double[] dataSources = dataThreads[i].getDataSources();
//						for (int j = 0; j < dataSources.length; j++) {
//							liveGraphs[i].populate(j, (currentTime - startTime) / 1000.0, dataSources[j]);
//						}
//					}
//
//				}
//				window.getWindow().repaint();
//				prevTime = System.currentTimeMillis();
//				Thread.sleep(1);
//			}
//
////					if (!liveGraph.getWindow().getHasBeenReDrawn() && currentTime == timeThread.getTime()) {
////						liveGraph.getWindow().getWindow().repaint();
////						liveGraph.getWindow().setHasBeenReDrawn(true);
////					}
//		}
//		
//		window.show();
//	}
//
//	private static DataThread[] initDataThreads(DataThreadFactory[] dataThreadFactories) {
//		DataThread[] dataThreads = new DataThreadDouble[dataThreadFactories.length];
//		for (int i = 0; i < dataThreadFactories.length; i++) {
//			dataThreads[i] = dataThreadFactories[i].createThread(null);
//			dataThreads[i].start();
//		}
//
//		return dataThreads;
//	}
//}