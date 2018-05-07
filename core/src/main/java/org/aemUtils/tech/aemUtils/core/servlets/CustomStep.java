package org.aemUtils.tech.aemUtils.core.servlets;

import org.apache.commons.mail.SimpleEmail;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;


@Component
@Service

@Properties({
	@Property(name= Constants.SERVICE_DESCRIPTION, value="Test Email workflow process step"),
	@Property(name= Constants.SERVICE_VENDOR, value="ADOBE"),
	@Property(name="process.label", value="Custom Test Email workflow step")
	
	
})
public class CustomStep implements WorkflowProcess{
	
	
	protected final Logger LOGGER = LoggerFactory.getLogger(CustomStep.class);
	
	@Reference
	private MessageGatewayService messageGatewayService;

	@Override
	public void execute(WorkItem arg0, WorkflowSession arg1, MetaDataMap arg2) throws WorkflowException {
		

		LOGGER.info("In the execute method");
		
		//Getting a message gateway
		MessageGateway<Email> messageGateway;
		
		//Creating email instance
		Email email = new SimpleEmail();
		
		String emailToRecipients ="vaibhavsharma.ei@gmail.com";
		String emailCCRecipients ="vaibhav.6451956@gmail.com";
		
	    arg0.getWorkflowData().getMetaDataMap().put("firstStepMessage", "Hello second step");

		
		try {
			email.addTo(emailToRecipients);
			email.addCc(emailCCRecipients);
			email.setSubject("AEM Custom step");
			email.setFrom("vaisharm@adobe.com");
			email.setMsg("This is a sample message");
			
			
			messageGateway = messageGatewayService.getGateway(Email.class);
			messageGateway.send((Email)email);
		} catch (EmailException e) {
			LOGGER.error("Email delivery failed");
			e.printStackTrace();
		}
		
	}

}
