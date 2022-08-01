package com.cn.sce.entity;

public class LogContentInfo {
    String time_iso8601;
    String companyId;
    String company;
    String projectNameCN;
    String projectName;
    String componentName;
    String componentMethodName;
    String componentType;
    String componentVer;
    String componentLang;
    String userName;

    public String toString() {
        return "LogContenInfo{time_iso8601='" + this.time_iso8601 + '\'' + ", companyId='" + this.companyId + '\'' + ", company='" + this.company + '\'' + ", projectNameCN='" + this.projectNameCN + '\'' + ", projectName='" + this.projectName + '\'' + ", componentName='" + this.componentName + '\'' + ", componentMethodName='" + this.componentMethodName + '\'' + ", componentType='" + this.componentType + '\'' + ", componentVer='" + this.componentVer + '\'' + ", componentLang='" + this.componentLang + '\'' + ", userName='" + this.userName + '\'' + '}';
    }

    public String getTime_iso8601() {
        return this.time_iso8601;
    }

    public void setTime_iso8601(String time_iso8601) {
        this.time_iso8601 = time_iso8601;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProjectNameCN() {
        return this.projectNameCN;
    }

    public void setProjectNameCN(String projectNameCN) {
        this.projectNameCN = projectNameCN;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentMethodName() {
        return this.componentMethodName;
    }

    public void setComponentMethodName(String componentMethodName) {
        this.componentMethodName = componentMethodName;
    }

    public String getComponentType() {
        return this.componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentVer() {
        return this.componentVer;
    }

    public void setComponentVer(String componentVer) {
        this.componentVer = componentVer;
    }

    public String getComponentLang() {
        return this.componentLang;
    }

    public void setComponentLang(String componentLang) {
        this.componentLang = componentLang;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LogContentInfo() {
    }

    public LogContentInfo(String time_iso8601, String companyId, String company, String projectNameCN, String projectName, String componentName, String componentMethodName, String componentType, String componentVer, String componentLang, String userName) {
        this.time_iso8601 = time_iso8601;
        this.companyId = companyId;
        this.company = company;
        this.projectNameCN = projectNameCN;
        this.projectName = projectName;
        this.componentName = componentName;
        this.componentMethodName = componentMethodName;
        this.componentType = componentType;
        this.componentVer = componentVer;
        this.componentLang = componentLang;
        this.userName = userName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LogContentInfo)) {
            return false;
        } else {
            LogContentInfo other = (LogContentInfo)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label143: {
                    Object this$time_iso8601 = this.getTime_iso8601();
                    Object other$time_iso8601 = other.getTime_iso8601();
                    if (this$time_iso8601 == null) {
                        if (other$time_iso8601 == null) {
                            break label143;
                        }
                    } else if (this$time_iso8601.equals(other$time_iso8601)) {
                        break label143;
                    }

                    return false;
                }

                Object this$companyId = this.getCompanyId();
                Object other$companyId = other.getCompanyId();
                if (this$companyId == null) {
                    if (other$companyId != null) {
                        return false;
                    }
                } else if (!this$companyId.equals(other$companyId)) {
                    return false;
                }

                Object this$company = this.getCompany();
                Object other$company = other.getCompany();
                if (this$company == null) {
                    if (other$company != null) {
                        return false;
                    }
                } else if (!this$company.equals(other$company)) {
                    return false;
                }

                label122: {
                    Object this$projectNameCN = this.getProjectNameCN();
                    Object other$projectNameCN = other.getProjectNameCN();
                    if (this$projectNameCN == null) {
                        if (other$projectNameCN == null) {
                            break label122;
                        }
                    } else if (this$projectNameCN.equals(other$projectNameCN)) {
                        break label122;
                    }

                    return false;
                }

                label115: {
                    Object this$projectName = this.getProjectName();
                    Object other$projectName = other.getProjectName();
                    if (this$projectName == null) {
                        if (other$projectName == null) {
                            break label115;
                        }
                    } else if (this$projectName.equals(other$projectName)) {
                        break label115;
                    }

                    return false;
                }

                Object this$componentName = this.getComponentName();
                Object other$componentName = other.getComponentName();
                if (this$componentName == null) {
                    if (other$componentName != null) {
                        return false;
                    }
                } else if (!this$componentName.equals(other$componentName)) {
                    return false;
                }

                Object this$componentMethodName = this.getComponentMethodName();
                Object other$componentMethodName = other.getComponentMethodName();
                if (this$componentMethodName == null) {
                    if (other$componentMethodName != null) {
                        return false;
                    }
                } else if (!this$componentMethodName.equals(other$componentMethodName)) {
                    return false;
                }

                label94: {
                    Object this$componentType = this.getComponentType();
                    Object other$componentType = other.getComponentType();
                    if (this$componentType == null) {
                        if (other$componentType == null) {
                            break label94;
                        }
                    } else if (this$componentType.equals(other$componentType)) {
                        break label94;
                    }

                    return false;
                }

                label87: {
                    Object this$componentVer = this.getComponentVer();
                    Object other$componentVer = other.getComponentVer();
                    if (this$componentVer == null) {
                        if (other$componentVer == null) {
                            break label87;
                        }
                    } else if (this$componentVer.equals(other$componentVer)) {
                        break label87;
                    }

                    return false;
                }

                Object this$componentLang = this.getComponentLang();
                Object other$componentLang = other.getComponentLang();
                if (this$componentLang == null) {
                    if (other$componentLang != null) {
                        return false;
                    }
                } else if (!this$componentLang.equals(other$componentLang)) {
                    return false;
                }

                Object this$userName = this.getUserName();
                Object other$userName = other.getUserName();
                if (this$userName == null) {
                    if (other$userName != null) {
                        return false;
                    }
                } else if (!this$userName.equals(other$userName)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof LogContentInfo;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $time_iso8601 = this.getTime_iso8601();
        result = result * 59 + ($time_iso8601 == null ? 43 : $time_iso8601.hashCode());
        Object $companyId = this.getCompanyId();
        result = result * 59 + ($companyId == null ? 43 : $companyId.hashCode());
        Object $company = this.getCompany();
        result = result * 59 + ($company == null ? 43 : $company.hashCode());
        Object $projectNameCN = this.getProjectNameCN();
        result = result * 59 + ($projectNameCN == null ? 43 : $projectNameCN.hashCode());
        Object $projectName = this.getProjectName();
        result = result * 59 + ($projectName == null ? 43 : $projectName.hashCode());
        Object $componentName = this.getComponentName();
        result = result * 59 + ($componentName == null ? 43 : $componentName.hashCode());
        Object $componentMethodName = this.getComponentMethodName();
        result = result * 59 + ($componentMethodName == null ? 43 : $componentMethodName.hashCode());
        Object $componentType = this.getComponentType();
        result = result * 59 + ($componentType == null ? 43 : $componentType.hashCode());
        Object $componentVer = this.getComponentVer();
        result = result * 59 + ($componentVer == null ? 43 : $componentVer.hashCode());
        Object $componentLang = this.getComponentLang();
        result = result * 59 + ($componentLang == null ? 43 : $componentLang.hashCode());
        Object $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        return result;
    }
}

