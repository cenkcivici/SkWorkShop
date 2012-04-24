package com.sk.frontend.vpos;import java.io.IOException;import javax.servlet.ServletException;import javax.servlet.http.HttpServlet;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;public class VPOSServlet extends HttpServlet {	private static final long serialVersionUID = 7274398710396586465L;	@Override	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		serve(req, resp);	}	@Override	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		serve(req, resp);	}	private void serve(HttpServletRequest request, HttpServletResponse response) throws IOException {		response.setContentType("text/xml");		if( request.getParameter("hasError") == null) {			sendSuccess(response);		}else{			sendError(response);		}	}	private void sendError(HttpServletResponse response) throws IOException {		response.getWriter().print("<GVPSResponse>"+			"<Mode> </Mode>"+			"<Order>"+			"<OrderID>SISTE20C15E38F1446AF92DABFB4059E87F6</OrderID>"+			"<GroupID> </GroupID>"+			"</Order>"+			"<Transaction>"+			"<Response>"+			"<Source>HOST</Source>"+			"<Code>54</Code>"+			"<ReasonCode>54</ReasonCode>"+			"<Message>Declined</Message>"+			"<ErrorMsg>›˛leminizi gerÁekle˛tiremiyoruz.Tekrar deneyiniz</ErrorMsg>"+			"<SysErrMsg>SON KULLANMA TARIHI HATALI</SysErrMsg>"+			"</Response>"+			"<RetrefNum>105809652540</RetrefNum>"+			"<AuthCode> </AuthCode>"+			"<BatchNum>000574</BatchNum>"+			"<SequenceNum>000510</SequenceNum>"+			"<ProvDate>20110227 09:40:48</ProvDate>"+			"<CardNumberMasked></CardNumberMasked>"+			"<CardHolderName>HA*** YIL***</CardHolderName> <HashData>8D8E2BE7FC080AD985CA0506C50479035FC2C206</HashData>"+			"<HostMsgList> </HostMsgList>"+			"<RewardInqResult>"+			"<RewardList> </RewardList>"+			"<ChequeList> </ChequeList>"+			"</RewardInqResult>"+			"</Transaction>"+			"</GVPSResponse>");			}	protected void sendSuccess(HttpServletResponse response) throws IOException {		response.getWriter().print("<GVPSResponse>"+			"<Mode></Mode>"+			"<Order>"+			"<OrderID>SIST2E8748F43EA24754912E365D637B91D8</OrderID>"+			"<GroupID></GroupID>"+			"</Order>"+			"<Transaction>"+			"<Response>"+			"<Source>HOST</Source>"+			"<Code>00</Code>"+			"<ReasonCode>00</ReasonCode>"+			"<Message>Approved</Message>"+			"<ErrorMsg></ErrorMsg>"+			"<SysErrMsg></SysErrMsg>"+			"</Response>"+			"<RetrefNum>105809652539</RetrefNum>"+			"<AuthCode>914729</AuthCode>"+			"<BatchNum>000574</BatchNum>"+			"<SequenceNum>000509</SequenceNum>"+			"<ProvDate>20110227 09:26:12</ProvDate>"+			"<CardNumberMasked></CardNumberMasked>"+			"<CardHolderName>HA*** YIL***</CardHolderName>"+			"<HashData>6F39EA85C6A5BBA9040F40FA7C8Fa99C70D7343A</HashData>"+			"<HostMsgList>"+			"</HostMsgList>"+			"<RewardInqResult>"+			"<RewardList></RewardList>"+			"<ChequeList></ChequeList>"+			"</RewardInqResult>"+			"</Transaction>"+			"</GVPSResponse>");	}}