package com.sbm.pipeline.manager.sbmpipelinemanager.service;

import com.openshift.restclient.ClientBuilder;
import com.openshift.restclient.IClient;
import com.openshift.restclient.ResourceKind;
import com.openshift.restclient.model.IProject;
import com.openshift.restclient.model.IResource;
import com.sbm.pipeline.manager.sbmpipelinemanager.dto.PipelineDto;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PipelineServiceImpl implements PipelineService {

    public static final String PROJECT_PIPELINERESOURCE_YML = "/project-pipelineresource.yml";
    public static final String DEPLOYMENT_TASK_YML = "/deployment-task.yml";
    public static final String DEPLOYMENT_PIPELINE_YML = "/deployment-pipeline.yml";
    public static final String DEPLOYMENT_PIPELINE_RUN = "/project-pipelinerun.yml";

    @Override
    public void createPipeline(PipelineDto pipelineDto) throws Exception{
        try {

            validateInputs(pipelineDto);

            IClient client = new ClientBuilder(pipelineDto.getClusterUrl())
                    .usingToken(pipelineDto.getClusterToken())
                    .build();


            IResource request = client.getResourceFactory().stub(ResourceKind.PROJECT_REQUEST, pipelineDto.getProjectName());
            IProject project = (IProject) client.create(request);


            String pipelineResourceStr = updateGitUrl(pipelineDto.getGitUrl());

            IResource pipelineResource = client.getResourceFactory().create(pipelineResourceStr);
            client.create(pipelineResource, pipelineDto.getProjectName());

            IResource taskResource = client.getResourceFactory().create(PipelineServiceImpl.class.getResourceAsStream(DEPLOYMENT_TASK_YML));
            client.create(taskResource, pipelineDto.getProjectName());

            IResource pipeline = client.getResourceFactory().create(PipelineServiceImpl.class.getResourceAsStream(DEPLOYMENT_PIPELINE_YML));
            client.create(pipeline, pipelineDto.getProjectName());

            IResource pipelineRun = client.getResourceFactory().create(PipelineServiceImpl.class.getResourceAsStream(DEPLOYMENT_PIPELINE_RUN));
            client.create(pipelineRun, pipelineDto.getProjectName());

        } catch (Exception ex) {
            throw new Exception("Failed to execute plugin", ex);
        }
    }

    private void validateInputs(PipelineDto pipelineDto) throws MojoExecutionException, MojoFailureException{

        String patternString = "((git|ssh|http(s)?)|(git@[\\w\\.]+))(:(//)?)([\\w\\.@\\:/\\-~]+)(\\.git)(/)?";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(pipelineDto.getGitUrl());
        boolean isMatched = matcher.matches();
        if(!isMatched)
            throw new MojoExecutionException("git url in not valid url");
    }

    private static String updateGitUrl(String newGitUrl){
        JSONObject resourceJson = null;
        try {
            InputStream inputStream = PipelineServiceImpl.class.getResourceAsStream(PROJECT_PIPELINERESOURCE_YML);
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer, "UTF-8");
            String resourceJsonStr = writer.toString();
            resourceJson = new JSONObject(resourceJsonStr);
            JSONObject spec = (JSONObject) resourceJson.get("spec");
            JSONArray params = (JSONArray) spec.get("params");
            for(int i = 0; i < params.length(); i++){
                JSONObject jsonObj = params.optJSONObject(i );
                if (jsonObj != null && "url".equals(jsonObj.get("name"))){
                    jsonObj.put("value", newGitUrl);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resourceJson.toString();
    }
}
