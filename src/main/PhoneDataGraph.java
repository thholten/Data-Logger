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


		LiveGraph accGraph = new LiveGraph(window, new String[] { "AccX", "AccY", "AccZ" }, "Acceleration",
				"Time (seconds)", "Units", 1.0, 10, 10);
		
		LiveGraph gyroGraph = new LiveGraph(window, new String[] { "GyroX", "GyroY", "GyroZ" }, "Gyro",
				"Time (seconds)", "Units", 1.0, 10, 10);
		
		LiveGraph magGraph = new LiveGraph(window, new String[] { "MagX", "MagY", "MagZ" }, "Magnetometer",
				"Time (seconds)", "Units", 1.0, 10, 10);

		LiveGraph[] liveGraphs = { accGraph , gyroGraph, magGraph};
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

		String[] accNames = new String[] { "accelerometerAccelerationX", "accelerometerAccelerationY",
				"accelerometerAccelerationZ" };
		
		String[] gyroNames = new String[] { "gyroRotationX", "gyroRotationY",
		"gyroRotationZ" };
		
		String[] magNames = new String[] { "magnetometerX", "magnetometerY",
		"magnetometerZ" };

		double[] accSources = new double[3];
		double[] gyroSources = new double[3];
		double[] magSources = new double[3];

		
		DataThreadFactory accType = new JSONDataThreadFactory(accSources, accNames, dataServer);
		DataThreadFactory gyroType = new JSONDataThreadFactory(gyroSources, gyroNames, dataServer);
		DataThreadFactory magType = new JSONDataThreadFactory(magSources, magNames, dataServer);

		
		synchronized (lock) {
			lock.wait();
		}

		window.show();

		DataThreadFactory[] dataThreadFactories = new DataThreadFactory[] { accType, gyroType, magType};

		DataThread[] dataThreads = initDataThreads(dataThreadFactories);
		long prevTime = System.currentTimeMillis(), startTime = System.currentTimeMillis();
		while (true) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - prevTime > 50) {
				for (int i = 0; i < dataThreadFactories.length; i++) {
					liveGraphs[i].updateDomain((currentTime - startTime) / 1000.0);
					if (!dataThreads[i].isAlive()) {
						dataThreads[i] = dataThreadFactories[i].createThread(dataThreadFactories[i].getParameters());
						dataThreads[i].start();
						try {
							double[] currentDataSources = dataThreads[i].getDataSources();
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