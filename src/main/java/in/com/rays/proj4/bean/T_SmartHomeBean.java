package in.com.rays.proj4.bean;

public class T_SmartHomeBean extends BaseBean {

	private String deviceName;
	private String roomName;
	private String powerStatus;
	private int energyUsage;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}

	public int getEnergyUsage() {
		return energyUsage;
	}

	public void setEnergyUsage(int energyUsage) {
		this.energyUsage = energyUsage;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
