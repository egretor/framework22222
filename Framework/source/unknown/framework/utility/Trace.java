package unknown.framework.utility;

import org.apache.log4j.Logger;

/**
 * 跟踪
 */
public class Trace {
	/**
	 * 日志器
	 * 
	 * @return 日志器
	 */
	public final static Logger logger() {
		return Logger.getLogger(Trace.class);
	}
}
