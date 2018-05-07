package org.aemUtils.tech.aemUtils.core.servlets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Component(label="Custom ACL Event Listener", immediate = true, metatype=false)

@Service(EventListener.class)
public class CustomJCRACLEventListener implements EventListener{

	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomJCRACLEventListener.class);
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	private ResourceResolver resourceResolver;
	
	private Session session;
	
    private BundleContext bundleContext;
	
	private ObservationManager observationManager;
	
	@Reference
	private SlingRepository repository;
	
	@Deactivate
	protected void deactivate(){
		LOGGER.info("----CustomJCRACLEventListener Signing Off-----");
		
	}
	
	@Activate
	protected  void activate(ComponentContext ctx) throws UnsupportedRepositoryOperationException, RepositoryException
	{
		LOGGER.info("----CustomJCRACLEventListener kicking in-----");
		this.bundleContext = ctx.getBundleContext();
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put(ResourceResolverFactory.SUBSERVICE, "getResourceResolver");
		
		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
			
			//session = resourceResolver.adaptTo(Session.class);
			
			session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
			
			LOGGER.info("----CustomJCRACLEventListener Using User id:"+ session.getUserID());
			
			
			observationManager = session.getWorkspace().getObservationManager();
			
			String[] types = {"nt:unstructured"};
			
			String path ="/content";
			
			observationManager.addEventListener(this, Event.PROPERTY_CHANGED, path, true, null, types, false);
			
			LOGGER.info("----CustomJCRACLEventListener, Observing property changes to {} nodes under {}", Arrays.asList(types),path);
			
		} catch (LoginException e) {
			LOGGER.error("Unable to register listener",e);
		}
		
		
	}	
	
	@Override
	public void onEvent(EventIterator events) {
		LOGGER.info("----CustomJCRACLEventListener, IN ONE EVENT");
		
        try {

		while(events.hasNext()){
			Event event = events.nextEvent();
			
		}
	}catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
    }

		
	}

}
