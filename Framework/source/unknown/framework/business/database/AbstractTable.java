package unknown.framework.business.database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import unknown.framework.module.database.OperationType;
import unknown.framework.module.database.Result;
import unknown.framework.module.database.Operation;
import unknown.framework.module.pojo.AbstractDatabasePojo;
import unknown.framework.module.pojo.TablePojo;
import unknown.framework.utility.Trace;

/**
 * 表
 */
public abstract class AbstractTable<T extends TablePojo> extends
		AbstractView<T> {
	/**
	 * 成功
	 */
	public final static int SUCCESS = 0;
	/**
	 * 失败
	 */
	public final static int FAIL = 1;
	/**
	 * 存在相同数据
	 */
	public final static int HAS_SAME = 2;
	/**
	 * 存在引用数据
	 */
	public final static int HAS_REFERENCE = 3;

	@Override
	public String viewSql() {
		return null;
	}

	/**
	 * 唯一值
	 * 
	 * @param value
	 *            实体对象
	 * @return 实体对象集合
	 */
	public abstract List<T> Unique(T value);

	/**
	 * 删除引用数据
	 * 
	 * @param value
	 *            实体对象
	 * @return 操作结果
	 */
	public abstract boolean deleteReference(T value);

	/**
	 * 是否存在引用数据
	 * 
	 * @param value
	 *            实体对象
	 * @return 结果
	 */
	public abstract boolean hasReference(T value);

	/**
	 * 是否存在相同数据
	 * 
	 * @param value
	 *            实体对象
	 * @return 结果
	 */
	public boolean hasSame(T value) {
		boolean result = false;

		if (value != null) {
			List<T> uniqueValues = this.Unique(value);
			if (uniqueValues != null) {
				if (uniqueValues.size() > 1) {
					result = true;
				} else {
					T uniqueValue = uniqueValues.get(0);
					String uuid = value.fgetUuid();
					String uniqueUuid = uniqueValue.fgetUuid();
					if (uuid == null) {
						result = true;
					} else {
						if (!uniqueUuid.equals(uuid)) {
							result = true;
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * 保存
	 * 
	 * @param value
	 *            实体对象
	 * @return 操作结果
	 */
	public int save(T value) {
		int result = AbstractTable.FAIL;

		if (value != null) {
			String uuid = value.fgetUuid();
			T pojo = this.query(uuid);
			if (pojo == null) {
				result = this.insert(value);
			} else {
				result = this.update(value);
			}
		}

		return result;
	}

	/**
	 * 增加
	 * 
	 * @param value
	 *            数据库实体
	 * @return 操作结果
	 */
	public int insert(T value) {
		int result = AbstractTable.FAIL;

		if (value != null) {
			boolean has = this.hasSame(value);
			if (has) {
				result = AbstractTable.HAS_SAME;
			} else {
				Convention convention = new Convention();
				List<Method> properties = convention.filterGetMethod(value);

				if (properties.size() > 0) {
					List<Object> parameters = new ArrayList<Object>();
					for (int i = 0; i < properties.size(); i++) {
						Method property = properties.get(i);

						try {
							parameters.add(property.invoke(value));
						} catch (IllegalArgumentException e) {
							Trace.logger().error(e);
						} catch (IllegalAccessException e) {
							Trace.logger().error(e);
						} catch (InvocationTargetException e) {
							Trace.logger().error(e);
						}
					}

					AbstractSqlBuilder sqlBuilder = this.getInstance()
							.getSqlBuilder();
					String sql = sqlBuilder.insert(value);

					Operation task = new Operation();
					task.setOperationType(OperationType.WRITE_OPERATION);
					task.setSql(sql);
					task.setParameters(parameters);

					Result taskResult = this.access(task);
					if (taskResult != null) {
						if (taskResult.isDone()) {
							result = AbstractTable.SUCCESS;
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * 修改
	 * 
	 * @param value
	 *            数据库实体
	 * @return 操作结果
	 */
	public int update(T value) {
		int result = AbstractTable.FAIL;

		if (value != null) {
			boolean has = this.hasSame(value);
			if (has) {
				result = AbstractTable.HAS_SAME;
			} else {
				Convention convention = new Convention();
				List<Method> properties = convention.filterGetMethod(value);

				if (properties.size() > 0) {
					List<Object> parameters = new ArrayList<Object>();
					for (int i = 0; i < properties.size(); i++) {
						Method property = properties.get(i);
						String propertyName = convention
								.decodeGetMethodName(property.getName());
						String fieldName = convention.decodeName(propertyName);

						if (!AbstractDatabasePojo.UUID
								.equalsIgnoreCase(fieldName)) {
							try {
								parameters.add(property.invoke(value));
							} catch (IllegalArgumentException e) {
								Trace.logger().error(e);
							} catch (IllegalAccessException e) {
								Trace.logger().error(e);
							} catch (InvocationTargetException e) {
								Trace.logger().error(e);
							}
						}
					}
					parameters.add(value.fgetUuid());

					AbstractSqlBuilder sqlBuilder = this.getInstance()
							.getSqlBuilder();
					String sql = sqlBuilder.update(value);

					Operation task = new Operation();
					task.setOperationType(OperationType.WRITE_OPERATION);
					task.setSql(sql);
					task.setParameters(parameters);

					Result taskResult = this.access(task);
					if (taskResult != null) {
						if (taskResult.isDone()) {
							result = AbstractTable.SUCCESS;
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * 删除
	 * 
	 * @param value
	 *            数据库实体
	 * @return 操作结果
	 */
	public int delete(T value) {
		int result = AbstractTable.FAIL;

		result = this.delete(value, false);

		return result;
	}

	/**
	 * 删除
	 * 
	 * @param value
	 *            数据库实体
	 * @param cascade
	 *            级联
	 * @return 操作结果
	 */
	public int delete(T value, boolean cascade) {
		int result = AbstractTable.FAIL;

		if (value != null) {
			boolean done = true;
			if (cascade) {
				done = this.deleteReference(value);
			}
			if (done) {
				boolean has = this.hasReference(value);
				if (has) {
					result = AbstractTable.HAS_REFERENCE;
				} else {
					String tableName = this.getTableName(value);

					AbstractSqlBuilder sqlBuilder = this.getInstance()
							.getSqlBuilder();

					String sql = sqlBuilder.delete(tableName);
					List<Object> parameters = new ArrayList<Object>();
					parameters.add(value.fgetUuid());

					Operation task = new Operation();
					task.setOperationType(OperationType.WRITE_OPERATION);
					task.setSql(sql);
					task.setParameters(parameters);

					Result taskResult = this.access(task);
					if (taskResult != null) {
						if (taskResult.isDone()) {
							result = AbstractTable.SUCCESS;
						}
					}
				}
			}
		}

		return result;
	}
}
