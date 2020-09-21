package crawl;

class Data {
	private String tradeUp;
	private String tradeDown;
	private String tradeTicker;
	private String tradeType;
	private String name;
	private String date;
	private String wallText;
	private String karmaCount;
	private String likeCount;
	private String dislikeCount;

	public Data(String tradeUp, String tradeDown, String tradeTicker, String tradeType, String name, String date,
			String wallText, String karmaCount, String likeCount, String dislikeCount) {
		this.tradeUp = tradeUp;
		this.tradeDown = tradeDown;
		this.tradeTicker = tradeTicker;
		this.tradeType = tradeType;
		this.name = name;
		this.date = date;
		this.wallText = wallText;
		this.karmaCount = karmaCount;
		this.likeCount = likeCount;
		this.dislikeCount = dislikeCount;
	}
	
	public String getTradeUp() {
		return tradeUp;
	}
	public String getTradeDown() {
		return tradeDown;
	}
	public String getTradeTicker() {
		return tradeTicker;
	}
	public String getTradeType() {
		return tradeType;
	}
	public String getName() {
		return name;
	}
	public String getDate() {
		return date;
	}
	public String getWallText() {
		return wallText;
	}
	public String getKarmaCount() {
		return karmaCount;
	}
	public String getLikeCount() {
		return likeCount;
	}
	public String getDislikeCount() {
		return dislikeCount;
	}
	

}
