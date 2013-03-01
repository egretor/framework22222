package unknown.website.manage.module;

import java.util.Date;

import unknown.framework.module.pojo.TablePojo;

/**
 * 管理模块表
 */
public abstract class AbstractManageTable extends TablePojo {
	private String insertUserId;
	private Date insertTime;
	private String updateUserId;
	private Date updateTime;
	private String remark;

	/**
	 * 执行新增操作用户编号
	 * 
	 * @return 执行新增操作用户编号
	 */
	public String fgetInsertUserId() {
		return insertUserId;
	}

	public void fsetInsertUserId(String insertUserId) {
		this.insertUserId = insertUserId;
	}

	/**
	 * 新增时间
	 * 
	 * @return 新增时间
	 */
	public Date fgetInsertTime() {
		return insertTime;
	}

	public void fsetInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	/**
	 * 执行更新操作用户编号
	 * 
	 * @return 执行更新操作用户编号
	 */
	public String fgetUpdateUserId() {
		return updateUserId;
	}

	public void fsetUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * 更新时间
	 * 
	 * @return 更新时间
	 */
	public Date fgetUpdateTime() {
		return updateTime;
	}

	public void fsetUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 备注
	 * 
	 * @return 备注
	 */
	public String fgetRemark() {
		return remark;
	}

	public void fsetRemark(String remark) {
		this.remark = remark;
	}

	public String getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(String insertUserId) {
		this.insertUserId = insertUserId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
