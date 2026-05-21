package in.com.rays.proj4.bean;

import java.util.Date;

/**
 * FacultyBean represents a faculty member in the application.
 * It stores personal details as well as associated college,
 * course, and subject information.
 * <p>
 * This bean extends {@link BaseBean} and inherits common attributes
 * such as id, createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public class FacultyBean extends BaseBean {

	/**
	 * First name of the faculty member.
	 */
	private String firstName;

	/**
	 * Last name of the faculty member.
	 */
	private String lastName;

	/**
	 * Date of birth of the faculty member.
	 */
	private Date dob;

	/**
	 * Gender of the faculty member.
	 */
	private String gender;

	/**
	 * Mobile number of the faculty member.
	 */
	private String mobileNo;

	/**
	 * Email address of the faculty member.
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
	 * Unique identifier of the associated course.
	 */
	private long courseId;

	/**
	 * Name of the associated course.
	 */
	private String courseName;

	/**
	 * Unique identifier of the associated subject.
	 */
	private long subjectId;

	/**
	 * Name of the associated subject.
	 */
	private String subjectName;

	/**
	 * Returns the first name of the faculty member.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the faculty member.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name of the faculty member.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the faculty member.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the date of birth of the faculty member.
	 *
	 * @return the date of birth
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the faculty member.
	 *
	 * @param dob the date of birth to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Returns the gender of the faculty member.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the faculty member.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Returns the mobile number of the faculty member.
	 *
	 * @return the mobile number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the faculty member.
	 *
	 * @param mobileNo the mobile number to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Returns the email address of the faculty member.
	 *
	 * @return the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the faculty member.
	 *
	 * @param email the email address to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the college identifier.
	 *
	 * @return the college id
	 */
	public long getCollegeId() {
		return collegeId;
	}

	/**
	 * Sets the college identifier.
	 *
	 * @param collegeId the college id to set
	 */
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	/**
	 * Returns the college name.
	 *
	 * @return the college name
	 */
	public String getCollegeName() {
		return collegeName;
	}

	/**
	 * Sets the college name.
	 *
	 * @param collegeName the college name to set
	 */
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	/**
	 * Returns the course identifier.
	 *
	 * @return the course id
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course identifier.
	 *
	 * @param courseId the course id to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Returns the course name.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name.
	 *
	 * @param courseName the course name to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the subject identifier.
	 *
	 * @return the subject id
	 */
	public long getSubjectId() {
		return subjectId;
	}

	/**
	 * Sets the subject identifier.
	 *
	 * @param subjectId the subject id to set
	 */
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Returns the subject name.
	 *
	 * @return the subject name
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets the subject name.
	 *
	 * @param subjectName the subject name to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Returns the display value of the faculty bean for dropdown lists.
	 *
	 * @return the display value
	 */
	@Override
	public String getValue() {
		return null;
	}

}