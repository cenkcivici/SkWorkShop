package com.sk.service.payment.garanti;import java.io.IOException;import java.net.UnknownHostException;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Service;import com.sk.domain.Order;import com.sk.service.payment.ResponseStatus;import com.sk.service.payment.VPOSResponse;import com.sk.util.http.HttpConnection;import com.sk.util.http.HttpResult;@Servicepublic class GarantiVPOSService {		Logger logger = LoggerFactory.getLogger(GarantiVPOSService.class);	@Autowired	private GarantiRequestFactory requestFactory;	@Autowired	private GarantiVPOSResponseHandler garantiVPOSResponseHandler;	@Value("${vpos.garanti.simulation}")	private Boolean simulation = false;	public VPOSResponse queryBonus(String creditCardNumber) {		logger.info("simulating the bonus request as we are waiting info from Garanti");		return new VPOSResponse("10.5","10.5",ResponseStatus.SUCCESS);	}		public VPOSResponse refundOrder(Order order) {		logger.debug("making a refund with {}",order);		HttpConnection connection = requestFactory.createRefundRequest(order);		return executeRequest(connection);	}	public VPOSResponse makePayment(Order order) {		logger.debug("making a payment with {}",order);		HttpConnection connection = requestFactory.createPaymentRequest(order);		return executeRequest(connection);	}	private VPOSResponse executeRequest(HttpConnection connection) {		logger.debug("executing the request for vpos");		if (simulation) {			logger.debug("simulating a vpos connection....");			return new VPOSResponse(ResponseStatus.SUCCESS);		} else {			try {				HttpResult result = connection.execute();				logger.debug("Following response arrived from Garanti \n{}",result.getContentAsString());				return garantiVPOSResponseHandler.handle(result);			} catch (UnknownHostException e) {				logger.error("There is an unknown host exception for Garanti!...");				return new VPOSResponse(ResponseStatus.UNKNOWN_HOST_PROBLEM);			} catch (IOException e) {				logger.error("There is a connection problem with Garanti!...");				return new VPOSResponse(ResponseStatus.CONNECTION_PROBLEM);			}		}	}	public void setRequestFactory(GarantiRequestFactory requestFactory) {		this.requestFactory = requestFactory;	}	public void setGarantiVPOSResponseHandler(GarantiVPOSResponseHandler garantiVPOSResponseHandler) {		this.garantiVPOSResponseHandler = garantiVPOSResponseHandler;	}}