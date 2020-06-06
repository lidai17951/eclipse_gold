package listener;

public class OnlineEntity {
	private int onlineNum=0; // ����������
	private int onlineLogin=0; // ����ע���û�
	private int onlineTourist=0;// �����ο�

	
	public OnlineEntity() {
	}

	public OnlineEntity(int onlineNum, int onlineLogin,int onlineTourist) {
		super();
		this.onlineNum = onlineNum;
		this.onlineLogin = onlineLogin;
	}

	public int getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}

	public int getOnlineLogin() {
		return this.onlineLogin;
	}
	
	public int getOnlineTourist() {
		//���������� �� ����ע������ = �����ο�
		return this.onlineNum-this.onlineLogin;
	}

//	public void setOnlineTourist(int onlineTourist) {
//		this.onlineTourist = onlineTourist;
//	}

	public void addOnlineNum() {//��������������
		++this.onlineNum;
	}
	public void addOnlineLogin() {//��������ע������
		++this.onlineLogin;
	}
	public void reduceOnlineNum() {//��������������
		if (this.onlineNum>0) {
			--this.onlineNum;
		}
	}
	public void reduceOnlineLogin() {//��������ע������
		if (this.onlineLogin>0) {
			--this.onlineLogin;
		}
	}

}
