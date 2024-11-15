package com.fangxuele.tool.push.domain;

import com.fangxuele.tool.push.bean.TemplateData;

import java.util.List;

public class TMsgMpTemplate {

    private String templateId;

    private String url;

    private String maAppid;

    private String maPagePath;

    private List<TemplateData> templateDataList;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMaAppid() {
        return maAppid;
    }

    public void setMaAppid(String maAppid) {
        this.maAppid = maAppid == null ? null : maAppid.trim();
    }

    public String getMaPagePath() {
        return maPagePath;
    }

    public void setMaPagePath(String maPagePath) {
        this.maPagePath = maPagePath == null ? null : maPagePath.trim();
    }

    public List<TemplateData> getTemplateDataList() {
        return templateDataList;
    }

    public void setTemplateDataList(List<TemplateData> templateDataList) {
        this.templateDataList = templateDataList;
    }
}