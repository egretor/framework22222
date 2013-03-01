package unknown.framework.module.pojo;

/**
 * 数据库实体
 * 
 * 值 空值 MySQL Oracle 备注
 * 
 * boolean Boolean BIT NUMBER(1)
 * 
 * int Integer INT NUMBER(16)
 * 
 * long Long BIGINT NUMBER(32)
 * 
 * double Double DOUBLE NUMBER(29,9)
 * 
 * String String TEXT VARCHAR(4000)
 * 
 * String String VARCHAR(32) VARCHAR(32) UUID
 * 
 * String String LONGTEXT CLOB
 * 
 * Date Date DATETIME TIMESTAMP
 */
public abstract class AbstractDatabasePojo {
	/**
	 * UUID
	 */
	public final static String UUID = "uuid";

	private String uuid;

	/**
	 * 
	 * @return 代理主键
	 */
	public String fgetUuid() {
		return uuid;
	}

	public void fsetUuid(String uuid) {
		this.uuid = uuid;
	}
}
