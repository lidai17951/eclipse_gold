package com.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.news.entity.RotateImg;
import com.news.service.NewsService;
/* adminNewsControl.html的上传按钮
 */
@WebServlet("/admin/RotateImgServlet")
public class RotateImgServlet extends HttpServlet {

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
				upload.setSizeMax(1024 * 1024 * 2);

				List<FileItem> items = upload.parseRequest(request);
				List<RotateImg> imgEntityList =new ArrayList<RotateImg>();
				NewsService nService = new NewsService();
				boolean checkImg = nService.checkImg(items);//图片是否合法
		
				if (checkImg) {
					for (FileItem fileItem : items) {
						RotateImg rotateImg =new RotateImg();				
						String imgName = fileItem.getName();// 这个获取的文件名是有后缀的
						imgName = imgName.replaceAll("\\s*", "");// 去除图片名称中的空格
						if (imgName.length() > 8) {// 图片名称太长则截短
							imgName = imgName.substring(imgName.lastIndexOf(".") - 2, imgName.length());
						}
						String newsid=nService.getRadom();//随机数模拟newsid和newstitle
						String newstitle=nService.getRadom();
						String newPath = "D:/gold/newsImgList" + "/" + newsid + imgName;
						rotateImg.setNewsid(newsid);
						rotateImg.setNewstitle(newstitle);
						rotateImg.setImgurl(newPath);
						rotateImg.setFileItem(fileItem);
						imgEntityList.add(rotateImg);
				
					}
					String resultMsg = nService.updateRotaleImg(imgEntityList);
					out.print(resultMsg);
				}else {
					out.print("图片格式错误");
				}

				
			}
		} catch (FileUploadBase.SizeLimitExceededException e) {// 是FileUploadException的子类
			// 文件超过30KB则报错
			out.print("上传大小超过2M");
			System.out.println("上传大小超过2M");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
