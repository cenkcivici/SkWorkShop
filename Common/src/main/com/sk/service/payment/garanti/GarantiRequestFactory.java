package com.sk.service.payment.garanti;import java.security.MessageDigest;import java.security.NoSuchAlgorithmException;import java.util.HashMap;import java.util.Map;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Component;import com.sk.domain.CreditCardPaymentMethod;import com.sk.service.template.VelocityHelper;import com.sk.util.http.HttpConnection;import com.sk.util.http.HttpConnectionFactory;@Componentpublic class GarantiRequestFactory {	@Autowired	private HttpConnectionFactory connectionFactory;	private final String path = "garanti/";	@Value("${vpos.garanti.url}")	private String garantiVPOSUrl;	@Value("${vpos.garanti.merchant.id}")	private String merchantId;	@Value("${vpos.garanti.terminal.id}")	private String terminalId;	@Value("${vpos.garanti.user.id}")	private String userId;	@Value("${vpos.garanti.user.password}")	private String password;	@Autowired	private VelocityHelper velocityHelper;	public GarantiRequestFactory() {	}	public GarantiRequestFactory(HttpConnectionFactory connectionFactory, VelocityHelper velocityHelper) {		this.connectionFactory = connectionFactory;		this.velocityHelper = velocityHelper;	}	public HttpConnection createPaymentRequest(CreditCardPaymentMethod cardPaymentMethod, Double amount) {		HttpConnection connection = connectionFactory.createConnection(garantiVPOSUrl);		String requestEntity = velocityHelper.getProcessed(path + "sales.vm", getDataModel(cardPaymentMethod, amount));		connection.setData(requestEntity);		return connection;	}	public Map<String, Object> getDataModel(CreditCardPaymentMethod cardPaymentMethod, Double amount) {		Map<String, Object> dataMap = new HashMap<String, Object>();		dataMap.put("userId", userId);		dataMap.put("hashData", getHashData());		dataMap.put("terminalId", terminalId);		dataMap.put("merchantId", merchantId);		dataMap.put("cardNumber", cardPaymentMethod.getCardNumber());		dataMap.put("expireDate", cardPaymentMethod.getExpireDate());		dataMap.put("cvc", cardPaymentMethod.getCvc());		dataMap.put("amount", getFormattedAmount(amount));		return dataMap;	}	public Integer getFormattedAmount(Double amount) {		amount = amount * 100;		return amount.intValue();	}	private String getHashData() {		// based on Garanti docs.		String inputAndPassword = userId + password;		try {			MessageDigest md = MessageDigest.getInstance("SHA1");			md.reset();			byte[] buffer = inputAndPassword.getBytes();			md.update(buffer);			byte[] digest = md.digest();			String hashData = "";			for (int i = 0; i < digest.length; i++) {				hashData += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);			}			return hashData.toUpperCase();		} catch (NoSuchAlgorithmException e) {			return null;		}	}	public void setConnectionFactory(HttpConnectionFactory connectionFactory) {		this.connectionFactory = connectionFactory;	}	public void setGarantiVPOSUrl(String garantiVPOSUrl) {		this.garantiVPOSUrl = garantiVPOSUrl;	}	public void setTerminalId(String terminalId) {		this.terminalId = terminalId;	}}