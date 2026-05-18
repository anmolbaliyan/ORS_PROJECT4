package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.rays.proj4.bean.TSessionBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

public class TSessionModel {

	// ========================== NEXT PK ==========================

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from T_session");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// ========================== ADD ==========================

	public long add(TSessionBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		TSessionBean duplicateBean = findBySessionCode(bean.getSessionCode());

		if (duplicateBean != null) {
			throw new DuplicateRecordException("Session Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();

			pk = nextPk();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into T_session values(?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getSessionCode());
			pstmt.setString(3, bean.getUserName());
			pstmt.setDate(4, new java.sql.Date(bean.getLoginTime().getTime()));
			pstmt.setString(5, bean.getStatus());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			e.printStackTrace();
			try {

				conn.rollback();

			} catch (Exception ex) {

				ex.printStackTrace();

				throw new ApplicationException("Exception : Add Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in add TSession");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ========================== UPDATE ==========================

	public void update(TSessionBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TSessionBean duplicateBean = findBySessionCode(bean.getSessionCode());

		if (duplicateBean != null && duplicateBean.getId() != bean.getId()) {

			throw new DuplicateRecordException("Session Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update T_session set code=?, name=?, time=?, status=? where id=?");

			pstmt.setString(1, bean.getSessionCode());
			pstmt.setString(2, bean.getUserName());
			pstmt.setDate(3, new java.sql.Date(bean.getLoginTime().getTime()));
			pstmt.setString(4, bean.getStatus());
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Update Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in update TSession");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== DELETE ==========================

	public void delete(TSessionBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from T_session where id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Delete Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in delete TSession");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== FIND BY PK ==========================

	public TSessionBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_session where id=?");

		TSessionBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TSessionBean();

				bean.setId(rs.getLong(1));
				bean.setSessionCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setLoginTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in findByPk");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ========================== FIND BY SESSION CODE ==========================

	public TSessionBean findBySessionCode(String code) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_session where code=?");

		TSessionBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, code);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TSessionBean();

				bean.setId(rs.getLong(1));
				bean.setSessionCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setLoginTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in findBySessionCode");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ========================== LIST ==========================

	public List<TSessionBean> list() throws ApplicationException {

		return search(null, 0, 0);
	}

	// ========================== SEARCH ==========================

	public List<TSessionBean> search(TSessionBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_session where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getSessionCode() != null && bean.getSessionCode().length() > 0) {

				sql.append(" and code like '" + bean.getSessionCode() + "%'");
			}

			if (bean.getUserName() != null && bean.getUserName().length() > 0) {

				sql.append(" and name like '" + bean.getUserName() + "%'");
			}

			if (bean.getLoginTime() != null && bean.getLoginTime().getTime() > 0) {

				sql.append(" and time like '" + bean.getLoginTime() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {

				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<TSessionBean> list = new ArrayList<TSessionBean>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TSessionBean();

				bean.setId(rs.getLong(1));
				bean.setSessionCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setLoginTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search TSession");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}