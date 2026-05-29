package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.com.rays.proj4.bean.THospitalSystemBean;
import in.com.rays.proj4.bean.UserBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

public class THospitalSystemModel {

	Logger log = Logger.getLogger(THospitalSystemModel.class);

	public Integer nextPk() throws DatabaseException {

		log.debug("Model nextPk Started");

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from T_hospital");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new DatabaseException("Exception in getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model nextPk End");

		return pk + 1;
	}

	public long add(THospitalSystemBean bean) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");

		Connection conn = null;
		int pk = 0;
		
		THospitalSystemBean existbean = findByRoomNumber(bean.getRoomNumber());

		if (existbean != null) {

			log.error("Duplicate Room Number");

			throw new DuplicateRecordException("Room Number already exists");
		}

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into T_hospital values(?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getPatientName());
			pstmt.setString(3, bean.getDoctorName());
			pstmt.setString(4, bean.getDisease());
			pstmt.setInt(5, bean.getRoomNumber());
			

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in add()", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			throw new ApplicationException("Exception in add Hospital System");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model add End");

		return pk;
	}

	public void update(THospitalSystemBean bean) throws ApplicationException {

		log.debug("Model update Started");

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update T_hospital set pname=?, dname=?, disease=?, roomno=? where id=?");

			pstmt.setString(1, bean.getPatientName());
			pstmt.setString(2, bean.getDoctorName());
			pstmt.setString(3, bean.getDisease());
			pstmt.setInt(4, bean.getRoomNumber());
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in update()", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			throw new ApplicationException("Exception in updating Hospital System");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model update End");
	}

	public void delete(THospitalSystemBean bean) throws ApplicationException {

		log.debug("Model delete Started");

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from T_hospital where id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in delete()", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			throw new ApplicationException("Exception in delete Hospital System");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model delete End");
	}

	public THospitalSystemBean findByPk(long pk) throws ApplicationException {

		log.debug("Model findByPk Started");

		StringBuffer sql = new StringBuffer("select * from T_hospital where id=?");

		THospitalSystemBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new THospitalSystemBean();

				bean.setId(rs.getLong(1));
				bean.setPatientName(rs.getString(2));
				bean.setDoctorName(rs.getString(3));
				bean.setDisease(rs.getString(4));
				bean.setRoomNumber(rs.getInt(5));
				
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in findByPk()", e);

			throw new ApplicationException("Exception in getting Hospital System by PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model findByPk End");

		return bean;
	}
	
	public THospitalSystemBean findByRoomNumber(int roomNumber)
			throws ApplicationException {

		log.debug("Model findByRoomNumber Started");

		StringBuffer sql = new StringBuffer(
				"select * from T_hospital where roomno=?");

		THospitalSystemBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, roomNumber);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new THospitalSystemBean();

				bean.setId(rs.getLong(1));
				bean.setPatientName(rs.getString(2));
				bean.setDoctorName(rs.getString(3));
				bean.setDisease(rs.getString(4));
				bean.setRoomNumber(rs.getInt(5));
				

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in findByRoomNumber()", e);

			throw new ApplicationException(
					"Exception in getting Hospital System by Room Number");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model findByRoomNumber End");

		return bean;
	}

	public List search(THospitalSystemBean bean, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");

		Connection conn = null;

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from T_hospital where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getPatientName() != null && bean.getPatientName().length() > 0) {

				sql.append(" and pname like '" + bean.getPatientName() + "%'");
			}

			if (bean.getDoctorName() != null && bean.getDoctorName().length() > 0) {

				sql.append(" and dname like '" + bean.getDoctorName() + "%'");
			}

			if (bean.getDisease() != null && bean.getDisease().length() > 0) {

				sql.append(" and disease like '" + bean.getDisease() + "%'");
			}

			if (bean.getRoomNumber() > 0) {

				sql.append(" and roomno = " + bean.getRoomNumber());
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

				bean = new THospitalSystemBean();

				bean.setId(rs.getLong(1));
				bean.setPatientName(rs.getString(2));
				bean.setDoctorName(rs.getString(3));
				bean.setDisease(rs.getString(4));
				bean.setRoomNumber(rs.getInt(5));
				
				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in search()", e);

			throw new ApplicationException("Exception in search Hospital System");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");

		return list;
	}
}