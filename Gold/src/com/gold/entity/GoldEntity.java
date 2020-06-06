package com.gold.entity;

/*NowApi接口的格式*/
public class GoldEntity {/*数据库存12个*/
	private String goldid;
	private String variety; /* 品种 oracle列 */
	private String varietynm; /* 品种名称 */
	private String last_price; /* 当前价 oracle列*/
	private String buy_price;/* 买入价oracle列 */
	private String sell_price;/* 卖出价oracle列 */
	private String volume; /* 成交量 oracle列*/
	private String change_price; /* 涨跌额oracle列 */
	private String change_margin;/* 涨跌幅oracle列 */
	private String high_price; /* 最高价oracle列 */
	private String low_price; /* 最低价oracle列 */
	private String open_price;/* 开盘价 oracle列*/
	private String yesy_price; /* 昨收价 oracle列*/
	private GoldTime goldTime; /* 更新时间 (行情有变化uptime才会更新)oracle列 */

	public GoldEntity() {

	}

	public GoldEntity(String goldid, String variety, String varietynm, String last_price, String buy_price,
			String sell_price, String volume, String change_price, String change_margin, String high_price,
			String low_price, String open_price, String yesy_price, GoldTime goldTime) {
		this.goldid = goldid;
		this.variety = variety;
		this.varietynm = varietynm;
		this.last_price = last_price;
		this.buy_price = buy_price;
		this.sell_price = sell_price;
		this.volume = volume;
		this.change_price = change_price;
		this.change_margin = change_margin;
		this.high_price = high_price;
		this.low_price = low_price;
		this.open_price = open_price;
		this.yesy_price = yesy_price;
		this.goldTime = goldTime;
	}

	public String getGoldid() {
		return goldid;
	}

	public void setGoldid(String goldid) {
		this.goldid = goldid;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public String getVarietynm() {
		return varietynm;
	}

	public void setVarietynm(String varietynm) {
		this.varietynm = varietynm;
	}

	public String getLast_price() {
		return last_price;
	}

	public void setLast_price(String last_price) {
		this.last_price = last_price;
	}

	public String getBuy_price() {
		return buy_price;
	}

	public void setBuy_price(String buy_price) {
		this.buy_price = buy_price;
	}

	public String getSell_price() {
		return sell_price;
	}

	public void setSell_price(String sell_price) {
		this.sell_price = sell_price;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getChange_price() {
		return change_price;
	}

	public void setChange_price(String change_price) {
		this.change_price = change_price;
	}

	public String getChange_margin() {
		return change_margin;
	}

	public void setChange_margin(String change_margin) {
		this.change_margin = change_margin;
	}

	public String getHigh_price() {
		return high_price;
	}

	public void setHigh_price(String high_price) {
		this.high_price = high_price;
	}

	public String getLow_price() {
		return low_price;
	}

	public void setLow_price(String low_price) {
		this.low_price = low_price;
	}

	public String getOpen_price() {
		return open_price;
	}

	public void setOpen_price(String open_price) {
		this.open_price = open_price;
	}

	public String getYesy_price() {
		return yesy_price;
	}

	public void setYesy_price(String yesy_price) {
		this.yesy_price = yesy_price;
	}

	public GoldTime getGoldTime() {
		return goldTime;
	}

	public void setGoldTime(GoldTime goldTime) {
		this.goldTime = goldTime;
	}

	@Override
	public String toString() {
		return "GoldEntity [goldid=" + goldid + ", variety=" + variety + ", varietynm=" + varietynm + ", last_price="
				+ last_price + ", buy_price=" + buy_price + ", sell_price=" + sell_price + ", volume=" + volume
				+ ", change_price=" + change_price + ", change_margin=" + change_margin + ", high_price=" + high_price
				+ ", low_price=" + low_price + ", open_price=" + open_price + ", yesy_price=" + yesy_price
				+ ", goldTime=" + goldTime + "]";
	}
	
	

}
