package unknown.framework.business.mysql;

import java.lang.reflect.Method;
import java.util.List;

import unknown.framework.business.database.AbstractSqlBuilder;
import unknown.framework.business.database.Convention;
import unknown.framework.module.pojo.AbstractDatabasePojo;

/**
 * MySQL SQL生成器
 */
public class MySQLSqlBuilder extends AbstractSqlBuilder {
	/**
	 * 合计别名
	 */
	protected final static String TABLE_COUNT_ALIAS = "count__alias";

	@Override
	public String rowCount(String sql) {
		String result = null;

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(*) from (");
		stringBuilder.append(sql);
		stringBuilder.append(") as ");
		stringBuilder.append(MySQLSqlBuilder.TABLE_COUNT_ALIAS);

		result = stringBuilder.toString();

		return result;
	}

	@Override
	public String query(String tableName) {
		String result = null;

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from ");
		stringBuilder.append(tableName);

		result = stringBuilder.toString();

		return result;
	}

	@Override
	public String queryByUuid(String tableName) {
		String result = null;

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select * from ");
		stringBuilder.append(tableName);
		stringBuilder.append(" where ");
		stringBuilder.append(AbstractDatabasePojo.UUID);
		stringBuilder.append(" = ?");

		result = stringBuilder.toString();

		return result;
	}

	@Override
	public String insertImplement(AbstractDatabasePojo value) {
		String result = null;

		Convention convention = new Convention();
		List<Method> properties = convention.filterGetMethod(value);

		if (properties.size() > 0) {
			StringBuilder fieldStringBuilder = new StringBuilder();
			StringBuilder parameterStringBuilder = new StringBuilder();
			int index = 0;
			for (int i = 0; i < properties.size(); i++) {
				Method property = properties.get(i);
				String propertyName = convention.decodeGetMethodName(property
						.getName());
				String fieldName = convention.decodeName(propertyName);

				if (index > 0) {
					fieldStringBuilder.append(", ");
					parameterStringBuilder.append(", ");
				}
				fieldStringBuilder.append(fieldName);
				parameterStringBuilder.append("?");
				index++;
			}

			String tableName = convention.decodeName(value.getClass()
					.getSimpleName());

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("insert into ");
			stringBuilder.append(tableName);
			stringBuilder.append(" (");
			stringBuilder.append(fieldStringBuilder.toString());
			stringBuilder.append(" ) values (");
			stringBuilder.append(parameterStringBuilder.toString());
			stringBuilder.append(" )");

			result = stringBuilder.toString();
		}

		return result;
	}

	@Override
	public String updateImplement(AbstractDatabasePojo value) {
		String result = null;

		Convention convention = new Convention();
		List<Method> properties = convention.filterGetMethod(value);

		if (properties.size() > 0) {
			StringBuilder fieldStringBuilder = new StringBuilder();
			int index = 0;
			for (int i = 0; i < properties.size(); i++) {
				Method property = properties.get(i);
				String propertyName = convention.decodeGetMethodName(property
						.getName());
				String fieldName = convention.decodeName(propertyName);

				if (!AbstractDatabasePojo.UUID.equalsIgnoreCase(fieldName)) {
					if (index > 0) {
						fieldStringBuilder.append(", ");
					}
					fieldStringBuilder.append(fieldName);
					fieldStringBuilder.append(" = ?");
					index++;
				}
			}

			String tableName = convention.decodeName(value.getClass()
					.getSimpleName());

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("update ");
			stringBuilder.append(tableName);
			stringBuilder.append(" set ");
			stringBuilder.append(fieldStringBuilder.toString());
			stringBuilder.append(" where ");
			stringBuilder.append(AbstractDatabasePojo.UUID);
			stringBuilder.append(" = ?");

			result = stringBuilder.toString();
		}

		return result;
	}

	@Override
	public String delete(String tableName) {
		String result = null;

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("delete from ");
		stringBuilder.append(tableName);
		stringBuilder.append(" where ");
		stringBuilder.append(AbstractDatabasePojo.UUID);
		stringBuilder.append(" = ?");

		result = stringBuilder.toString();

		return result;
	}
}
