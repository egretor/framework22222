package unknown.website.manage.business;

import unknown.framework.business.database.AbstractView;
import unknown.framework.module.database.Instance;
import unknown.framework.module.pojo.ViewPojo;
import unknown.website.MySQL;

/**
 * 管理模块视图
 */
public abstract class AbstractMySQLView extends AbstractView<ViewPojo> {
	@Override
	public Instance getInstance() {
		return MySQL.getManageInstance();
	}

	@Override
	public boolean capitalSQL() {
		return false;
	}

}
