package in.com.rays.proj4.bean;

/**
 * SubjectBean represents a subject entity in the application.
 * It contains subject details such as subject name, associated
 * course information, and description.
 * <p>
 * This bean extends {@link BaseBean} and inherits common attributes
 * such as id, createdBy, modifiedBy, createdDatetime, and
 * modifiedDatetime.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public class SubjectBean extends BaseBean {

	/**
	 * Name of the subject.
	 */
	private String name;

	/**
	 * Unique identifier of the associated course.
	 */
	private long courseId;

	/**
	 * Name of the associated course.
	 */
	private String courseName;

	/**
	 * Description of the subject.
	 */
	private String description;

	/**
	 * Returns the name of the subject.
	 *
	 * @return the subject name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the subject.
	 *
	 * @param name the subject name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the associated course identifier.
	 *
	 * @return the course id
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the associated course identifier.
	 *
	 * @param courseId the course id to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Returns the associated course name.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the associated course name.
	 *
	 * @param courseName the course name to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the description of the subject.
	 *
	 * @return the subject description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the subject.
	 *
	 * @param description the subject description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the display value of the subject bean for dropdown lists.
	 *
	 * @return the display value
	 */
	@Override
	public String getValue() {
		return null;
	}

}