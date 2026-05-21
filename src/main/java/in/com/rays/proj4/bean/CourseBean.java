package in.com.rays.proj4.bean;

/**
 * CourseBean represents a course entity in the application.
 * It contains course-related information such as course name,
 * duration, and description.
 * <p>
 * This bean extends {@link BaseBean} and inherits common attributes
 * like id, createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public class CourseBean extends BaseBean {

	/**
	 * Name of the course.
	 */
	private String name;

	/**
	 * Duration of the course.
	 */
	private String duration;

	/**
	 * Description of the course.
	 */
	private String description;

	/**
	 * Returns the course name.
	 *
	 * @return the course name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the course name.
	 *
	 * @param name the course name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the duration of the course.
	 *
	 * @return the course duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the course.
	 *
	 * @param duration the course duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Returns the description of the course.
	 *
	 * @return the course description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the course.
	 *
	 * @param description the course description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the display value of the course for dropdown lists.
	 * The course name is used as the display value.
	 *
	 * @return the course name
	 */
	@Override
	public String getValue() {
		return name;
	}
}