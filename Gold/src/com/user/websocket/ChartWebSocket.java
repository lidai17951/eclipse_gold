package com.user.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value="/ChartWebSocket",configurator=GetHttpSessionConfigurator.class)

public class ChartWebSocket {

	 // ��̬������������¼��ǰ������������Ӧ�ð�����Ƴ��̰߳�ȫ�ġ�
	 private static final AtomicInteger onlineCount = new AtomicInteger(0);
	 // concurrent�����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
	 private static CopyOnWriteArraySet<ChartWebSocket> webSocketSet = new CopyOnWriteArraySet<ChartWebSocket>();
	 //����һ����¼�ͻ��˵������ǳ�
	 private String nickname;
	 // ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
	 private Session session;
	 private String uname;
	 private String upower;

	 public ChartWebSocket() {
	  //nickname = "�ÿ�"+onlineCount.getAndIncrement();
		 
	 }
	 /*
	  *ʹ��@Onopenע��ı�ʾ���ͻ������ӳɹ���Ļص�������Session�ǿ�ѡ����
	  ���Session��WebSocket�淶�еĻỰ����ʾһ�λỰ������HttpSession
	  */
	 @OnOpen
	 public void onOpen(Session session,EndpointConfig config) {
	HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
	this.upower =(String) httpSession.getAttribute("upower");

	switch (this.upower) {
	case "supreme":
		this.upower="��������Ա";
		break;
	case "client":
		this.upower="��Ա";
		break;
	}
	this.uname =(String) httpSession.getAttribute("uname");
	this.nickname ="("+upower+")"+uname;
	this.session = session;
	  webSocketSet.add(this);
	  String message = String.format("[%s,%s]",nickname,"����������");
	  broadcast(message);
	  System.out.println("onOpen");
	 }
	/*
	  *ʹ��@OnMessageע��ı�ʾ���ͻ��˷�����Ϣ��Ļص�����һ��������ʾ�û����͵����ݡ�����Session�ǿ�ѡ��������OnOpen�����е�session��һ�µ�
	  */
	 @OnMessage
	 public void onMessage(String message,Session session){
	 //���ﵱȻ���ӡtrue
	  System.out.println(this.session==session);
	  broadcast(String.format("%s:%s",nickname,filter(message)));
	 }
	/*
	*�û��Ͽ����Ӻ�Ļص���ע��������������ǿͻ��˵����˶Ͽ����ӷ�����Ż�ص�
	*/
	 @OnClose
	 public void onClose() {
	  webSocketSet.remove(this);
	  String message = String.format("[%s,%s]",nickname,"�뿪������������");
	  broadcast(message);
	 }
	 //���Ⱥ��
	 private void broadcast(String info){
	  for(ChartWebSocket w:webSocketSet){
	   try {
	    synchronized (ChartWebSocket.class) {
	     w.session.getBasicRemote().sendText(info);
	    }
	   } catch (IOException e) {
	    System.out.println("��ͻ���"+w.nickname+"������Ϣʧ��");
	    webSocketSet.remove(w);
	    try {
	     w.session.close();
	    } catch (IOException e1) {}
	    String message = String.format("[%s,%s]",w.nickname,"�Ѿ��Ͽ�����");
	    broadcast(message);
	   }

	  }
	 }
	 //���û�����Ϣ������һЩ�������������ιؼ��ֵȵȡ�����
	 public static String filter(String message){
	  if(message==null){
	   return null;
	  }
	  return message;
	 }
}
