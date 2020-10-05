package com.sbm.pipeline.manager.sbmpipelinemanager.config;

import com.sbm.pipeline.manager.sbmpipelinemanager.controller.PipelineController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class RestConfig extends ResourceConfig {

	public RestConfig() {
		register(PipelineController.class);
	}
}