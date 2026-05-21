package in.com.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean represents a user of the application.
 * It contains personal details, login credentials,
 * role information, and other user-related attributes.
 * <p>
 * This bean extends {@link BaseBean} and inherits common attributes
 * such as id, createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public class UserBean extends BaseBean {

	/**
	 * First name of the user.
	 */
	private String firstName;

	/**
	 * Last name of the user.
	 */
	private String lastName;

	/**
	 * Login ID or username of the user.
	 */
	private String login;

	/**
	 * Password of the user.
	 */
	private String password;

	/**
	 * Confirmation password used during registration or password update.
	 */
	private String confirmPassword;

	/**
	 * Date of birth of the user.
	 */
	private Date dob;

	/**
	 * Mobile number of the user.
	 */
	private String mobileNo;

	/**
	 * Role identifier assigned to the user.
	 */
	private long roleId;

	/**
	 * Gender of the user.
	 */
	private String gender;

	/**
	 * Returns the first name of the user.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the user.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the user.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the login ID of the user.
	 *
	 * @return the login ID
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login ID of the user.
	 *
	 * @param login the login ID to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Returns the password of the user.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the confirmation password.
	 *
	 * @return the confirmation password
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Sets the confirmation password.
	 *
	 * @param confirmPassword the confirmation password to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Returns the date of birth of the user.
	 *
	 * @return the date of birth
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the user.
	 *
	 * @param dob the date of birth to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Returns the mobile number of the user.
	 *
	 * @return the mobile number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the user.
	 *
	 * @param mobileNo the mobile number to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Returns the role identifier of the user.
	 *
	 * @return the role id
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * Sets the role identifier of the user.
	 *
	 * @param roleId the role id to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Returns the gender of the user.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the user.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Returns the display value of the user bean for dropdown lists.
	 *
	 * @return the display value
	 */
	@Override
	public String getValue() {
		return null;
	}

}