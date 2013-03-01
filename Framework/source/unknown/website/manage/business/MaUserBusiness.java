package unknown.website.manage.business;

import java.util.List;

import unknown.website.manage.module.MaUser;

public class MaUserBusiness extends AbstractMySQLTable<MaUser> {

	@Override
	public List<MaUser> Unique(MaUser value) {
		return null;
	}

	@Override
	public boolean deleteReference(MaUser value) {
		return false;
	}

	@Override
	public boolean hasReference(MaUser value) {
		return false;
	}

	@Override
	public MaUser initializePojo() {
		return new MaUser();
	}
}
