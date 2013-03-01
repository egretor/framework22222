package unknown.framework.business.database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import unknown.framework.module.database.OperationType;
import unknown.framework.module.database.Paging;
import unknown.framework.module.database.Result;
import unknown.framework.module.database.Row;
import unknown.framework.module.database.Table;
import unknown.framework.module.database.Operation;
import unknown.framework.module.pojo.ViewPojo;
import unknown.framework.utility.Trace;

/**
 * 视图
 * 
 * @param <T>
 *            数据库视图实体
 */
public abstract class AbstractView<T extends ViewPojo> extends AbstractDatabase {
	/**
	 * 
	 * @return 视图SQL
	 */
	public abstract String viewSql();

	/**
	 * 
	 * @return 实体对象
	 */
	public abstract T initializePojo();

	/**
	 * 表名称
	 * 
	 * @param value
	 *            实体对象
	 * @return 表名称
	 */
	public String getTableName(T value) {
		String result = this.viewSql();

		Convention convention = new Convention();
		if (result == null) {
			result = convention.decodeName(value.getClass().getSimpleName());
		}

		return result;
	}

	/**
	 * 数据转换
	 * 
	 * @param fields
	 *            表字段集合
	 * @param row
	 *            行数据
	 * @return 实体对象
	 */
	protected T Parse(List<String> fields, Row row) {
		T result = this.initializePojo();

		List<Object> values = row.getValues();

		Convention convention = new Convention();
		List<Method> methods = convention.filterSetMethod(result);
		if (methods != null) {
			for (int j = 0; j < fields.size(); j++) {
				Object column = values.get(j);
				String field = fields.get(j);
				for (int k = 0; k < methods.size(); k++) {
					Method method = methods.get(k);
					String methodName = convention.decodeSetMethodName(method
							.getName());
					String fieldName = convention.decodeName(methodName);
					if (field.equalsIgnoreCase(fieldName)) {
						try {
							method.invoke(result, column);
						} catch (IllegalArgumentException e) {
							Trace.logger().error(e);
						} catch (IllegalAccessException e) {
							Trace.logger().error(e);
						} catch (InvocationTargetException e) {
							Trace.logger().error(e);
						}
						break;
					}
				}
			}
		}

		return result;
	}

	/**
	 * 数据转换
	 * 
	 * @param table
	 *            表数据
	 * @return 实体对象集合
	 */
	protected List<T> Parse(Table table) {
		List<T> results = null;

		if (table != null) {
			List<String> fields = table.getFields();
			List<Row> rows = table.getRows();
			if ((fields != null) && (fields.size() > 0)) {
				if (rows != null) {
					results = new ArrayList<T>();
					for (int i = 0; i < rows.size(); i++) {
						Row row = rows.get(i);
						T currentValue = this.Parse(fields, row);
						results.add(currentValue);
					}
				}
			}
		}

		return results;
	}

	/**
	 * 查询
	 * 
	 * @return 所有实体对象集合
	 */
	public List<T> query() {
		List<T> results = null;

		Paging paging = null;
		results = this.query(paging);

		return results;
	}

	/**
	 * 分页查询
	 * 
	 * @param paging
	 *            分页
	 * @return 实体对象集合
	 */
	public List<T> query(Paging paging) {
		List<T> results = null;

		T value = this.initializePojo();
		if (value != null) {
			String tableName = this.getTableName(value);

			AbstractSqlBuilder sqlBuilder = this.getInstance().getSqlBuilder();
			String sql = sqlBuilder.query(tableName);

			List<Object> parameters = new ArrayList<Object>();

			Operation operation = new Operation();
			operation.setOperationType(OperationType.READ_OPERATION);
			operation.setSql(sql);
			operation.setParameters(parameters);
			operation.setPaging(paging);

			Result operationResult = this.access(operation);
			if (operationResult != null) {
				if (operationResult.isDone()) {
					Table table = operationResult.getTable();
					results = this.Parse(table);
				}
			}
		}

		return results;
	}

	/**
	 * 根据代理主键查询
	 * 
	 * @param uuid
	 *            代理主键
	 * @return 实体对象
	 */
	public T query(String uuid) {
		T result = null;

		T value = this.initializePojo();
		if (value != null) {
			String tableName = this.getTableName(value);

			AbstractSqlBuilder sqlBuilder = this.getInstance().getSqlBuilder();
			String sql = sqlBuilder.queryByUuid(tableName);
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(uuid);

			Operation operation = new Operation();
			operation.setOperationType(OperationType.READ_OPERATION);
			operation.setSql(sql);
			operation.setParameters(parameters);
			operation.setPaging(null);

			Result operationResult = this.access(operation);
			if (operationResult != null) {
				if (operationResult.isDone()) {
					Table table = operationResult.getTable();
					List<T> values = this.Parse(table);
					if ((values != null) && (values.size() > 0)) {
						result = values.get(0);
					}
				}
			}
		}

		return result;
	}
}
