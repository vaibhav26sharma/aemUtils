package org.aemUtils.tech.aemUtils.core.servlets;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang.time.StopWatch;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(metatype = false, label = "Parses error.log file for Error traces")
@Service(value = LogParser.class)

public class LogParserImpl implements LogParser {

	/**
	 * parseLogger reads the AEM OOTB error.log file and creates a new log file
	 * out of it containing just the error traces
	 * 
	 * sourcePath= Path to read the error.log file from destinationPath= Path to
	 * write & save the errorOnly.log file to
	 */

	// private static final String FILENAME =
	// "C://Users//vaisharm//Documents//AEM_DEV//Instances//Author62//crx-quickstart//logs//error.log";

	private final Logger LOGGER = LoggerFactory.getLogger(LogParserImpl.class);

	private static final StopWatch stopwatch = new StopWatch();

	private static long ERROR_COUNT = 0;

	@Activate
	protected void activate(final Map<String, Object> config) {
		LOGGER.debug("*************Activating the LogParser Service************");

	}

	public void parseLogger(String sourcePath, String destinationPath) {

		stopwatch.start();

		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> al = new ArrayList<String>();
		Stream<String> stream = null;
		List<String> errorOnlyList = new ArrayList<String>();

		try {

			fr = new FileReader(sourcePath);
			br = new BufferedReader(fr);
			stream = br.lines();

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				al.add(sCurrentLine);
			}

			for (int i = 0; i < al.size(); i++) {
				String line = al.get(i);

				if (line.contains("ERROR")) {

					ERROR_COUNT++;
					errorOnlyList.add(al.get(i) + '\n');
				}

			}

			try {
				/*
				 * FileOutputStream fileOut = new FileOutputStream(
				 * "C:\\Users\\vaisharm\\Documents\\AEM_DEV\\Instances\\Author62\\crx-quickstart\\logs\\errorOnly.log"
				 * );
				 */
				FileOutputStream fileOut = new FileOutputStream(destinationPath);

				ObjectOutputStream oos = new ObjectOutputStream(fileOut);

				oos.writeObject(errorOnlyList);

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		} catch (IOException e) {

		}

		finally {
			stopwatch.stop();
			stopwatch.split();
			LOGGER.info("***************Time taken to parse the error.log file**************" + "-->"
					+ stopwatch.toSplitString());
		}

	}

	@Override
	public boolean ifErrors() {

		if (ERROR_COUNT > 0)
			return true;
		else
			return false;
	}

	@Override
	public long totalErrors() {
		return ERROR_COUNT;
	}

}