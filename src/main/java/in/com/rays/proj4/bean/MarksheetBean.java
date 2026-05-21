package in.com.rays.proj4.bean;

/**
 * MarksheetBean represents a student's marksheet in the application.
 * It contains student information along with marks obtained in
 * Physics, Chemistry, and Mathematics subjects.
 * <p>
 * This bean extends {@link BaseBean} and inherits common attributes
 * such as id, createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public class MarksheetBean extends BaseBean {

	/**
	 * Roll number of the student.
	 */
	private String rollNo;

	/**
	 * Unique identifier of the student.
	 */
	private long studentId;

	/**
	 * Name of the student.
	 */
	private String name;

	/**
	 * Marks obtained in Physics.
	 */
	private Integer physics;

	/**
	 * Marks obtained in Chemistry.
	 */
	private Integer chemistry;

	/**
	 * Marks obtained in Mathematics.
	 */
	private Integer maths;

	/**
	 * Returns the roll number of the student.
	 *
	 * @return the roll number
	 */
	public String getRollNo() {
		return rollNo;
	}

	/**
	 * Sets the roll number of the student.
	 *
	 * @param rollNo the roll number to set
	 */
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	/**
	 * Returns the student identifier.
	 *
	 * @return the student id
	 */
	public long getStudentId() {
		return studentId;
	}

	/**
	 * Sets the student identifier.
	 *
	 * @param studentId the student id to set
	 */
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	/**
	 * Returns the name of the student.
	 *
	 * @return the student name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the student.
	 *
	 * @param name the student name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the marks obtained in Physics.
	 *
	 * @return the Physics marks
	 */
	public Integer getPhysics() {
		return physics;
	}

	/**
	 * Sets the marks obtained in Physics.
	 *
	 * @param physics the Physics marks to set
	 */
	public void setPhysics(Integer physics) {
		this.physics = physics;
	}

	/**
	 * Returns the marks obtained in Chemistry.
	 *
	 * @return the Chemistry marks
	 */
	public Integer getChemistry() {
		return chemistry;
	}

	/**
	 * Sets the marks obtained in Chemistry.
	 *
	 * @param chemistry the Chemistry marks to set
	 */
	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}

	/**
	 * Returns the marks obtained in Mathematics.
	 *
	 * @return the Mathematics marks
	 */
	public Integer getMaths() {
		return maths;
	}

	/**
	 * Sets the marks obtained in Mathematics.
	 *
	 * @param maths the Mathematics marks to set
	 */
	public void setMaths(Integer maths) {
		this.maths = maths;
	}

	/**
	 * Returns the display value of the marksheet bean for dropdown lists.
	 *
	 * @return the display value
	 */
	@Override
	public String getValue() {
		return null;
	}

}