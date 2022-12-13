package com.devitron.servtronic.servicebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServtronicServiceBaseProtoApplication {



	public static void main(String[] args) {

		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		//ctx.register(Config.class);
		//ctx.refresh();

		SpringApplication.run(ServtronicServiceBaseProtoApplication.class, args);
	}

}
