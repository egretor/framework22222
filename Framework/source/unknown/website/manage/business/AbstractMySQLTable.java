package unknown.website.manage.business;

import unknown.framework.business.database.AbstractTable;
import unknown.framework.module.database.Instance;
import unknown.website.MySQL;
import unknown.website.manage.module.AbstractManageTable;

/**
 * 管理模块表
 */
public abstract class AbstractMySQLTable<T extends AbstractManageTable> extends AbstractTable<T> {

	@Override
	public Instance getInstance() {
		return MySQL.getManageInstance();
	}

	@Override
	public boolean capitalSQL() {
		return false;
	}

}
