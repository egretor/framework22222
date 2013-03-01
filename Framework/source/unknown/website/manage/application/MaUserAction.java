package unknown.website.manage.application;

import java.util.List;

import unknown.website.manage.business.MaUserBusiness;
import unknown.website.manage.module.MaUser;

import com.opensymphony.xwork2.ActionSupport;

public class MaUserAction {

	private List<MaUser> maUsers;

	public String execute() {
		MaUserBusiness bs = new MaUserBusiness();
		this.maUsers = bs.query();
		return ActionSupport.SUCCESS;
	}

	public List<MaUser> getMaUsers() {
		return maUsers;
	}

	public void setMaUsers(List<MaUser> maUsers) {
		this.maUsers = maUsers;
	}

}
