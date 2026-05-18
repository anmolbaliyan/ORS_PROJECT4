package in.com.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.com.rays.proj4.bean.TProductBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.util.JDBCDataSource;

public class TProductModel {

	// ========================== NEXT PK ==========================

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from T_product");

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

	public long add(TProductBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		int pk = 0;

		TProductBean existBean = findByName(bean.getProductName());

		if (existBean != null) {
			throw new DuplicateRecordException("Product Name already exists");
		}

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into T_product values(?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getProductName());
			pstmt.setString(3, bean.getBrand());
			pstmt.setDouble(4, bean.getPrice());
			pstmt.setInt(5, bean.getQuantity());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Add Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in add TProduct");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ========================== UPDATE ==========================

	public void update(TProductBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TProductBean existBean = findByName(bean.getProductName());

		if (existBean != null && existBean.getProductId() != bean.getProductId()) {

			throw new DuplicateRecordException("Product Name already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update T_product set name=?, brand=?, price=?, quantity=? where id=?");

			pstmt.setString(1, bean.getProductName());
			pstmt.setString(2, bean.getBrand());
			pstmt.setDouble(3, bean.getPrice());
			pstmt.setInt(4, bean.getQuantity());
			pstmt.setInt(5, bean.getProductId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Update Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in update TProduct");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== DELETE ==========================

	public void delete(TProductBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from T_product where id=?");

			pstmt.setInt(1, bean.getProductId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {

				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Delete Rollback Exception");

			}

			throw new ApplicationException("Exception : Exception in delete TProduct");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	// ========================== FIND BY PK ==========================

	public TProductBean findByPk(int pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_product where id=?");

		TProductBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TProductBean();

				bean.setProductId(rs.getInt(1));
				bean.setProductName(rs.getString(2));
				bean.setBrand(rs.getString(3));
				bean.setPrice(rs.getInt(4));
				bean.setQuantity(rs.getInt(5));
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

	public TProductBean findByName(String name) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_product where name=?");

		TProductBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TProductBean();

				bean.setProductId(rs.getInt(1));
				bean.setProductName(rs.getString(2));
				bean.setBrand(rs.getString(3));
				bean.setPrice(rs.getInt(4));
				bean.setQuantity(rs.getInt(5));
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

	public List<TProductBean> list() throws ApplicationException {

		return search(null, 0, 0);
	}

	// ========================== SEARCH ==========================

	public List<TProductBean> search(TProductBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from T_product where 1=1");

		if (bean != null) {

			if (bean.getProductId() > 0) {
				sql.append(" and id = " + bean.getProductId());
			}

			if (bean.getProductName() != null && bean.getProductName().length() > 0) {

				sql.append(" and name like '" + bean.getProductName() + "%'");
			}

			if (bean.getBrand() != null && bean.getBrand().length() > 0) {

				sql.append(" and brand like '" + bean.getBrand() + "%'");
			}

			if (bean.getPrice() > 0) {

				sql.append(" and price = " + bean.getPrice());
			}

			if (bean.getQuantity() > 0) {

				sql.append(" and quantity = " + bean.getQuantity());
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<TProductBean> list = new ArrayList<TProductBean>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TProductBean();

				bean.setProductId(rs.getInt(1));
				bean.setProductName(rs.getString(2));
				bean.setBrand(rs.getString(3));
				bean.setPrice(rs.getInt(4));
				bean.setQuantity(rs.getInt(5));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search TProduct");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}