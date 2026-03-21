package in.com.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.com.rays.proj4.bean.RoleBean;
import in.com.rays.proj4.exception.ApplicationException;
import in.com.rays.proj4.exception.DatabaseException;
import in.com.rays.proj4.exception.DuplicateRecordException;
import in.com.rays.proj4.model.RoleModel;

public class TestRole {

	static RoleModel model = new RoleModel();

	public static void main(String[] args) throws Exception {
		// testnextPK();

		//testAdd();
		
		//testDelete();
		
		testUpdate();
		
		//testFindByPK();
		
		//testFindByName();
		
		//testSearch();
	}

	private static void testnextPK() throws DatabaseException {

		long i = model.nextPk();

		System.out.println("Next Pk Is " + i);
	}

	public static void testAdd() throws Exception {

		RoleBean bean = new RoleBean();

		bean.setName("faculty");
		bean.setDescription("faculty");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		long id = model.add(bean);
		System.out.println("record inserted at: " + id);

	}
	
	public static void testDelete() throws ApplicationException{
		
		RoleBean bean = new RoleBean();
		
		bean.setId(1l);
		
		model.delete(bean);
		
		System.out.println("record deleted");
		
	}	
	public static void testUpdate(){
		try{
			RoleBean bean=model.findByPk(3L);
			bean.setName("college");
			bean.setDescription("college");
			model.update(bean);
			
			RoleBean updatedbean=model.findByPk(2L);
			
			if(!"ajay".equals(updatedbean.getName())){
				System.out.println("Test Update fill");
			}
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DuplicateRecordException e){
			e.printStackTrace();
		}
	}
	public static void testFindByPK(){
		try{
		RoleBean bean =new RoleBean();
		long pk=2L;
		bean=model.findByPk(pk);
		if(bean==null){
			System.out.println("Test Find By PK fill");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		
		}catch(ApplicationException e){
			e.printStackTrace();
		}
		
	}
	public static void testFindByName(){
		try{
			RoleBean bean=new RoleBean();
			bean=model.findByName("ankit");
			if(bean==null){
				System.out.println("Test Find By Name fill");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	public static void testSearch(){
		try{
			RoleBean bean=new RoleBean();
			List list=new ArrayList();
			//bean.setName("ankit");
			list=model.search(bean,0,0);
			if(list.size() < 0){
				System.out.println("test Search fill");
			}
			Iterator it = list.iterator();
			
			while(it.hasNext()){
				bean=(RoleBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}


}