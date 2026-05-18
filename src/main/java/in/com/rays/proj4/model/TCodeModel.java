package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.rays.proj4.bean.TCodeBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

public class TCodeModel {

	// ========================== NEXT PK ==========================

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from T_code");

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

	public long add(TCodeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		TCodeBean existBean = findByLanguage(bean.getLanguage());

		if (existBean != null) {
			throw new DuplicateRecordException("Language already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();

			pk = nextPk();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into T_code values(?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getLanguage());
			pstmt.setString(3, bean.getCodeSnippet());
			pstmt.setString(4, bean.getExecutionTime());
			pstmt.setString(5, bean.getOutputStatus());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			e.printStackTrace();

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Add Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in add TCode");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ========================== UPDATE ==========================

	public void update(TCodeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TCodeBean existBean = findByLanguage(bean.getLanguage());

		if (existBean != null && existBean.getId() != bean.getId()) {

			throw new DuplicateRecordException("Language already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update T_code set language=?, code=?, time=?, status=? where id=?");

			pstmt.setString(1, bean.getLanguage());
			pstmt.setString(2, bean.getCodeSnippet());
			pstmt.setString(3, bean.getExecutionTime());
			pstmt.setString(4, bean.getOutputStatus());
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

			throw new ApplicationException("Exception : Exception in update TCode");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== DELETE ==========================

	public void delete(TCodeBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from T_code where id=?");

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

			throw new ApplicationException("Exception : Exception in delete TCode");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== FIND BY PK ==========================

	public TCodeBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_code where id=?");

		TCodeBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TCodeBean();

				bean.setId(rs.getLong(1));
				bean.setLanguage(rs.getString(2));
				bean.setCodeSnippet(rs.getString(3));
				bean.setExecutionTime(rs.getString(4));
				bean.setOutputStatus(rs.getString(5));
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

	// ========================== FIND BY LANGUAGE ==========================

	public TCodeBean findByLanguage(String language) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_code where language=?");

		TCodeBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, language);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TCodeBean();

				bean.setId(rs.getLong(1));
				bean.setLanguage(rs.getString(2));
				bean.setCodeSnippet(rs.getString(3));
				bean.setExecutionTime(rs.getString(4));
				bean.setOutputStatus(rs.getString(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in findByLanguage");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ========================== LIST ==========================

	public List<TCodeBean> list() throws ApplicationException {

		return search(null, 0, 0);
	}

	// ========================== SEARCH ==========================

	public List<TCodeBean> search(TCodeBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_code where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getLanguage() != null && bean.getLanguage().length() > 0) {
				sql.append(" and language like '" + bean.getLanguage() + "%'");
			}

			if (bean.getCodeSnippet() != null && bean.getCodeSnippet().length() > 0) {
				sql.append(" and code like '" + bean.getCodeSnippet() + "%'");
			}

			if (bean.getExecutionTime() != null && bean.getExecutionTime().length() > 0) {
				sql.append(" and time like '" + bean.getExecutionTime() + "%'");
			}

			if (bean.getOutputStatus() != null && bean.getOutputStatus().length() > 0) {
				sql.append(" and status like '" + bean.getOutputStatus() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<TCodeBean> list = new ArrayList<TCodeBean>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TCodeBean();

				bean.setId(rs.getLong(1));
				bean.setLanguage(rs.getString(2));
				bean.setCodeSnippet(rs.getString(3));
				bean.setExecutionTime(rs.getString(4));
				bean.setOutputStatus(rs.getString(5));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search TCode");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}