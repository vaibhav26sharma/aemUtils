package org.aemUtils.tech.aemUtils.core.schedulers;

import java.util.Map;

import org.aemUtils.tech.aemUtils.core.servlets.LogParser;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A cron-job like tasks that get executed regularly. It hits the log parser and
 * checks if values in /system/console/configMgr
 */
@Component(metatype = true, label = "A scheduler to read error traces", description = "Thi Scheduler timely hits the LogParser Service & gets the error traces if any in the logs")
@Service(value = Runnable.class)
@Properties({ @Property(name = "isActive", boolValue = false, description = "Check to start this scheduler"),
		@Property(name = "scheduler.expression", value = "*/30 * * * * ?", description = "Cron-job expression"),
		@Property(name = "scheduler.concurrent", boolValue = false, description = "Whether or not to schedule this task concurrently") })

public class LogReadScheduler implements Runnable {

	@Reference
	private LogParser lParser;

	@Property(label = "sourceLogDirectory.path", description = "Provide the fully qualified path for your error.log file")
	public static final String sourceLogDirectory = "sourceLogDirectory";
	private String source;

	@Property(label = "destinationLogDirectory.path", description = "Provide the fully qualified path for the destination to store the generated errorOnly.log file")
	public static final String destinationLogDirectory = "destinationLogDirectory";
	private String destination;

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public void run() {
		LOGGER.debug("LogReadScheduler is now running, source='{}'", source);
		LOGGER.debug("LogReadScheduler is now running, destination='{}'", destination);
	}

	/*
	 * @Property(label = "A parameter", description =
	 * "Can be configured in /system/console/configMgr") public static final
	 * String MY_PARAMETER = "myParameter"; private String myParameter;
	 */
	@Activate
	protected void activate(final Map<String, Object> config) {
		readProperties(config);

		lParser.parseLogger(source, destination);
	}

	private void readProperties(final Map<String, Object> config) {
		// myParameter = PropertiesUtil.toString(config.get(MY_PARAMETER),
		// null);
		source = PropertiesUtil.toString(config.get(sourceLogDirectory), null);
		LOGGER.debug("**************configure: sourceLogDirectory='{}''", source);

		destination = PropertiesUtil.toString(config.get(destinationLogDirectory), null);
		LOGGER.debug("***************configure: destinationLogDirectory='{}''", destination);
		// LOGGER.debug("configure: myParameter='{}''", myParameter);

	}
}
