package com.sbm.pipeline.manager.sbmpipelinemanager.service;

import com.sbm.pipeline.manager.sbmpipelinemanager.dto.PipelineDto;

public interface PipelineService {

    public void createPipeline(PipelineDto pipelineDto) throws Exception;
}
