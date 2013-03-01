package unknown.framework.module.database;

import java.util.List;

/**
 * 数据库操作
 */
public class Operation {

	private int operationType;
	private String sql;
	private List<Object> parameters;
	private Paging paging;

	/**
	 * 操作类型
	 * 
	 * @return 操作类型
	 */
	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}

	/**
	 * SQL
	 * 
	 * @return SQL
	 */
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 参数集合
	 * 
	 * @return 参数集合
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * 分页
	 * 
	 * @return 分页
	 */
	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}
}
