package unknown.framework.module.database;

import unknown.framework.business.database.AbstractSqlBuilder;

/**
 * 数据库实例
 */
public class Instance {
	private String name;
	private String url;
	private String user;
	private String password;
	private AbstractSqlBuilder sqlBuilder;

	/**
	 * 名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 连接Url
	 * 
	 * @return 连接Url
	 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 用户
	 * 
	 * @return 用户
	 */
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 密码
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * SQL生成器
	 * 
	 * @return SQL生成器
	 */
	public AbstractSqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}

	public void setSqlBuilder(AbstractSqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
	}
}
