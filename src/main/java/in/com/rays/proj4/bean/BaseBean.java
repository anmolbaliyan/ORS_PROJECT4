package in.com.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * BaseBean is an abstract parent bean that contains common attributes
 * used by all application beans such as id, audit information,
 * and creation/modification timestamps.
 * <p>
 * It also provides a default implementation of the
 * {@link DropdownListBean#getKey()} method.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public abstract class BaseBean implements DropdownListBean {

	/**
	 * Unique identifier of the bean.
	 */
	protected long id;

	/**
	 * Name of the user who created the record.
	 */
	protected String createdBy;

	/**
	 * Name of the user who last modified the record.
	 */
	protected String modifiedBy;

	/**
	 * Date and time when the record was created.
	 */
	protected Timestamp createdDatetime;

	/**
	 * Date and time when the record was last modified.
	 */
	protected Timestamp modifiedDatetime;

	/**
	 * Returns the unique identifier of the bean.
	 *
	 * @return the id of the bean
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the bean.
	 *
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the creator of the record.
	 *
	 * @return the name of the creator
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the creator of the record.
	 *
	 * @param createdBy the creator name
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Returns the name of the user who last modified the record.
	 *
	 * @return the modifier name
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the name of the user who last modified the record.
	 *
	 * @param modifiedBy the modifier name
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Returns the creation date and time of the record.
	 *
	 * @return the creation timestamp
	 */
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * Sets the creation date and time of the record.
	 *
	 * @param createdDatetime the creation timestamp
	 */
	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	/**
	 * Returns the last modification date and time of the record.
	 *
	 * @return the modification timestamp
	 */
	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	/**
	 * Sets the last modification date and time of the record.
	 *
	 * @param modifiedDatetime the modification timestamp
	 */
	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	/**
	 * Returns the key value of the bean for dropdown lists.
	 * The bean id is used as the key.
	 *
	 * @return the bean id as a String
	 */
	@Override
	public String getKey() {
		return id + "";
	}
}