package unknown.framework.module.database;

/**
 * 执行结果
 */
public class Result {
	private boolean done;
	private Table table;
	private Paging paging;

	/**
	 * 成败
	 * 
	 * @return 成败
	 */
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * 表数据
	 * 
	 * @return 表数据
	 */
	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
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
