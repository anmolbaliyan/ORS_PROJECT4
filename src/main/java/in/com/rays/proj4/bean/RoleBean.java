package in.com.rays.proj4.bean;

/**
 * RoleBean represents a user role in the application.
 * It contains role-related information such as role name
 * and description. This bean is used for role-based access
 * control and authorization.
 * <p>
 * This bean extends {@link BaseBean} and inherits common
 * attributes such as id, createdBy, modifiedBy,
 * createdDatetime, and modifiedDatetime.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public class RoleBean extends BaseBean {

	/**
	 * Constant representing the Administrator role.
	 */
	public static final int ADMIN = 1;

	/**
	 * Constant representing the Student role.
	 */
	public static final int STUDENT = 2;

	/**
	 * Constant representing the College role.
	 */
	public static final int COLLEGE = 3;

	/**
	 * Constant representing the Kiosk role.
	 */
	public static final int KIOSK = 4;

	/**
	 * Constant representing the Faculty role.
	 */
	public static final int FACULTY = 5;

	/**
	 * Name of the role.
	 */
	private String name;

	/**
	 * Description of the role.
	 */
	private String description;

	/**
	 * Returns the role name.
	 *
	 * @return the role name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the role name.
	 *
	 * @param name the role name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the description of the role.
	 *
	 * @return the role description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the role.
	 *
	 * @param description the role description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the display value of the role for dropdown lists.
	 * The role name is used as the display value.
	 *
	 * @return the role name
	 */
	@Override
	public String getValue() {
		return name;
	}

}