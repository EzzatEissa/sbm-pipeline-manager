package com.sbm.pipeline.manager.sbmpipelinemanager.dto;

public class PipelineDto {

    private String projectName;

    private String gitUrl;

    private String clusterUrl;

    private String clusterToken;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getClusterUrl() {
        return clusterUrl;
    }

    public void setClusterUrl(String clusterUrl) {
        this.clusterUrl = clusterUrl;
    }

    public String getClusterToken() {
        return clusterToken;
    }

    public void setClusterToken(String clusterToken) {
        this.clusterToken = clusterToken;
    }

    @Override
    public String toString() {
        return "PipelineDto{" +
                "projectName='" + projectName + '\'' +
                ", gitUrl='" + gitUrl + '\'' +
                ", clusterUrl='" + clusterUrl + '\'' +
                ", clusterToken='" + clusterToken + '\'' +
                '}';
    }
}
