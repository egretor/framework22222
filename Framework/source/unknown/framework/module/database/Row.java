package unknown.framework.module.database;

import java.util.List;

/**
 * 数据库行数据
 */
public class Row {
	private List<Object> values;

	/**
	 * 数据集合
	 * @return 数据集合
	 */
	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}
}
