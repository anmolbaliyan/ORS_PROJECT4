package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.rays.proj4.bean.TCPasswordBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

public class TCPasswordModel {

	// ========================== NEXT PK ==========================

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from T_password");

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

	public long add(TCPasswordBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		int pk = 0;

		TCPasswordBean existBean = findByCode(bean.getConfirmCode());

		if (existBean != null) {

			throw new DuplicateRecordException("Confirm Code already exists");
		}

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into T_password values(?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getConfirmCode());
			pstmt.setString(3, bean.getUserName());
			pstmt.setString(4, bean.getConfirmValue());
			pstmt.setString(5, bean.getStatus());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				ex.printStackTrace();

				throw new ApplicationException("Exception : Add rollback exception");
			}

			throw new ApplicationException("Exception : Exception in add TCPassword");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ========================== UPDATE ==========================

	public void update(TCPasswordBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TCPasswordBean existBean = findByCode(bean.getConfirmCode());

		if (existBean != null && existBean.getId() != bean.getId()) {

			throw new DuplicateRecordException("Confirm Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update T_password set code=?, name=?, value=?, status=? where id=?");

			pstmt.setString(1, bean.getConfirmCode());
			pstmt.setString(2, bean.getUserName());
			pstmt.setString(3, bean.getConfirmValue());
			pstmt.setString(4, bean.getStatus());
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Update rollback exception");
			}

			throw new ApplicationException("Exception : Exception in update TCPassword");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== DELETE ==========================

	public void delete(TCPasswordBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from T_password where id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Delete rollback exception");
			}

			throw new ApplicationException("Exception : Exception in delete TCPassword");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== FIND BY PK ==========================

	public TCPasswordBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_password where id=?");

		TCPasswordBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TCPasswordBean();

				bean.setId(rs.getLong(1));
				bean.setConfirmCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setConfirmValue(rs.getString(4));
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

	// ========================== FIND BY CODE ==========================

	public TCPasswordBean findByCode(String code) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_password where code=?");

		TCPasswordBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, code);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TCPasswordBean();

				bean.setId(rs.getLong(1));
				bean.setConfirmCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setConfirmValue(rs.getString(4));
				bean.setStatus(rs.getString(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in findByCode");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ========================== LIST ==========================

	public List<TCPasswordBean> list() throws ApplicationException {

		return search(null, 0, 0);
	}

	// ========================== SEARCH ==========================

	public List<TCPasswordBean> search(TCPasswordBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_password where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {

				sql.append(" and id = " + bean.getId());
			}

			if (bean.getConfirmCode() != null && bean.getConfirmCode().length() > 0) {

				sql.append(" and code like '" + bean.getConfirmCode() + "%'");
			}

			if (bean.getUserName() != null && bean.getUserName().length() > 0) {

				sql.append(" and name like '" + bean.getUserName() + "%'");
			}

			if (bean.getConfirmValue() != null && bean.getConfirmValue().length() > 0) {

				sql.append(" and value like '" + bean.getConfirmValue() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {

				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<TCPasswordBean> list = new ArrayList<TCPasswordBean>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TCPasswordBean();

				bean.setId(rs.getLong(1));
				bean.setConfirmCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setConfirmValue(rs.getString(4));
				bean.setStatus(rs.getString(5));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search TCPassword");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}