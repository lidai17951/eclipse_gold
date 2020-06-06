package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * ��������δ���
 * */
//@WebListener
public class OnlineListener implements HttpSessionListener,ServletContextListener,HttpSessionAttributeListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		ServletContext application = se.getSession().getServletContext();
		OnlineEntity online = (OnlineEntity) application.getAttribute("online");
		online.addOnlineNum();
		application.setAttribute("online", online);
		System.out.println("��������"+online.getOnlineNum());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		ServletContext application = se.getSession().getServletContext();
		OnlineEntity online = (OnlineEntity) application.getAttribute("online");
		online.reduceOnlineNum();
		application.setAttribute("online", online);
		
	}

	@Override  //����������
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("����������");

		OnlineEntity online = new OnlineEntity();
		sce.getServletContext().setAttribute("online", online);
	}

	@Override //�رշ�����
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("application����");
		sce.getServletContext().removeAttribute("online");
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		ServletContext application = se.getSession().getServletContext();
		OnlineEntity online = (OnlineEntity) application.getAttribute("online");
		online.addOnlineLogin();
		application.setAttribute("online", online);
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		ServletContext application = se.getSession().getServletContext();
		OnlineEntity online = (OnlineEntity) application.getAttribute("online");
		online.reduceOnlineLogin();
		application.setAttribute("online", online);
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		
	}

}
