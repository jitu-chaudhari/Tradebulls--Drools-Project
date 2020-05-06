package com.tradebulls.main;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.io.ResourceFactory;

import com.tradebulls.model.Product;

public class DroolsTest {
	 
	public static final void main(String[] args) throws IOException {
		SegmentWise segment = new SegmentWise();
		segment.allSegment();
		//segment.fnoSegment();
	}
}
 class SegmentWise{
	 
	private static final String HEADER = "brokerageSlabCode, clientID, brokerageType, slabType, segment, TradingAmount,tradePrice, quantity, brokerageOfferper, brokerageOfferAmt, perAmount, amtAmount";
 	private static final String COMMA_DELIMITER = ",";
	private static final String LINE_SEPARATOR = "\n";

	 void allSegment() throws IOException {
		  
			FileWriter fileWriter = null;
			
		try {
			
				KieServices kieServices = KieServices.Factory.get();
//				KieContainer kContainer = kieServices.getKieClasspathContainer();
//				KieSession kSession = kContainer.newKieSession("ksession-rules");
				Resource dt = ResourceFactory.newClassPathResource("rules/cashRule.xlsx", getClass());
			  	KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);
		        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
		        KieRepository kieRepository = kieServices.getRepository();
		        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
		        KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
		        KieSession kSession = kieContainer.newKieSession();
			
			String url = "jdbc:mysql://entdb-uat.tradebulls.in:3501/EnterpriseDB";
		    String username = "jitendra.chaudhari";
		    String passwd = "OTP@123";
		  
		    Connection connect = DriverManager.getConnection(url, username, passwd);
		    
		    if (connect != null) {
		    	System.out.println(" Database Connected");
		    }
			 String sql = 
					 
					"SELECT b.ClientID ,c.BrokerageSlabCode,c.BrokerageType,a.Quantity-a.DeliveryQuantity Quantity,a.TradePrice,'T' brokerageSlabType,(a.Quantity-a.DeliveryQuantity) * a.TradePrice TradingAmount,c.Segment\r\n" + 
					"\r\n" + 
					"FROM EnterpriseDB.BO_NSECashTradeDetail  a\r\n" + 
					"\r\n" + 
					"INNER JOIN\r\n" + 
					"\r\n" + 
					"  (SELECT DISTINCT ClientID,BrokerageSlabCode,BrokerageType,BrokerageSlabType,Segment FROM BO_ClientBrokerageDetails where Segment = 'CASH' and BrokerageSlabType = 'T') c on\r\n" + 
					"\r\n" + 
					"  a.ClientID = c.ClientID\r\n" + 
					"\r\n" + 
					"INNER JOIN MDMDB.ClientMaster b on a.ClientID = b.ClientID\r\n" + 
					"\r\n" + 
					"where a.Quantity - a.DeliveryQuantity > 0\r\n" + 
					"\r\n" + 
					"UNION ALL\r\n" + 
					"\r\n" + 
					"SELECT b.ClientID ,c.BrokerageSlabCode,c.BrokerageType,a.DeliveryQuantity Quantity,a.TradePrice,'D' brokerageSlabType,a.DeliveryQuantity * a.TradePrice TradingAmount,c.Segment\r\n" + 
					"\r\n" + 
					"FROM EnterpriseDB.BO_NSECashTradeDetail  a\r\n" + 
					"\r\n" + 
					"INNER JOIN\r\n" + 
					"\r\n" + 
					"  (SELECT DISTINCT ClientID,BrokerageSlabCode,BrokerageType,BrokerageSlabType,Segment FROM BO_ClientBrokerageDetails where Segment = 'CASH' and BrokerageSlabType = 'D') c on\r\n" + 
					"\r\n" + 
					"  a.ClientID = c.ClientID\r\n" + 
					"\r\n" + 
					"INNER JOIN MDMDB.ClientMaster b on a.ClientID = b.ClientID\r\n" + 
					"\r\n" + 
					"where a.DeliveryQuantity > 0 limit 10000";
					 
					 
					 
					// "SELECT DISTINCT a.ClientID,ScripID,BrokerageSlabCode,BrokerageType, BrokerageSlabType,Segment,TradingAmount,Quantity,TradePrice\r\n" + 
			 		//"FROM BO_BSECashTradeDetail a INNER JOIN BO_ClientBrokerageDetails b ON a.ClientID = b.ClientID  WHERE b.BrokerageSlabCode = \"B0205\" ORDER BY BrokerageSlabCode";
			 
//					"SELECT DISTINCT a.ClientID,ScripID,BrokerageSlabCode,BrokerageType, BrokerageSlabType,Segment,TradingAmount,Quantity,TradePrice\r\n" + 
//			 		"FROM BO_BSECashTradeDetail a INNER JOIN BO_ClientBrokerageDetails b ON a.ClientID = b.ClientID where  segment = 'CASH' ORDER BY BrokerageSlabCode LIMIT 10000";
//					 
//					 "SELECT DISTINCT a.ClientID,ScripID,BrokerageSlabCode,BrokerageType, BrokerageSlabType,Segment,TradingAmount,Quantity,TradePrice\r\n" + 
//			 		"FROM BO_BSECashTradeDetailDaily a INNER JOIN BO_ClientBrokerageDetails b ON a.ClientID = b.ClientID  WHERE b.BrokerageSlabCode=\"B007\"\r\n";
					 
//					 "SELECT DISTINCT a.ClientID,ScripID,BrokerageSlabCode,BrokerageType,BrokerageSlabType,Segment,TradingAmount,Quantity,TradePrice \r\n" + 
//			 		"FROM BO_BSECashTradeDetailDaily a INNER JOIN `BO_ClientBrokerageDetails` b ON a.ClientID = b.ClientID WHERE b.BrokerageSlabCode=\"B007\"\r\n" + 
//			 		"ORDER BY  BrokerageSlabCode";
			 
			 System.out.println("Query Executing....");
			  
			  Statement statement = connect.createStatement();
			  ResultSet result = statement.executeQuery(sql);
			 
			  long s = System.currentTimeMillis();
			  
			  fileWriter = new FileWriter("./BrokerageOffer.csv");
			  fileWriter.append(HEADER);
			  fileWriter.append(LINE_SEPARATOR);
			  
			  
			  while(result.next()){
				  
					Product product = new Product();
					product.setBrokerageSlabCode(result.getString("brokerageSlabCode").trim());
					product.setClientID(result.getInt("clientID"));
					product.setBrokerageType(result.getString("brokerageType"));
					product.setSlabType(result.getString("brokerageSlabType"));
					product.setSegment(result.getString("segment"));
					product.setTradingAmount(result.getDouble("tradingAmount"));
					product.setTradeprice(result.getDouble("tradePrice"));
					product.setQuantity(result.getInt("quantity"));
				  
					fileWriter.append(product.getBrokerageSlabCode());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(product.getClientID()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(product.getBrokerageType());   
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(product.getSlabType());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(product.getSegment());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(product.getTradingAmount()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(product.getTradeprice()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(product.getQuantity()));
					fileWriter.append(COMMA_DELIMITER);
					
				  
				  System.out.println( "ClientID-"+result.getInt("clientID")
					+" Brokerage Slab Code-"+result.getString("brokerageSlabCode")
					+" Brokerage Type-"+result.getString("brokerageType")
					+" Brokerage Slab Type-"+result.getString("brokerageSlabType")
					+" Segment-"+result.getString("segment")
					+" Trading Amount-"+ result.getBigDecimal("tradingAmount")
					+" Quntity-"+result.getInt("quantity")+"\n");
			  
			//product.setClientID("T102");
			//result.absolute(1);
			//product.setBuySell("S");
			//product.setScripID(11);
			//product.setTradingAmount(result.getBigDecimal("tradingAmount"));
			 
			kSession.insert(product);
			kSession.fireAllRules();
			
			System.out.println("Brokerage Slab Code-" +product.getBrokerageSlabCode()+
					"\nClient ID-"+product.getClientID()+
					"\nBrokerage Type-"+ product.getBrokerageType()+
					"\nSlab Type-"+product.getSlabType()+
					"\nSegment-"+ product.getSegment()+
					"\nTrading Amount-"+product.getTradingAmount()+
					"\nTrade Price-"+ product.getTradeprice()+
					"\nQuantity-"+product.getQuantity()+
					"\nOffer on percentage-"+product.getBrokerageOfferper()+
					"\nOffer on Amount-"+product.getBrokerageOfferAmt());
			
			if(product.getBrokerageOfferper()!= 0) {
				double tradingamount = product.getTradingAmount(); //result.getBigDecimal("tradingAmount").doubleValue();
				
				double perAmount = (tradingamount * product.getBrokerageOfferper()/100);
				System.out.println("\nPercentage Final-"+ perAmount+"\n\n");
				product.setPercentageAmount(perAmount);
				
			}
			else {
				double amtAmount = (product.getQuantity() * product.getBrokerageOfferAmt());
				System.out.println("\nAmount Final-"+ amtAmount+"\n\n");
				product.setAmtAmount(amtAmount);
			}
			
			fileWriter.append(String.valueOf(product.getBrokerageOfferper()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(product.getBrokerageOfferAmt()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(product.getPercentageAmount()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(product.getAmtAmount()));
			fileWriter.append(LINE_SEPARATOR);
			
			System.out.println("Inserting Data ........");           
			
//			String sql1 = "INSERT INTO BO_DroolsBrokerageOffer (brokerageSlabCode, clientID, brokerageType, slabType, segment, tradePrice, TradingAmount, quantity, brokerageOfferper, brokerageOfferAmt, perAmount, amtAmount  ) "
//					+ "		VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//			
//			PreparedStatement statement2 = connect.prepareStatement(sql1);
//			statement2.setString(1, product.getBrokerageSlabCode());
//			statement2.setInt(2, product.getClientID());
//			statement2.setString(3, product.getBrokerageType());
//			statement2.setString(4, product.getSlabType());
//			statement2.setString(5,product.getSegment());
//			statement2.setDouble(6, product.getTradeprice());
//			statement2.setDouble(7, product.getTradingAmount());
//			statement2.setInt(8, product.getQuantity());
//			statement2.setDouble(9, product.getBrokerageOfferper());
//			statement2.setDouble(10, product.getBrokerageOfferAmt());
//			statement2.setDouble(11, product.getPercentageAmount());
//			statement2.setDouble(12, product.getAmtAmount());
//			
//			statement2.executeUpdate();
//			
//			System.out.println("Inserted........");
			
//			String updateSQL = "UPDATE BO_DroolsBrokerageOffer SET brokerageSlabCode=" +product.getBrokerageSlabCode()
//																 +",clientID="+product.getClientID()
//																 +",brokerageType="+product.getBrokerageType()
//																 +",slabType="+product.getSlabType()
//																 +",segment="+product.getSegment()
//																 +",TradingAmount="+product.getTradingAmount();
////																 +"tradeprice="+product.getTradeprice()
////																 +"brokerageOfferper="+product.getBrokerageOfferper()
////																 +"brokerageOfferAmt="+product.getBrokerageOfferAmt();
////																 +"perAmount="+ product.getPercentageAmount()
////																 +"amtAmount="+product.getAmtAmount();
			
//			PreparedStatement statement2 = connect.prepareStatement(updateSQL);
//			statement2.executeUpdate();
			
			}
			System.out.println("Write to CSV file Succeeded!!!");
			long e =  System.currentTimeMillis();	
			System.out.println("Time taken: " + (e-s));

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally 
		{
 		try
 		{
 			fileWriter.close();
 		} 	 	
 		catch(IOException ie)
 		{
 			System.out.println("Error occured while closing the fileWriter");
 			ie.printStackTrace();
 		}
 	
		}
		}
		
		public void fnoSegment() {
			try {
				
				KieServices kieServices = KieServices.Factory.get();
//				KieContainer kContainer = ks.getKieClasspathContainer();
//				KieSession kSession = kContainer.newKieSession("ksessionTable-rule-");
					Resource dt = ResourceFactory.newClassPathResource("rules/rulesFNO.xlsx", getClass());
				  	KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);
			        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
			        KieRepository kieRepository = kieServices.getRepository();
			        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
			        KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
			        KieSession kSession = kieContainer.newKieSession();
				
				String url = "jdbc:mysql://entdb-uat.tradebulls.in:3501/EnterpriseDB";
			    String username = "jitendra.chaudhari";
			    String passwd = "OTP@123";
			  
			    Connection connect = DriverManager.getConnection(url, username, passwd);
			    
			    if (connect != null) {
			    	System.out.println(" Database Connected");
			    }
			    
				 String sql = "SELECT distinct a.NSEFNOTradeDetailDailyID,a.clientID, a.ClientCode,a.FNOContractID,a.OrderNumber,a.Quantity,a.Price,a.TradingAmount,a.Quantity / a.MarketLot as Lot,a.InstrumentType, a.OptionType,\r\n" + 
				 		" b.BrokerageSlabCode, b.BrokerageType, b.BrokerageSlabType, b.Segment\r\n" + 
				 		"\r\n" + 
				 		"FROM EnterpriseDB.BO_NSEFNOTradeDetailDaily a\r\n" + 
				 		"\r\n" + 
				 		"INNER JOIN MDMDB.ClientMaster c on a.ClientID = c.ClientID\r\n" + 
				 		"\r\n" + 
				 		"INNER JOIN EnterpriseDB.BO_ClientBrokerageDetails b on a.ClientID = b.ClientID and b.Segment = 'FNO' and b.BrokerageSlabType = 'T'\r\n" + 
				 		"\r\n" + 
				 		"where left(a.InstrumentType,3) = b.BrokerageProductCode\r\n" + 
				 		"\r\n" + 
				 		"and a.ClientID = 110177;";
				 
				  Statement statement = connect.createStatement();
				  ResultSet result = statement.executeQuery(sql);
				 
				  long s = System.currentTimeMillis();
				  
				  while(result.next()){
					  
						Product product = new Product();
						
						product.setNSEFNOTradeDetailDailyID(result.getInt("NSEFNOTradeDetailDailyID"));
						product.setBrokerageSlabCode(result.getString("brokerageSlabCode").trim());
						product.setClientID(result.getInt("clientID"));
						product.setBrokerageType(result.getString("brokerageType"));
						product.setSlabType(result.getString("brokerageSlabType"));
						product.setSegment(result.getString("segment"));
						product.setTradingAmount(result.getDouble("tradingAmount"));
						product.setLot(result.getInt("Lot"));
						//product.setTradeprice(result.getDouble("tradePrice"));
						product.setQuantity(result.getInt("quantity"));
						
						FactHandle fact1;
						fact1 = kSession.insert(product);
						kSession.fireAllRules();
						
						System.out.println("NSETrade details ID-"+ product.getNSEFNOTradeDetailDailyID()+
								"\nBrokerage Slab Code-" +product.getBrokerageSlabCode()+
								"\nClient ID-"+product.getClientID()+
								"\nBrokerage Type-"+ product.getBrokerageType()+
								"\nSlab Type-"+product.getSlabType()+
								"\nSegment-"+ product.getSegment()+
								"\nTrading Amount-"+product.getTradingAmount()+
								"\nTrade Price-"+ product.getTradeprice()+
								"\nOffer on percentage-"+product.getBrokerageOfferper()+
								"\nOffer on Amount-"+product.getBrokerageOfferAmt());
						
						if(product.getBrokerageOfferper()!= 0) {
							double tradingamount = result.getBigDecimal("tradingAmount").doubleValue();
							
							double perAmount = (tradingamount * product.getBrokerageOfferper()/100);
							System.out.println("\nPercentage Final-"+ perAmount+"\n\n");
							product.setPercentageAmount(perAmount);
						}
						else {
							double amtAmount = (product.getQuantity() * product.getBrokerageOfferAmt());
							System.out.println("\nAmount Final-"+ amtAmount+"\n\n");
							product.setAmtAmount(amtAmount);
						}
				  }
				  	long e =  System.currentTimeMillis();	
					System.out.println("Time taken: " + (e-s));
					 
			
			
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		
 }
 }

 
