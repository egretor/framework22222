package unknown.website.manage.business;

import unknown.framework.business.database.AbstractTable;
import unknown.framework.module.database.Instance;
import unknown.framework.module.pojo.TablePojo;
import unknown.website.MySQL;

/**
 * 管理模块表
 */
public abstract class AbstractMySQLTable extends AbstractTable<TablePojo> {

	@Override
	public Instance getInstance() {
		return MySQL.getManageInstance();
	}

	@Override
	public boolean capitalSQL() {
		return false;
	}

}
