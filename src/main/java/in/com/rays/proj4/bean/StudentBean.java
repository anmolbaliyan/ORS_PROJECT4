package in.com.rays.proj4.bean;

import java.util.Date;

/**
 * StudentBean represents a student entity in the application.
 * It contains personal information and associated college details
 * of a student.
 * <p>
 * This bean extends {@link BaseBean} and inherits common attributes
 * such as id, createdBy, modifiedBy, createdDatetime, and
 * modifiedDatetime.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public class StudentBean extends BaseBean {

	/**
	 * First name of the student.
	 */
	private String firstName;

	/**
	 * Last name of the student.
	 */
	private String lastName;

	/**
	 * Date of birth of the student.
	 */
	private Date dob;

	/**
	 * Gender of the student.
	 */
	private String gender;

	/**
	 * Mobile number of the student.
	 */
	private String mobileNo;

	/**
	 * Email address of the student.
	 */
	private String email;

	/**
	 * Unique identifier of the associated college.
	 */
	private long collegeId;

	/**
	 * Name of the associated college.
	 */
	private String collegeName;

	/**
	 * Returns the first name of the student.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the student.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the student.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the student.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the date of birth of the student.
	 *
	 * @return the date of birth
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the student.
	 *
	 * @param dob the date of birth to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Returns the gender of the student.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the student.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Returns the mobile number of the student.
	 *
	 * @return the mobile number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the student.
	 *
	 * @param mobileNo the mobile number to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Returns the email address of the student.
	 *
	 * @return the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the student.
	 *
	 * @param email the email address to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the associated college identifier.
	 *
	 * @return the college id
	 */
	public long getCollegeId() {
		return collegeId;
	}

	/**
	 * Sets the associated college identifier.
	 *
	 * @param collegeId the college id to set
	 */
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	/**
	 * Returns the associated college name.
	 *
	 * @return the college name
	 */
	public String getCollegeName() {
		return collegeName;
	}

	/**
	 * Sets the associated college name.
	 *
	 * @param collegeName the college name to set
	 */
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	/**
	 * Returns the display value of the student bean for dropdown lists.
	 *
	 * @return the display value
	 */
	@Override
	public String getValue() {
		return null;
	}

}