package org.aemUtils.tech.aemUtils.core.servlets;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

// Event Listeners are created using Event handlers

//Listener watches the event
//Handler handles or directs the event



@Component
@Service(value= EventHandler.class)
@Property(name ="event.topics", value=ReplicationAction.EVENT_TOPIC)
public class EventHanlderCustom implements EventHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventHanlderCustom.class);
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public void handleEvent(Event event) {

		LOGGER.info("**** Handling Event*****");
		process(event);
	}
	
	
	public boolean process(Event event){
		LOGGER.info("****Processing the JOB*******");
		ReplicationAction action = ReplicationAction.fromEvent(event);
		ResourceResolver resourceResolver =null;
		
		if(action.getType().equals(ReplicationActionType.ACTIVATE)){
			
			try {
				resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
				final PageManager pm = resourceResolver.adaptTo(PageManager.class);
				final Page page = pm.getContainingPage(action.getPath());
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return true;
	}

}
