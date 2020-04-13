package main;

import java.awt.BorderLayout;
import org.jfree.chart.ChartPanel;

import sensorplot.DataThread;
import sensorplot.DataThreadFactory;
import sensorplot.JSONDataThread;
import sensorplot.JSONDataThreadFactory;
import sensorplot.LiveGraph;
import sensorplot.MyLock;

import sensorplot.Window;
import websockets.DataSocketServer;

public class PhoneDataGraph {

	static double x = 0;
	static boolean isStarted = false;

	public static void main(String[] args) throws InterruptedException {

		Window window = new Window(1500, 800, "Sensor Data", new BorderLayout());

		// Create JFreeChart Graphs for Accelerometer, Gyro, and Magnetometer Data
		LiveGraph accGraph = new LiveGraph(window, new String[] { "AccX", "AccY", "AccZ" }, "Acceleration",
				"Time (seconds)", "Units", 1.0, 10, 10);

		LiveGraph gyroGraph = new LiveGraph(window, new String[] { "GyroX", "GyroY", "GyroZ" }, "Gyro",
				"Time (seconds)", "Units", 1.0, 10, 10);

		LiveGraph magGraph = new LiveGraph(window, new String[] { "MagX", "MagY", "MagZ" }, "Magnetometer",
				"Time (seconds)", "Units", 1.0, 10, 10);

		LiveGraph[] liveGraphs = { accGraph, gyroGraph, magGraph };

		// create lock object for synchronization
		MyLock lock = new MyLock();

		ChartPanel[] panels = new ChartPanel[liveGraphs.length];
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new ChartPanel(liveGraphs[i].getChart());
			panels[i].setSize(500, 400);
		}

		window.getWindow().add(panels[0], BorderLayout.WEST);
		window.getWindow().add(panels[1], BorderLayout.SOUTH);
		window.getWindow().add(panels[2], BorderLayout.EAST);

		DataSocketServer dataServer = new DataSocketServer(lock);

		dataServer.start();

		// the JSON fields we will be collecting data from for each chart
		// this sensor data is logged by SensorLog app on iPhone
		// https://apps.apple.com/us/app/sensorlog/id388014573)
		String[] accNames = new String[] { "accelerometerAccelerationX", "accelerometerAccelerationY",
				"accelerometerAccelerationZ" };

		String[] gyroNames = new String[] { "gyroRotationX", "gyroRotationY", "gyroRotationZ" };

		String[] magNames = new String[] { "magnetometerX", "magnetometerY", "magnetometerZ" };

		// the destinations for the collected data
		double[] accSources = new double[3];
		double[] gyroSources = new double[3];
		double[] magSources = new double[3];

		// initialize data sources
		DataThreadFactory accFactory = new JSONDataThreadFactory(accSources, accNames, dataServer);
		DataThreadFactory gyroFactory = new JSONDataThreadFactory(gyroSources, gyroNames, dataServer);
		DataThreadFactory magFactory = new JSONDataThreadFactory(magSources, magNames, dataServer);

		// wait for web socket to establish connection
		synchronized (lock) {
			lock.wait();
		}

		window.show();

		DataThreadFactory[] dataThreadFactories = new DataThreadFactory[] { accFactory, gyroFactory, magFactory };

		// initialize dataThreads with an object from their factories
		DataThread[] dataThreads = initDataThreads(dataThreadFactories);

		long prevTime = System.currentTimeMillis(), startTime = System.currentTimeMillis();
		while (true) {
			long currentTime = System.currentTimeMillis();

			// update data every 50 ms
			if (currentTime - prevTime > 50) {
				for (int i = 0; i < dataThreadFactories.length; i++) {
					liveGraphs[i].updateDomain((currentTime - startTime) / 1000.0);

					// if dataThreads aren't still active from previous iteration, start them again
					if (!dataThreads[i].isAlive()) {
						dataThreads[i] = dataThreadFactories[i].createThread(dataThreadFactories[i].getParameters());
						dataThreads[i].start();
						try {
							// get data from the dataThreads
							double[] currentDataSources = dataThreads[i].getDataSources();

							// populate each graph with the new data
							for (int j = 0; j < accSources.length; j++) {
								liveGraphs[i].populate(j, (currentTime - startTime) / 1000.0, currentDataSources[j]);
							}
						} catch (Exception e) {
							System.out.println("No data");
						}

					}

				}
				window.getWindow().repaint();
				prevTime = System.currentTimeMillis();
			}
			Thread.sleep(1);
		}
	}

	private static DataThread[] initDataThreads(DataThreadFactory[] dataThreadFactories) {
		DataThread[] dataThreads = new JSONDataThread[dataThreadFactories.length];
		for (int i = 0; i < dataThreadFactories.length; i++) {
			dataThreads[i] = dataThreadFactories[i].createThread(dataThreadFactories[i].getParameters());
			dataThreads[i].start();
		}

		return dataThreads;
	}
}