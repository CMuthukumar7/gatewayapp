package com.rest.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

	private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

	public static void main(String[] args) {

		//System.setProperty("java.net.debug", "ssl:handshake");

		log.info("args value : {} ", args);

		if (args != null && args.length > 0) {
			log.info("args lenght : {} ", args.length);

			int i = 0;

			for (String arg : args) {
				log.info((i++) + " arg value : {} ", arg);
			}

		}

		SpringApplication.run(GatewayApplication.class, args);
	}

}
