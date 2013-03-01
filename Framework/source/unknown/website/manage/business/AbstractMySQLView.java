package unknown.website.manage.business;

import unknown.framework.business.database.AbstractView;
import unknown.framework.module.database.Instance;
import unknown.website.MySQL;
import unknown.website.manage.module.AbstractManageView;

/**
 * 管理模块视图
 */
public abstract class AbstractMySQLView<T extends AbstractManageView> extends AbstractView<T> {
	@Override
	public Instance getInstance() {
		return MySQL.getManageInstance();
	}

	@Override
	public boolean capitalSQL() {
		return false;
	}

}
