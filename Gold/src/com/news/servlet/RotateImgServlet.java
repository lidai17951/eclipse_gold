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
/* adminNewsControl.html���ϴ���ť
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
				boolean checkImg = nService.checkImg(items);//ͼƬ�Ƿ�Ϸ�
		
				if (checkImg) {
					for (FileItem fileItem : items) {
						RotateImg rotateImg =new RotateImg();				
						String imgName = fileItem.getName();// �����ȡ���ļ������к�׺��
						imgName = imgName.replaceAll("\\s*", "");// ȥ��ͼƬ�����еĿո�
						if (imgName.length() > 8) {// ͼƬ����̫����ض�
							imgName = imgName.substring(imgName.lastIndexOf(".") - 2, imgName.length());
						}
						String newsid=nService.getRadom();//�����ģ��newsid��newstitle
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
					out.print("ͼƬ��ʽ����");
				}

				
			}
		} catch (FileUploadBase.SizeLimitExceededException e) {// ��FileUploadException������
			// �ļ�����30KB�򱨴�
			out.print("�ϴ���С����2M");
			System.out.println("�ϴ���С����2M");
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
