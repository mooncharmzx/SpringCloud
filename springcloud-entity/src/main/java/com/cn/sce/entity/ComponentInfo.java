package com.cn.sce.entity;

import java.io.Serializable;
import java.util.Objects;

public class ComponentInfo implements Serializable {
    private String compId;
    private String compName;
    private String compType;
    private String compLanguage;
    private String compVersion;

    public String getCompId() {
        return this.compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return this.compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompType() {
        return this.compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }

    public String getCompLanguage() {
        return this.compLanguage;
    }

    public void setCompLanguage(String compLanguage) {
        this.compLanguage = compLanguage;
    }

    public String getCompVersion() {
        return this.compVersion;
    }

    public void setCompVersion(String compVersion) {
        this.compVersion = compVersion;
    }

    public ComponentInfo() {
    }

    public ComponentInfo(String compId, String compName, String compType, String compLanguage, String compVersion) {
        this.compId = compId;
        this.compName = compName;
        this.compType = compType;
        this.compLanguage = compLanguage;
        this.compVersion = compVersion;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ComponentInfo) {
            ComponentInfo componentInfo = (ComponentInfo)obj;
            return this.compName.equals(componentInfo.compName);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.compId, this.compName, this.compType, this.compLanguage});
    }
}
