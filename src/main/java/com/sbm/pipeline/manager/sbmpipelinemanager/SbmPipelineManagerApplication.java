package com.sbm.pipeline.manager.sbmpipelinemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sbm.pipeline.manager.sbmpipelinemanager")
public class SbmPipelineManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbmPipelineManagerApplication.class, args);
	}

}
