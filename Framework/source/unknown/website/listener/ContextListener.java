package unknown.website.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import unknown.framework.business.database.Driver;
import unknown.framework.business.mysql.MySQLSqlBuilder;
import unknown.framework.module.database.Instance;
import unknown.website.MySQL;

/**
 * Application Lifecycle Listener implementation class ContextListener
 * 
 */
@WebListener
public class ContextListener implements ServletContextListener,
		ServletContextAttributeListener {
	private final static String WEBSITE_DIRECTORY_KEY = "WebsiteDirectoryKey";

	/**
	 * Default constructor.
	 */
	public ContextListener() {
	}

	private static String websiteDirectory;

	public static String getWebSiteDirectory() {
		return ContextListener.websiteDirectory;
	}

	private static String website;

	public static String getWebsite() {
		return ContextListener.website;
	}

	private void initializeWebsiteDirectory(ServletContext servletContext) {
		String websiteDirectoryKey = servletContext
				.getInitParameter(ContextListener.WEBSITE_DIRECTORY_KEY);
		ContextListener.websiteDirectory = servletContext.getRealPath("/");
		System.setProperty(websiteDirectoryKey,
				ContextListener.websiteDirectory);
	}

	private void initializeWebsite(ServletContext servletContext) {
		ContextListener.website = servletContext.getContextPath();
	}

	private Instance initializeInstance(ServletContext servletContext,
			String instanceName) {
		Instance result = new Instance();

		String urlKey = String.format("%s.Url", instanceName);
		String userKey = String.format("%s.User", instanceName);
		String passwordKey = String.format("%s.Password", instanceName);

		result.setName(instanceName);
		result.setUrl(servletContext.getInitParameter(urlKey));
		result.setUser(servletContext.getInitParameter(userKey));
		result.setPassword(servletContext.getInitParameter(passwordKey));
		result.setSqlBuilder(new MySQLSqlBuilder());

		return result;
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();

		this.initializeWebsiteDirectory(servletContext);
		this.initializeWebsite(servletContext);

		Driver.registerDriver();

		Instance mysqlManageInstance = this.initializeInstance(servletContext,
				MySQL.MANAGE_INSTANCE_NAME);
		MySQL.setManageInstance(mysqlManageInstance);
	}

	/**
	 * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
	 */
	public void attributeAdded(ServletContextAttributeEvent event) {
	}

	/**
	 * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
	 */
	public void attributeReplaced(ServletContextAttributeEvent event) {
	}

	/**
	 * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
	 */
	public void attributeRemoved(ServletContextAttributeEvent event) {
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
	}

}
