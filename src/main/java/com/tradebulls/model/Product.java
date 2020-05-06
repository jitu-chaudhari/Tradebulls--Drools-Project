package com.tradebulls.model;

public class Product {

	private String type;
	private int discount;
	private String BuySell;
	private int clientID;
	private int scripID;
	private double tradeprice;
	private String brokerageType;
	private double tradingAmount;
	private int quantity;
	private double brokerageOfferper;
	private String brokerageSlabCode;
	private String slabType;
	private String brokerageChargeType;
	private String segment;
	private double brokerageOfferAmt;
	private double percentageAmount;
	private double AmtAmount;
	private int lot;
	private int NSEFNOTradeDetailDailyID;
	
	public int getNSEFNOTradeDetailDailyID() {
		return NSEFNOTradeDetailDailyID;
	}

	public void setNSEFNOTradeDetailDailyID(int nSEFNOTradeDetailDailyID) {
		NSEFNOTradeDetailDailyID = nSEFNOTradeDetailDailyID;
	}

	public int getLot() {
		return lot;
	}

	public void setLot(int lot) {
		this.lot = lot;
	}
	
	public double getPercentageAmount() {
		return percentageAmount;
	}

	public void setPercentageAmount(double percentageAmount) {
		this.percentageAmount = percentageAmount;
	}

	public double getAmtAmount() {
		return AmtAmount;
	}

	public void setAmtAmount(double amtAmount) {
		AmtAmount = amtAmount;
	}

	public double getBrokerageOfferAmt() {
		return brokerageOfferAmt;
	}

	public void setBrokerageOfferAmt(double brokerageOfferAmt) {
		this.brokerageOfferAmt = brokerageOfferAmt;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getBrokerageChargeType() {
		return brokerageChargeType;
	}

	public void setBrokerageChargeType(String brokerageChargeType) {
		this.brokerageChargeType = brokerageChargeType;
	}

	public String getSlabType() {
		return slabType;
	}

	public void setSlabType(String slabType) {
		this.slabType = slabType;
	}

	public String getBrokerageSlabCode() {
		return brokerageSlabCode;
	}

	public void setBrokerageSlabCode(String brokerageSlabCode) {
		this.brokerageSlabCode = brokerageSlabCode;
	}
	
	public String getBrokerageType() {
		return brokerageType;
	}

	public void setBrokerageType(String brokerageType) {
		this.brokerageType = brokerageType;
	}
		
	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	

	public int getScripID() {
		return scripID;
	}

	public void setScripID(int scripID) {
		this.scripID = scripID;
	}

	public double getTradingAmount() {
		return tradingAmount;
	}

	public void setTradingAmount(double tradingAmount) {
		this.tradingAmount = tradingAmount;
	}
	public double getBrokerageOfferper() {
		return brokerageOfferper;
	}

	public void setBrokerageOfferper(double brokerageOfferper) {
		this.brokerageOfferper = brokerageOfferper;
	}
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTradeprice() {
		return tradeprice;
	}

	public void setTradeprice(double tradeprice) {
		this.tradeprice = tradeprice;
	}

	public String getBuySell() {
		return BuySell;
	}

	public void setBuySell(String buySell) {
		BuySell = buySell;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

}