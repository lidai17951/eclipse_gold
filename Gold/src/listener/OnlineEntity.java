package listener;

public class OnlineEntity {
	private int onlineNum=0; // 在线总人数
	private int onlineLogin=0; // 在线注册用户
	private int onlineTourist=0;// 在线游客

	
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
		//在线总人数 减 在线注册人数 = 在线游客
		return this.onlineNum-this.onlineLogin;
	}

//	public void setOnlineTourist(int onlineTourist) {
//		this.onlineTourist = onlineTourist;
//	}

	public void addOnlineNum() {//增加在线总人数
		++this.onlineNum;
	}
	public void addOnlineLogin() {//增加在线注册人数
		++this.onlineLogin;
	}
	public void reduceOnlineNum() {//减少在线总人数
		if (this.onlineNum>0) {
			--this.onlineNum;
		}
	}
	public void reduceOnlineLogin() {//减少在线注册人数
		if (this.onlineLogin>0) {
			--this.onlineLogin;
		}
	}

}
