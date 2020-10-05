package com.sbm.pipeline.manager.sbmpipelinemanager.controller;

import com.sbm.pipeline.manager.sbmpipelinemanager.dto.PipelineDto;
import com.sbm.pipeline.manager.sbmpipelinemanager.service.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Path("/pipeline")
public class PipelineController {

    @Autowired
    private PipelineService pipelineService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getApprovedEmployer(PipelineDto pipelineDto) {
        try{
            pipelineService.createPipeline(pipelineDto);
            return "CREATED";
        }catch (Exception ex){
            ex.printStackTrace();
            return "FAILED";
        }


    }
}
