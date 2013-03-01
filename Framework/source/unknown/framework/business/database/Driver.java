package unknown.framework.business.database;

import unknown.framework.module.database.DriverType;
import unknown.framework.utility.Trace;

public final class Driver {
	public static void registerDriver() {
		try {
			Class.forName(DriverType.MYSQL);
			Class.forName(DriverType.ORACLE);
		} catch (ClassNotFoundException e) {
			Trace.logger().error(e);
		}
	}
}
