package com.sk.service.payment.garanti;import org.junit.Before;import org.junit.Test;import org.junit.runner.RunWith;import org.mockito.Mock;import org.mockito.runners.MockitoJUnitRunner;import com.sk.service.template.VelocityHelper;import com.sk.util.builder.CreditCardPaymentMethodBuilder;import com.sk.util.http.HttpConnection;import com.sk.util.http.HttpConnectionFactory;import static org.mockito.Mockito.verify;import static org.mockito.Mockito.when;import static org.hamcrest.MatcherAssert.assertThat;import static org.hamcrest.Matchers.equalTo;import static org.mockito.Matchers.anyMap;import static org.mockito.Matchers.anyString;import static org.mockito.Matchers.eq;@RunWith(MockitoJUnitRunner.class)public class GarantiRequestFactoryTest {	private GarantiRequestFactory factory;	@Mock	private HttpConnectionFactory connectionFactory;	@Mock	private HttpConnection connection;	@Mock	private VelocityHelper helper;	@Before	public void init() {		factory = new GarantiRequestFactory(connectionFactory, helper);	}	@Test	public void shouldGetValidConnection() {		when(connectionFactory.createConnection(anyString())).thenReturn(connection);		when(helper.getProcessed(anyString(), anyMap())).thenReturn("some content");		factory.createPaymentRequest(new CreditCardPaymentMethodBuilder().build(), 1234d);		verify(helper).getProcessed(eq("garanti/sales.vm"), anyMap());		verify(connection).setData("some content");	}	@Test	public void shouldConvertTheAmountCorrectlyIfThereIsADecimalValue() {		assertThat(factory.getFormattedAmount(1234.56d), equalTo(123456));	}	@Test	public void shouldConvertTheAmountCorrectlyIfItIsAnInteger() {		assertThat(factory.getFormattedAmount(1234d), equalTo(123400));	}}