package com.user.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import com.file.io.UserHandle;
import com.sql.dao.impl.JdbcTool;
import com.sql.dao.impl.SqlDaoImpl;
import com.user.entity.UserInfo;
import com.user.entity.UserPage;

public class UserService {
	/*
	public static void main(String[] args) {
		UserService uService =new UserService();
		UserPage uPage = uService.paging(10, 1,"createtime","asc");
	
		String jsonString = JSON.toJSONString(uPage.getobjList());
		System.out.println(jsonString);
		System.out.println("当前页"+uPage.getCurrentPage());
		System.out.println("页面大小"+uPage.getPageSize());
		System.out.println("一共几页"+uPage.getTotalPage());
		System.out.println("表中几行数据"+uPage.getTotalCount());

	}*/

		//查询所有用户返回给adminUser.html(默认创建时间早到晚排序)
		//pageSize:分页 每页几行
	    //currentPage:当前第几页
	    //oBy:根据哪一列排序
	   //upDonw:升序还是降序，指定asc或desc
	public UserPage paging(int pageSize,int currentPage,String oBy,String upDonw) {
		SqlDaoImpl goldDao = new SqlDaoImpl();
		List<Object> objList = new ArrayList<Object>();
		int totalCount = goldDao.getCount("userinfo"); //总条数
		UserPage userPage = new UserPage();//分页辅助类
		userPage.setTotalCount(totalCount);
		userPage.setPageSize(pageSize);
		userPage.setCurrentPage(currentPage);
		String sql="select * from"
				+ "(select rownum rn,e1.* from"
				+ "(select * from userinfo  order by "+oBy+" "+upDonw+")"
						+ " e1 where rownum<5000)   where rn>=? and rn<=? ";

		Object[] params= {(currentPage-1)*pageSize+1,currentPage*pageSize};
		ResultSet rs = goldDao.queryData(sql, params);
			try {
				while (rs.next()) {
					UserInfo uInfo = new UserInfo();
					uInfo.setRownum(rs.getInt("rn"));
					uInfo.setUserId(rs.getString("userid"));
					uInfo.setUname(rs.getString("uname"));
					switch (rs.getString("upower")) {
					case "supreme":
						uInfo.setUpower("超级管理员");
						break;
					case "client":
						uInfo.setUpower("普通用户");
						break;
					case "test":
						uInfo.setUpower("试验性数据");
						break;
					}
					String headurl = rs.getString("headurl");
					headurl = headurl.replaceAll("D:/gold", "/golddisk");
					uInfo.setHeadurl(headurl);
					uInfo.setCreateTime(rs.getString("createtime"));
					objList.add(uInfo);
				}
				userPage.setobjList(objList);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				JdbcTool.close(rs, JdbcTool.pstmt, JdbcTool.conn);
			}
		return userPage;
	}
	
	//根据传过来的账号密码,查询单个用户.并返回所有信息
	public UserInfo queryUser(String userId,String upwd) {
		String sql ="select * from userinfo where userid=? and upwd=?";
		Object[] params= {userId,upwd};
		SqlDaoImpl goldDao = new SqlDaoImpl();
		ResultSet rs = goldDao.queryData(sql, params);
		UserInfo uInfo = new UserInfo();
		 try {
			while (rs.next()) {
				uInfo.setUserId(rs.getString("userid"));
				uInfo.setUname(rs.getString("uname"));
				uInfo.setUpwd(rs.getString("upwd"));
				uInfo.setUpower(rs.getString("upower"));
				uInfo.setCreateTime(rs.getString("createtime"));
				String headurl = rs.getString("headurl");
				headurl = headurl.replaceAll("D:/gold", "/golddisk");
				uInfo.setHeadurl(headurl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcTool.close(rs, JdbcTool.pstmt, JdbcTool.conn);
		}
		return uInfo;
	}
	
	
	//是否存在同名userName
	public boolean isexistUid(String uId) {
		String sql = "select count(1) from userinfo where userid =?";
		Object[] params= {uId};
		SqlDaoImpl goldDao = new SqlDaoImpl();
		return goldDao.queryExist(sql, params); //存在返回true
	}
	
	//是否存在同名uname
	public boolean isexistUname(String uname) {
		String sql = "select count(1) from userinfo where uname =?";
		Object[] params= {uname};
		SqlDaoImpl goldDao = new SqlDaoImpl();
		return goldDao.queryExist(sql, params); //存在返回true
	}
	
	public String addUser(String uId,String uname,String upwd) {
		UserService uService = new UserService();
		if (uService.isexistUid(uId)) {
			return "账号已存在";
		}else if (uService.isexistUname(uname)) {
			return "昵称已存在";
		}else {
			String sql ="insert into userinfo(userid,uname,upwd,upower) "
					+ "values(?,?,?,'client')";
			Object[] params= {uId,uname,upwd};
			SqlDaoImpl goldDao = new SqlDaoImpl();
			int count = goldDao.addGoldInfo(sql, params);
			if (count!=0) {
				return "注册成功";
			}
		}
		return "注册失败";	
	}
	
	
		/*更新头像,
		 *  查看数据库:如果url是默认头像则只替换地址
		 *  如果url不是默认头像则先删除旧头像,再把新的url替换旧的
		 * 
		 */
		public boolean updateHeadPhoto(String userId,String imgName, FileItem fileItem) {
				
				String newPath = "D:/gold/userHeadPhoto"+"/"+userId+imgName;
				String sql ="select userid,headurl from userinfo where userid=?";
				Object[] params= {userId};
				SqlDaoImpl goldDao = new SqlDaoImpl();
			ResultSet rs = goldDao.queryData(sql, params);
			UserInfo uInfo = new UserInfo();
			try {
				while (rs.next()) {
					uInfo.setUserId(rs.getString("userId"));
					uInfo.setHeadurl(rs.getString("headurl"));
				}
				UserHandle uHandle = new UserHandle();
				//替换旧头像
				boolean replaceSucess = uHandle.replaceImg(uInfo.getHeadurl(),newPath,fileItem);
				if (replaceSucess) {
					String upHeadSql ="update userinfo set headurl=? where userid=?";
					Object[] params2= {newPath,uInfo.getUserId()};
					int count = goldDao.addGoldInfo(upHeadSql, params2);
					if (count>0) {
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}finally {
				JdbcTool.close(rs, JdbcTool.pstmt, JdbcTool.conn);
			}
				
		}
		

		

	
		
}
