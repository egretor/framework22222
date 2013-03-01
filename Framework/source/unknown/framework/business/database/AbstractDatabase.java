package unknown.framework.business.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unknown.framework.utility.Trace;
import unknown.framework.module.database.Instance;
import unknown.framework.module.database.OperationType;
import unknown.framework.module.database.Paging;
import unknown.framework.module.database.Result;
import unknown.framework.module.database.Row;
import unknown.framework.module.database.Table;
import unknown.framework.module.database.Operation;

/**
 * 数据库
 */
public abstract class AbstractDatabase {
	/**
	 * 链接缓存
	 */
	protected final static Map<String, Connection> connectionCache = new HashMap<String, Connection>();

	/**
	 * 数据库实例
	 * 
	 * @return 数据库实例
	 */
	public abstract Instance getInstance();

	/**
	 * SQL大写
	 * 
	 * @return SQL大写
	 */
	public abstract boolean capitalSQL();

	/**
	 * 访问数据库
	 * 
	 * @param operation
	 *            操作
	 * @return 结果
	 */
	public Result access(Operation operation) {
		Result result = null;
		List<Operation> operations = new ArrayList<Operation>();
		operations.add(operation);
		List<Result> results = this.access(operations);
		if ((results != null) && (results.size() > 0)) {
			result = results.get(0);
		}

		return result;
	}

	/**
	 * 访问数据库
	 * 
	 * @param operations
	 *            操作集合
	 * @return 结果集合
	 */
	public List<Result> access(List<Operation> operations) {
		List<Result> results = null;

		if (operations != null) {
			Instance instance = this.getInstance();
			String name = instance.getName();
			String url = instance.getUrl();
			String user = instance.getUser();
			String password = instance.getPassword();

			Connection connection = null;
			if (AbstractDatabase.connectionCache.containsKey(name)) {
				connection = AbstractDatabase.connectionCache.get(name);
			} else {
				try {
					connection = DriverManager.getConnection(url, user,
							password);
					AbstractDatabase.connectionCache.put(name, connection);
				} catch (SQLException e) {
					Trace.logger().error(e);
				}
			}

			results = this.statement(connection, operations);
		}

		return results;
	}

	/**
	 * 准备语句
	 * 
	 * @param connection
	 *            数据库链接
	 * @param Operations
	 *            操作集合
	 * @return 结果集合
	 */
	protected List<Result> statement(Connection connection,
			List<Operation> Operations) {
		List<Result> results = null;

		PreparedStatement preparedStatement = null;
		results = new ArrayList<Result>();
		try {
			for (int i = 0; i < Operations.size(); i++) {
				Result result = null;
				Operation Operation = Operations.get(i);
				if (Operation != null) {
					int operationType = Operation.getOperationType();
					Paging paging = Operation.getPaging();
					String sql = null;
					if (this.capitalSQL()) {
						sql = Operation.getSql().toUpperCase();
					} else {
						sql = Operation.getSql().toLowerCase();
					}
					List<Object> parameters = Operation.getParameters();

					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.clearParameters();
					if (parameters != null) {
						int index = 0;
						while (index < parameters.size()) {
							Object value = parameters.get(index);
							index++;
							preparedStatement.setObject(index, value);
						}
					}

					switch (operationType) {
					case OperationType.READ_OPERATION:
						if (paging == null) {
							result = this.executeQuery(preparedStatement);
						} else {
							AbstractSqlBuilder sqlBuilder = this.getInstance()
									.getSqlBuilder();
							String rowCountSql = sqlBuilder.rowCount(sql);
							if (this.capitalSQL()) {
								rowCountSql = rowCountSql.toUpperCase();
							} else {
								rowCountSql = rowCountSql.toLowerCase();
							}
							result = this.executePagingQuery(preparedStatement,
									paging, rowCountSql);
						}
						break;
					case OperationType.WRITE_OPERATION:
						result = this.executeUpdate(preparedStatement);
						break;
					}

					preparedStatement.close();
				}
				results.add(result);
			}
		} catch (SQLException e) {
			Trace.logger().error(e);
		}
		return results;
	}

	/**
	 * 执行查询
	 * 
	 * @param preparedStatement
	 *            语句
	 * @return 结果
	 */
	protected Result executeQuery(PreparedStatement preparedStatement) {
		Result result = new Result();

		try {
			List<String> fields = new ArrayList<String>();
			List<Row> rows = new ArrayList<Row>();

			ResultSet resultSet = preparedStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int index = 0;
			int count = metaData.getColumnCount();
			while (index < count) {
				index++;
				String field = metaData.getColumnName(index);
				fields.add(field);
			}

			while (resultSet.next()) {
				Row row = new Row();
				List<Object> values = new ArrayList<Object>();

				index = 0;
				while (index < count) {
					index++;
					Object value = resultSet.getObject(index);
					values.add(value);
				}

				row.setValues(values);
				rows.add(row);
			}

			resultSet.close();

			Table table = new Table();
			table.setFields(fields);
			table.setRows(rows);

			result.setDone(true);
			result.setTable(table);
		} catch (SQLException e) {
			result.setDone(false);
			Trace.logger().error(e);
		}

		return result;
	}

	/**
	 * 执行分页查询
	 * 
	 * @param preparedStatement
	 *            语句
	 * @param paging
	 *            分页
	 * @param rowCountSql
	 *            行数总计SQL
	 * @return 结果
	 */
	protected Result executePagingQuery(PreparedStatement preparedStatement,
			Paging paging, String rowCountSql) {
		Result result = new Result();

		try {
			// 查询数据
			List<String> fields = new ArrayList<String>();
			List<Row> rows = new ArrayList<Row>();

			ResultSet resultSet = preparedStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int index = 0;
			int count = metaData.getColumnCount();
			while (index < count) {
				index++;
				String field = metaData.getColumnName(index);
				fields.add(field);
			}

			int currentPageRowCount = 0;
			int currentPageFirstRow = paging.getCurrentPageFirstRow();
			int rowsPerPage = paging.getRowsPerPage();
			resultSet.absolute(currentPageFirstRow);
			if (!resultSet.isAfterLast()) {
				do {
					Row row = new Row();
					List<Object> values = new ArrayList<Object>();

					index = 0;
					while (index < count) {
						index++;
						Object value = resultSet.getObject(index);
						values.add(value);
					}

					row.setValues(values);
					rows.add(row);
					currentPageRowCount++;
					if (currentPageRowCount == rowsPerPage) {
						break;
					}
				} while (resultSet.next());
			}

			resultSet.close();

			Table table = new Table();
			table.setFields(fields);
			table.setRows(rows);
			// 计算总行数
			int rowCount = 0;
			resultSet = preparedStatement.executeQuery(rowCountSql);
			while (resultSet.next()) {
				rowCount = resultSet.getInt(1);
			}
			resultSet.close();
			paging.setRowCount(rowCount);
			
			result.setDone(true);
			result.setTable(table);
			result.setPaging(paging);
		} catch (SQLException e) {
			result.setDone(false);
			Trace.logger().error(e);
		}

		return result;
	}

	/**
	 * 执行更新
	 * 
	 * @param preparedStatement
	 *            语句
	 * @return 结果
	 */
	protected Result executeUpdate(PreparedStatement preparedStatement) {
		Result result = new Result();

		try {
			preparedStatement.executeUpdate();
			result.setDone(true);
		} catch (SQLException e) {
			result.setDone(false);
			Trace.logger().error(e);
		}

		return result;
	}
}
