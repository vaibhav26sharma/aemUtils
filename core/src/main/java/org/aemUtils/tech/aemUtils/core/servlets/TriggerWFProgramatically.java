package org.aemUtils.tech.aemUtils.core.servlets;

import java.io.IOException;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameterMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.day.cq.workflow.model.WorkflowModel ;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService ; 
import com.day.cq.workflow.WorkflowSession; 
import com.day.cq.workflow.exec.WorkflowData; 


@Component
@Service
@Properties({
	
	@Property(name="sling.servlet.paths", value="/bin/programaticallyrunworkflow"),
	@Property(name="sling.servlet.methods", value="GET")
})

public class TriggerWFProgramatically extends SlingSafeMethodsServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static private Logger LOGGER = LoggerFactory.getLogger(TriggerWFProgramatically.class);
	
	@Reference
	private WorkflowService workflowService;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException{
		
		
		ResourceResolver resourceResolver =request.getResourceResolver();
		Session session = resourceResolver.adaptTo(Session.class);
		
		
		//RequestParameterMap params = request.getRequestParameterMap();
		String path="/content/we-retail";
		String model="request_for_activation";
		try {
		
		WorkflowSession wfSession = workflowService.getWorkflowSession(session);
		WorkflowModel wfModel = wfSession.getModel(model);
		WorkflowData wfData = wfSession.newWorkflowData("JCR_PATH", path);
		
		
		wfSession.startWorkflow(wfModel,wfData);
		
		} catch (WorkflowException e) {
			response.getWriter().write("failed");
			LOGGER.error("Error starting workflow", e);
		}
		
		response.getWriter().write("Success");
		
	}
	
	
	
}
