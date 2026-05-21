package in.com.rays.proj4.bean;

/**
 * DropdownListBean is an interface used for populating dropdown lists
 * in the application. Classes implementing this interface provide
 * a key-value pair representation where:
 * <ul>
 * <li>Key represents the unique identifier of the item.</li>
 * <li>Value represents the display text shown in the dropdown.</li>
 * </ul>
 *
 * @author Anmol Kumar Baliyan
 */
public interface DropdownListBean {

	/**
	 * Returns the unique key of the dropdown item.
	 *
	 * @return the key as a String
	 */
	public String getKey();

	/**
	 * Returns the display value of the dropdown item.
	 *
	 * @return the value as a String
	 */
	public String getValue();

}