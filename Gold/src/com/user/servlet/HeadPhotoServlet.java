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

/*�ϴ�ͷ��(����gold/userHeadPhoto/�ļ�����)
 * ʹ�� �û�id+ͼƬ���������ļ����ɱ�֤����û���ͬһ��ͼƬҲ�����ͻ��·���������ݿ�
 * ��Ҫcommons-fileupload-1.3.1.jar
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
						out.print("����ʧ��");
					} else {
						HttpSession session = request.getSession();
						String userId = (String) session.getAttribute("userId");
						String imgName = fileItem.getName();// �����ȡ���ļ������к�׺��
						imgName = imgName.replaceAll("\\s*", "");//ȥ��ͼƬ�����еĿո�
						if (imgName.length()>8) {//ͼƬ����̫����ض�
							imgName =imgName.substring(imgName.lastIndexOf(".")-2,imgName.length()) ;
						}
						String suffix = imgName.substring(imgName.lastIndexOf(".") + 1,imgName.length()).toUpperCase();
						if (!("PNG".equals(suffix) || !"JPG".equals(suffix) || !"JPEG".equals(suffix))) {
							out.print("���ϴ�ͼƬ");
						} else {
							
							UserService uService = new UserService();
							boolean updataSuccess = uService.updateHeadPhoto(userId, imgName, fileItem);
							if (updataSuccess) {
								session.setAttribute("headurl", "/golddisk/userHeadPhoto/"+userId+imgName);
								out.print("���³ɹ�");
							} else {
								out.print("����ʧ��");
							}
						}

					}
				}
			}
	
		} catch (FileUploadBase.SizeLimitExceededException e) {//��FileUploadException������
			//�ļ�����30KB�򱨴�
			out.print("�ϴ���С����2M");
			System.out.println("�ϴ���С����2M");
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
