package in.com.rays.proj4.bean;

/**
 * CollegeBean represents a college entity in the application.
 * It contains college-related information such as name, address,
 * state, city, and contact number.
 * <p>
 * This bean extends {@link BaseBean} and inherits common attributes
 * such as id, createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * </p>
 *
 * @author Anmol Kumar Baliyan
 */
public class CollegeBean extends BaseBean {

	/**
	 * Name of the college.
	 */
	private String name;

	/**
	 * Address of the college.
	 */
	private String address;

	/**
	 * State in which the college is located.
	 */
	private String state;

	/**
	 * City in which the college is located.
	 */
	private String city;

	/**
	 * Contact phone number of the college.
	 */
	private String phoneNo;

	/**
	 * Returns the college name.
	 *
	 * @return the college name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the college name.
	 *
	 * @param name the college name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the address of the college.
	 *
	 * @return the college address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address of the college.
	 *
	 * @param address the college address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns the state of the college.
	 *
	 * @return the state name
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state of the college.
	 *
	 * @param state the state name to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Returns the city of the college.
	 *
	 * @return the city name
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city of the college.
	 *
	 * @param city the city name to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Returns the contact phone number of the college.
	 *
	 * @return the phone number
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * Sets the contact phone number of the college.
	 *
	 * @param phoneNo the phone number to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * Returns the display value of the bean for dropdown lists.
	 *
	 * @return the display value of the college
	 */
	@Override
	public String getValue() {
		return null;
	}
}