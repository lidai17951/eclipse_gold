package com.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.user.service.UserService;

/*上传头像(放在gold/userHeadPhoto/文件夹下)
 * 使用 用户id+图片名称命名文件，可保证多个用户用同一张图片也不会冲突。路径放入数据库
 * 需要commons-fileupload-1.3.1.jar
 *commons-io-2.4.jar
 * */
@WebServlet("/client/HeadPhotoServlet")
public class HeadPhotoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(1024*1024*2);
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fileItem : items) {
					if (fileItem.isFormField()) {
						out.print("更新失败");
					} else {
						HttpSession session = request.getSession();
						String userId = (String) session.getAttribute("userId");
						String imgName = fileItem.getName();// 这个获取的文件名是有后缀的
						imgName = imgName.replaceAll("\\s*", "");//去除图片名称中的空格
						if (imgName.length()>8) {//图片名称太长则截短
							imgName =imgName.substring(imgName.lastIndexOf(".")-2,imgName.length()) ;
						}
						String suffix = imgName.substring(imgName.lastIndexOf(".") + 1,imgName.length()).toUpperCase();
						if (!("PNG".equals(suffix) || !"JPG".equals(suffix) || !"JPEG".equals(suffix))) {
							out.print("请上传图片");
						} else {
							
							UserService uService = new UserService();
							boolean updataSuccess = uService.updateHeadPhoto(userId, imgName, fileItem);
							if (updataSuccess) {
								session.setAttribute("headurl", "/golddisk/userHeadPhoto/"+userId+imgName);
								out.print("更新成功");
							} else {
								out.print("更新失败");
							}
						}

					}
				}
			}
	
		} catch (FileUploadBase.SizeLimitExceededException e) {//是FileUploadException的子类
			//文件超过30KB则报错
			out.print("上传大小超过2M");
			System.out.println("上传大小超过2M");
		}
		catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.print(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
