package unknown.framework.business.database;

import java.util.HashMap;
import java.util.Map;

import unknown.framework.module.pojo.AbstractDatabasePojo;

/**
 * SQL生成器
 */
public abstract class AbstractSqlBuilder {
	/**
	 * 分隔符
	 */
	protected final static String SEPARATOR = "@";

	/**
	 * 新增SQL名称
	 */
	protected final static String INSERT_SQL_NAME = "insert";

	/**
	 * 修改SQL名称
	 */
	protected final static String UPDATE_SQL_NAME = "update";

	/**
	 * SQL缓存
	 */
	protected static Map<String, String> sqlCache = new HashMap<String, String>();

	/**
	 * 缓存键
	 * 
	 * @param value
	 *            实体对象
	 * @param sqlName
	 *            SQL名称
	 * @return 缓存键
	 */
	protected String cacheKey(AbstractDatabasePojo value, String sqlName) {
		String result = null;

		if (value != null) {
			result = String.format("%s%s%s", sqlName,
					AbstractSqlBuilder.SEPARATOR, value.getClass().getName());
		}

		return result;
	}

	/**
	 * 获取缓存SQL
	 * 
	 * @param key
	 *            缓存键
	 * @return 缓存SQL
	 */
	protected String getCache(String key) {
		String result = null;

		if (key != null) {
			if (!key.isEmpty()) {
				if (AbstractSqlBuilder.sqlCache.containsKey(key)) {
					result = AbstractSqlBuilder.sqlCache.get(key);
				}
			}
		}

		return result;
	}

	/**
	 * 复制缓存SQL
	 * 
	 * @param key
	 *            缓存键
	 * @param value
	 *            缓存SQL
	 */
	protected void setCache(String key, String value) {
		if (key != null) {
			if (!key.isEmpty()) {
				if (!AbstractSqlBuilder.sqlCache.containsKey(key)) {
					AbstractSqlBuilder.sqlCache.put(key, value);
				}
			}
		}
	}

	/**
	 * 行数总计
	 * 
	 * @param sql
	 *            SQL
	 * @return SQL
	 */
	public abstract String rowCount(String sql);

	/**
	 * 查询
	 * 
	 * @param tableName
	 *            表名称
	 * @return SQL
	 */
	public abstract String query(String tableName);

	/**
	 * 根据代理主键查询
	 * 
	 * @param tableName
	 *            表名称
	 * @return SQL
	 */
	public abstract String queryByUuid(String tableName);

	/**
	 * 新增SQL实现
	 * 
	 * @param value
	 *            实体对象
	 * @return SQL
	 */
	protected abstract String insertImplement(AbstractDatabasePojo value);

	/**
	 * 修改SQL实现
	 * 
	 * @param value
	 *            实体对象
	 * @return SQL
	 */
	protected abstract String updateImplement(AbstractDatabasePojo value);

	/**
	 * 新增SQL
	 * 
	 * @param value
	 *            实体对象
	 * @return SQL
	 */
	public String insert(AbstractDatabasePojo value) {
		String result = null;

		String key = this.cacheKey(value, AbstractSqlBuilder.INSERT_SQL_NAME);
		result = this.getCache(key);
		if (result == null) {
			result = this.insertImplement(value);
			this.setCache(key, result);
		}

		return result;
	}

	/**
	 * 修改SQL
	 * 
	 * @param value
	 *            实体对象
	 * @return SQL
	 */
	public String update(AbstractDatabasePojo value) {
		String result = null;

		String key = this.cacheKey(value, AbstractSqlBuilder.UPDATE_SQL_NAME);
		result = this.getCache(key);
		if (result == null) {
			result = this.updateImplement(value);
			this.setCache(key, result);
		}

		return result;
	}

	/**
	 * 删除SQL
	 * 
	 * @param tableName
	 *            表名称
	 * @return SQL
	 */
	public abstract String delete(String tableName);
}
