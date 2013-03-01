package unknown.website.manage.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import unknown.framework.business.database.Convention;
import unknown.framework.module.pojo.TablePojo;
import unknown.framework.utility.Trace;
import unknown.website.manage.module.MaUser;

public class MaUserBusiness extends AbstractMySQLTable {

	@Override
	public List<TablePojo> Unique(TablePojo value) {
		return null;
	}

	@Override
	public boolean deleteReference(TablePojo value) {
		return false;
	}

	@Override
	public boolean hasReference(TablePojo value) {
		return false;
	}

	@Override
	public TablePojo initializePojo() {
		return new MaUser();
	}

	public static int a1 = aaa();

	public static int aaa() {

		Date ca = Calendar.getInstance().getTime();
		Convention c = new Convention();
		MaUser maUser = new MaUser();
		maUser.fsetUuid(c.Uuid());
		maUser.fsetInsertUserId(maUser.fgetUuid());
		maUser.fsetInsertTime(ca);
		maUser.fsetUpdateUserId(maUser.fgetUuid());
		maUser.fsetUpdateTime(ca);
		maUser.fsetRemark("abc123");

		MaUserBusiness business = new MaUserBusiness();
		
		Trace.logger().info("test");

		return business.insert(maUser);
	}
}
