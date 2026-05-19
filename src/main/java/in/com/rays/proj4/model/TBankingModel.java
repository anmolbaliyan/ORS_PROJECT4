package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.rays.proj4.bean.TBankingBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

public class TBankingModel {

	// ========================== NEXT PK ==========================

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from T_banking");

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

	public long add(TBankingBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		int pk = 0;

		TBankingBean existBean = findByName(bean.getHolderName());

		if (existBean != null) {
			throw new DuplicateRecordException("Holder Name already exists");
		}

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into T_banking values(?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getHolderName());
			pstmt.setInt(3, bean.getBalance());
			pstmt.setString(4, bean.getAccountType());
			pstmt.setInt(5, bean.getTransactionId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Add Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in add TBanking");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ========================== UPDATE ==========================

	public void update(TBankingBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TBankingBean existBean = findByName(bean.getHolderName());

		if (existBean != null && existBean.getAccountNo() != bean.getAccountNo()) {

			throw new DuplicateRecordException("Holder Name already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update T_banking set name=?, balance=?, type=?, tranx=? where id=?");

			pstmt.setString(1, bean.getHolderName());
			pstmt.setInt(2, bean.getBalance());
			pstmt.setString(3, bean.getAccountType());
			pstmt.setInt(4, bean.getTransactionId());
			pstmt.setLong(5, bean.getAccountNo());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Update Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in update TBanking");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== DELETE ==========================

	public void delete(TBankingBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from T_banking where id=?");

			pstmt.setLong(1, bean.getAccountNo());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Delete Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in delete TBanking");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== FIND BY PK ==========================

	public TBankingBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_banking where id=?");

		TBankingBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TBankingBean();

				bean.setAccountNo(rs.getLong(1));
				bean.setHolderName(rs.getString(2));
				bean.setBalance(rs.getInt(3));
				bean.setAccountType(rs.getString(4));
				bean.setTransactionId(rs.getInt(5));
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

	// ========================== FIND BY NAME ==========================

	public TBankingBean findByName(String name) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_banking where name=?");

		TBankingBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TBankingBean();

				bean.setAccountNo(rs.getLong(1));
				bean.setHolderName(rs.getString(2));
				bean.setBalance(rs.getInt(3));
				bean.setAccountType(rs.getString(4));
				bean.setTransactionId(rs.getInt(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in findByName");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ========================== LIST ==========================

	public List<TBankingBean> list() throws ApplicationException {

		return search(null, 0, 0);
	}

	// ========================== SEARCH ==========================

	public List<TBankingBean> search(TBankingBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_banking where 1=1");

		if (bean != null) {

			if (bean.getAccountNo() > 0) {
				sql.append(" and id = " + bean.getAccountNo());
			}

			if (bean.getHolderName() != null && bean.getHolderName().length() > 0) {

				sql.append(" and name like '" + bean.getHolderName() + "%'");
			}

			if (bean.getBalance() > 0) {

				sql.append(" and balance = " + bean.getBalance());
			}

			if (bean.getAccountType() != null && bean.getAccountType().length() > 0) {

				sql.append(" and type like '" + bean.getAccountType() + "%'");
			}

			if (bean.getTransactionId() > 0) {

				sql.append(" and tranx = " + bean.getTransactionId());
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<TBankingBean> list = new ArrayList<TBankingBean>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TBankingBean();

				bean.setAccountNo(rs.getLong(1));
				bean.setHolderName(rs.getString(2));
				bean.setBalance(rs.getInt(3));
				bean.setAccountType(rs.getString(4));
				bean.setTransactionId(rs.getInt(5));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search TBanking");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}