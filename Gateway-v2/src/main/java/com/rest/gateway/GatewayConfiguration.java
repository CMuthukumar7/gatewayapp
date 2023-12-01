package com.rest.gateway;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.rest.gateway.helper.ApplicationProperties;

@Configuration
public class GatewayConfiguration {

	private final Logger log = LoggerFactory.getLogger(GatewayConfiguration.class);

	private static final String TLSV1 = "TLSv1.1";
	private static final String TLSV1_2 = "TLSv1.2";
	private static final String SSLV3 = "SSLv3";

	private static int TIMEOUT = 190000;

	private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT)
			.setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();

	@Autowired
	private ApplicationProperties applicationProperties;

	@Bean
	public RestTemplate getRestTemplate() throws Exception {

		try {
			printCiphers();
			return new RestTemplate(getRequestFactory());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private ClientHttpRequestFactory getRequestFactory() throws Exception {
		
		log.info("Application property values : {} ", applicationProperties);
		
		log.info("JKS file name value : {} ", applicationProperties.getJksFileName());
		
		log.info("Passcode value : {} ", applicationProperties.getPassCode());
		
		List<Header> httpHeaders = new ArrayList();
		
		HttpComponentsClientHttpRequestFactory requestFactory = null;

		try (InputStream keyStoreFileStream = new FileInputStream(applicationProperties.getJksFileName());) {
			
			log.info("keyStoreFileStream value : {} ", keyStoreFileStream);

			KeyStore keystore = KeyStore.getInstance("jks");
			keystore.load(keyStoreFileStream, applicationProperties.getPassCode().toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(keystore, applicationProperties.getPassCode().toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(keystore);

			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
			} };

			SSLContext sslContext = SSLContext.getInstance(TLSV1_2);
			sslContext.init(kmf.getKeyManagers(), trustAllCerts, new SecureRandom());

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
					new String[] { TLSV1, TLSV1_2, SSLV3 }, null, NoopHostnameVerifier.INSTANCE);

			CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
					.evictExpiredConnections().setDefaultHeaders(httpHeaders).setSSLSocketFactory(sslsf).build();

			requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(240000);
			requestFactory.setHttpClient(httpClient);

			printCiphers();

		} catch (Exception e) {
			log.error("Exception raised, in getRequestFactory method : {} ", e);
			e.printStackTrace();
			throw e;
		}

		return requestFactory;
	}

	private void printCiphers() {

		try {
			log.info("Java Version Value : {} ", System.getProperty("java.version"));
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

			String[] defaultCiphers = ssf.getDefaultCipherSuites();
			String[] availableCiphers = ssf.getSupportedCipherSuites();

			if (availableCiphers != null) {
				for (int i = 0; i < availableCiphers.length; ++i) {
					log.info("availableCiphers Ciphers => {} ", availableCiphers[i]);
				}
			}

			if (defaultCiphers != null) {
				for (int i = 0; i < defaultCiphers.length; ++i) {
					log.info("default Ciphers => {} ", defaultCiphers[i]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Bean
	public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
		return new BeanFactoryPostProcessor() {
			
			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

				BeanDefinition bean = beanFactory.getBeanDefinition(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
				bean.getPropertyValues().add("loadOnStartup", 1);
				
			}
		};
	}

}
