package org.aemUtils.tech.aemUtils.core.servlets;

import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(immediate=true)
@Service
@Properties({
	@Property(name="service.description", value="My Custom dynamic participant step"),
	@Property(name="chooser.label", value="Dynamic Participant chooser step")
})
public class CustomDynamicParticipantStep implements ParticipantStepChooser{

	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args)
			throws WorkflowException {
		
		Logger LOGGER = LoggerFactory.getLogger(this.getClass());
		
		LOGGER.info("### Inside Custom Dynamic Participant Step ####");
		
		String participant ="testuser";
		
       String firststepMessage = workItem.getWorkflowData().getMetaDataMap().get("firstStepMessage", String.class);

       LOGGER.info(firststepMessage);
		
		Workflow wf = workItem.getWorkflow();
		List<HistoryItem> wfHistory = workflowSession.getHistory(wf);
		
		if(!wfHistory.isEmpty()){
			
			participant = "administrators";
		}
		else{
			participant ="testuser";
		}
		
		LOGGER.info("#### Participant:"+ participant);
		return participant;
	}

}
