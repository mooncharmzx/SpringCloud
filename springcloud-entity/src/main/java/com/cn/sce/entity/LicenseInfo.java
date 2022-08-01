package com.cn.sce.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LicenseInfo implements Serializable {
    private String cerId;
    private String authId;
    private String comCode;
    private String comName;
    private String projectCode;
    private String projectName;
    private Date passTime;
    private Date issueTime;
    private Date expireTime;
    private Date downTime;
    private String user;
    private String signatureSecret;
    private List<ComponentInfo> comps;
    private Map<String, Object> exts;

    public LicenseInfo() {
    }

    public String getSignatureSecret() {
        return this.signatureSecret;
    }

    public void setSignatureSecret(String signatureSecret) {
        this.signatureSecret = signatureSecret;
    }

    public String toString() {
        return "LicenseInfo{cerId='" + this.cerId + '\'' + ", authId='" + this.authId + '\'' + ", comCode='" + this.comCode + '\'' + ", comName='" + this.comName + '\'' + ", projectCode='" + this.projectCode + '\'' + ", projectName='" + this.projectName + '\'' + ", passTime=" + this.passTime + ", issueTime=" + this.issueTime + ", expireTime=" + this.expireTime + ", downTime=" + this.downTime + ", user='" + this.user + '\'' + ", signatureSecret='" + this.signatureSecret + '\'' + ", comps=" + this.comps + ", exts=" + this.exts + '}';
    }

    public boolean hasComponent(String component) {
        if (this.comps == null) {
            return false;
        } else {
            Iterator var2 = this.comps.iterator();

            ComponentInfo comp;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                comp = (ComponentInfo)var2.next();
            } while(!comp.getCompName().equals(component));

            return true;
        }
    }

    public ComponentInfo getComponentById(String component) {
        if (this.comps == null) {
            return null;
        } else {
            Iterator var2 = this.comps.iterator();

            ComponentInfo comp;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                comp = (ComponentInfo)var2.next();
            } while(!comp.getCompName().equals(component));

            return comp;
        }
    }

    public String getCerId() {
        return this.cerId;
    }

    public void setCerId(String cerId) {
        this.cerId = cerId;
    }

    public String getAuthId() {
        return this.authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getComCode() {
        return this.comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getComName() {
        return this.comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getPassTime() {
        return this.passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public Date getIssueTime() {
        return this.issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Date getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getDownTime() {
        return this.downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<ComponentInfo> getComps() {
        return this.comps;
    }

    public void setComps(List<ComponentInfo> comps) {
        this.comps = comps;
    }

    public Map<String, Object> getExts() {
        return this.exts;
    }

    public void setExts(Map<String, Object> exts) {
        this.exts = exts;
    }
}
