package org.aemUtils.tech.aemUtils.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SlingServlet(methods ={"GET"},
		metatype=true,
		resourceTypes = {"services/powerproxy"},
		selectors={"groups"})
public class SlingServletBinded2ResourceType extends SlingSafeMethodsServlet {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(SlingServletBinded2ResourceType.class);
	
	/*
	 * Create a page and mention the sling:resouceType property of the page with
	 * value same as mentioned in resourceTypes for this servlet. The page then will render from
	 * the servlet*/
	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
		
		LOGGER.info("--->Inside the get method of resource Type servlet for aemutisl/components/page/slingTemplate");
		PrintWriter out = response.getWriter();
		
		out.write("<html><body>");
		out.println("<h1>This value was returned by an AEM Sling Servlet bound by using a Sling ResourceTypes property</h1>");
        out.println("</html></body>");
		
		
	}
}
