package unknown.framework.module.database;

/**
 * 分页
 */
public class Paging {
	/**
	 * 最小页数
	 */
	public final static int MINIMUM_PAGE = 1;
	/**
	 * 每页最小行数
	 */
	public final static int MINIMUM_ROWS_PER_PAGE = 1;
	/**
	 * 每页最大行数
	 */
	public final static int MAXIMUM_ROWS_PER_PAGE = Integer.MAX_VALUE;

	private int currentPage = Paging.MINIMUM_PAGE;
	private int rowsPerPage = Paging.MAXIMUM_ROWS_PER_PAGE;
	private int rowCount = 0;

	/**
	 * 当前页第一行
	 * 
	 * @return 当前页第一行
	 */
	public int getCurrentPageFirstRow() {
		int result = 1;

		result = ((currentPage - 1) * rowsPerPage) + 1;

		return result;
	}

	/**
	 * 总页数
	 * 
	 * @return 总页数
	 */
	public int getPageCount() {
		int result = 1;

		result = this.rowCount / this.rowsPerPage;
		int remainder = this.rowCount % this.rowsPerPage;
		if (remainder > 0) {
			result++;
		}

		return result;
	}

	/**
	 * 当前页
	 * 
	 * @return 当前页
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage >= Paging.MINIMUM_PAGE) {
			this.currentPage = currentPage;
		}
	}

	/**
	 * 每页行数
	 * 
	 * @return 每页行数
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		if (rowsPerPage >= Paging.MINIMUM_ROWS_PER_PAGE) {
			this.rowsPerPage = rowsPerPage;
		}
	}

	/**
	 * 总行数
	 * 
	 * @return 总行数
	 */
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

}
