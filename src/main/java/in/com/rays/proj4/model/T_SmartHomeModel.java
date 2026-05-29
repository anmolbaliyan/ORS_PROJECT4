package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.rays.proj4.bean.T_SmartHomeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.util.JDBCDataSource;

public class T_SmartHomeModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM T_smarthome");

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

	public long add(T_SmartHomeBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO T_smarthome VALUES(?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getDeviceName());
			pstmt.setString(3, bean.getRoomName());
			pstmt.setString(4, bean.getPowerStatus());
			pstmt.setInt(5, bean.getEnergyUsage());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception in add Smart Home");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(T_SmartHomeBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE T_smarthome SET dname=?, rname=?, status=?, usage=?, WHERE id=?");

			pstmt.setString(1, bean.getDeviceName());
			pstmt.setString(2, bean.getRoomName());
			pstmt.setString(3, bean.getPowerStatus());
			pstmt.setInt(4, bean.getEnergyUsage());
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception in update Smart Home");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(T_SmartHomeBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM T_smarthome WHERE id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception in delete Smart Home");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public T_SmartHomeBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;

		T_SmartHomeBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM T_smarthome WHERE id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new T_SmartHomeBean();

				bean.setId(rs.getLong(1));
				bean.setDeviceName(rs.getString(2));
				bean.setRoomName(rs.getString(3));
				bean.setPowerStatus(rs.getString(4));
				bean.setEnergyUsage(rs.getInt(5));
	

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Find By PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List search(T_SmartHomeBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM T_smarthome WHERE 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

			if (bean.getDeviceName() != null && bean.getDeviceName().length() > 0) {

				sql.append(" AND dname LIKE '" + bean.getDeviceName() + "%'");
			}

			if (bean.getRoomName() != null && bean.getRoomName().length() > 0) {

				sql.append(" AND rname LIKE '" + bean.getRoomName() + "%'");
			}

			if (bean.getPowerStatus() != null && bean.getPowerStatus().length() > 0) {

				sql.append(" AND status LIKE '" + bean.getPowerStatus() + "%'");
			}

			if (bean.getEnergyUsage() > 0) {

				sql.append(" AND usage = " + bean.getEnergyUsage());
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new T_SmartHomeBean();

				bean.setId(rs.getLong(1));
				bean.setDeviceName(rs.getString(2));
				bean.setRoomName(rs.getString(3));
				bean.setPowerStatus(rs.getString(4));
				bean.setEnergyUsage(rs.getInt(5));
				

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}
}