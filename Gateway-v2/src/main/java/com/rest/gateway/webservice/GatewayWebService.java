package com.rest.gateway.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.gateway.helper.ApplicationProperties;

@RestController
@RequestMapping("/v1")
public class GatewayWebService {

	private final static Logger log = LoggerFactory.getLogger(GatewayWebService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApplicationProperties applicationProperties;
	
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	public String getUrl(String url) {
		log.info("Endpoint Url value : {}", url);
		return url;
	}
	
	private static void logJsonValue(String methodname, Object source) {
		
		if(source != null) {
			try {
				log.info(methodname, objectMapper.writeValueAsString(source));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else {
			log.info(methodname, source);
		}
		
	}

	public static HttpHeaders getHeaders(HttpHeaders headers) {

		if (headers != null) {
			log.info("############## START PRINTING all HTTP HEADERS key and values ##########");

			for (String headerName : headers.keySet()) {
				log.info(headerName + " : " + headers.get(headerName));
			}
			
			log.info("############## END PRINTED all HTTP HEADERS key and values ##########");

		} else {
			headers = new HttpHeaders();
		}
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;

	}

	@PostMapping(value = "/registration-request")
	public Object registrationRequest(@RequestBody Object request, @RequestHeader HttpHeaders headers)
			throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside Registration Request api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/registration-request"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in registration request, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in registration request, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}
		
		logJsonValue("Response from registration request api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/registration-status")
	public Object registrationStatus(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside Registration Status api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/registration-status"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in registration status, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in registration status, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}
		logJsonValue("Response from registration Status api response value : {} ", response);
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/information")
	public Object information(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside information api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/information"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in registration information, exception body : {} ",
					e.getResponseBodyAsString());
			log.info("Exception raised in registration information, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from information api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/cancel-request")
	public Object cancelRequest(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside cancel request api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/cancel-request"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in cancel request, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in cancel request, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from cancel request api response value : {} ", response);
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/cancellation-status")
	public Object cancellationStatus(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside cancellation status api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/cancellation-status"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in cancel status, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in cancel status, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from cancellation status api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/collection-request")
	public Object collectionRequest(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside Collection Request api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/collection-request"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in collection request, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in collection request, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from Collection Request api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/collection-status")
	public Object collectionStatus(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside Collection Status api request value : {} ", request); 
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/collection-status"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in collection status, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in collection status, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from Collection Request api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/pre-collection-report")
	public Object preCollectionReport(@RequestBody Object request, @RequestHeader HttpHeaders headers)
			throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside Pre Collection report api request value : {} ", request); 
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/pre-collection-report"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in pre collection report, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in pre collection report, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from Pre Collection report api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/stop-payment")
	public Object stopPayment(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside stop-payment api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/stop-payment"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in stop payment, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in stop payment, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from stop-payment api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/report/AB903")
	public Object ab903(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside report AB903 api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/report/AB903"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in report ab903, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in report ab903, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from report AB903 api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@PostMapping(value = "/report/AB907")
	public Object ab907(@RequestBody Object request, @RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		logJsonValue("Entered inside report AB907 api request value : {} ", request);
		
		Object response = null;

		try {
			response = restTemplate.postForObject(
					getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/report/AB907"),
					new HttpEntity<>(request, getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in report ab907, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in report ab907, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}
		
		logJsonValue("Response from report AB903 api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@GetMapping(value = "/cancellation-reasons")
	public Object cancellationReasons(@RequestParam MultiValueMap<String, String> queryParam,
			@RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		
		log.info("Entered inside Cancellation Reasons api queryParam value : {} ", queryParam);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/cancellation-reasons"))
				.queryParams(queryParam);

		Object response = null;

		try {
			response = restTemplate.exchange(getUrl(builder.toUriString()), HttpMethod.GET,
					new HttpEntity<>(getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in cancel reasons, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in cancel reasons, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from Cancellation Reasons api response value : {} ", response);
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@GetMapping(value = "/purpose-codes")
	public Object purposeCodes(@RequestParam MultiValueMap<String, String> queryParam,
			@RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		log.info("Entered inside Purpose Codes api queryParam value : {} ", queryParam);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/purpose-codes"))
				.queryParams(queryParam);

		Object response = null;

		try {
			response = restTemplate.exchange(getUrl(builder.toUriString()), HttpMethod.GET,
					new HttpEntity<>(getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in purpose codes, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in purpose codes, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}
		
		logJsonValue("Response from Purpose Codes api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@GetMapping(value = "/oic-list")
	public Object oicList(@RequestParam MultiValueMap<String, String> queryParam, @RequestHeader HttpHeaders headers)
			throws Exception {

		log.info("*********************************** ENTER ***********************************");
		log.info("Entered inside oic list api queryParam value : {} ", queryParam);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(getUrl(applicationProperties.getFabUrl() + "/directdebit/v1/oic-list"))
				.queryParams(queryParam);

		Object response = null;

		try {
			response = restTemplate.exchange(getUrl(builder.toUriString()), HttpMethod.GET,
					new HttpEntity<>(getHeaders(headers)), Object.class);

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
			log.error("Exception raised in oic list, exception body : {} ", e.getResponseBodyAsString());
			log.info("Exception raised in oic list, exception body : {} ", e.getResponseBodyAsString());
			return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
		}

		logJsonValue("Response from oic list api response value : {} ", response);
		
		log.info("*********************************** EXIT ***********************************");
		return response;

	}

	@GetMapping(value = "/hello")
	public Object gethello(@RequestHeader HttpHeaders headers) throws Exception {

		log.info("*********************************** ENTER ***********************************");
		getHeaders(headers);
		log.info("*********************************** EXIT ***********************************");
		return "HELLO";

	}

}