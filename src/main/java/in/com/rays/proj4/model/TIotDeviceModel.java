package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.rays.proj4.bean.TIotDeviceBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

public class TIotDeviceModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from T_iotdevice");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public long add(TIotDeviceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		TIotDeviceBean existBean = findByPk(bean.getDeviceId());

		if (existBean != null) {
			throw new DuplicateRecordException("Device Id already exists");
		}

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into T_iotdevice values(?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getDeviceName());
			pstmt.setString(3, bean.getSensorType());
			pstmt.setString(4, bean.getStatus());
			pstmt.setInt(5, bean.getBatteryLevel());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add Rollback Exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in Add Device");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(TIotDeviceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update T_iotdevice set name=?, type=?, status=?, level=? where id=?");

			pstmt.setString(1, bean.getDeviceName());
			pstmt.setString(2, bean.getSensorType());
			pstmt.setString(3, bean.getStatus());
			pstmt.setInt(4, bean.getBatteryLevel());
			pstmt.setLong(5, bean.getDeviceId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update Rollback Exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in Update Device");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(TIotDeviceBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from T_iotdevice where id=?");

			pstmt.setLong(1, bean.getDeviceId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete Rollback Exception");
			}

			throw new ApplicationException("Exception in Delete Device");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public TIotDeviceBean findByPk(long pk) throws ApplicationException {

		TIotDeviceBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from T_iotdevice where id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TIotDeviceBean();

				bean.setDeviceId(rs.getLong(1));
				bean.setDeviceName(rs.getString(2));
				bean.setSensorType(rs.getString(3));
				bean.setStatus(rs.getString(4));
				bean.setBatteryLevel(rs.getInt(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List search(TIotDeviceBean bean, int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from T_iotdevice where 1=1");

		if (bean != null) {

			if (bean.getDeviceId() > 0) {
				sql.append(" and id=" + bean.getDeviceId());
			}

			if (bean.getDeviceName() != null && bean.getDeviceName().length() > 0) {
				sql.append(" and name like '" + bean.getDeviceName() + "%'");
			}

			if (bean.getSensorType() != null && bean.getSensorType().length() > 0) {
				sql.append(" and type like '" + bean.getSensorType() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TIotDeviceBean();

				bean.setDeviceId(rs.getLong(1));
				bean.setDeviceName(rs.getString(2));
				bean.setSensorType(rs.getString(3));
				bean.setStatus(rs.getString(4));
				bean.setBatteryLevel(rs.getInt(5));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Search Device");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}