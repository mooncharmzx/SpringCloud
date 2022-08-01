package com.cn.sce.license;

public class ValidateResult {

    private boolean result;
    private String error;

    ValidateResult(boolean result, String error) {
        this.result = result;
        this.error = error;
    }

    public static ValidateResult.ValidateResultBuilder builder() {
        return new ValidateResult.ValidateResultBuilder();
    }

    public boolean isResult() {
        return this.result;
    }

    public String getError() {
        return this.error;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ValidateResult)) {
            return false;
        } else {
            ValidateResult other = (ValidateResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isResult() != other.isResult()) {
                return false;
            } else {
                Object this$error = this.getError();
                Object other$error = other.getError();
                if (this$error == null) {
                    if (other$error != null) {
                        return false;
                    }
                } else if (!this$error.equals(other$error)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ValidateResult;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + (this.isResult() ? 79 : 97);
        Object $error = this.getError();
        result = result * 59 + ($error == null ? 43 : $error.hashCode());
        return result;
    }

    public String toString() {
        return "ValidateResult(result=" + this.isResult() + ", error=" + this.getError() + ")";
    }

    public static class ValidateResultBuilder {
        private boolean result;
        private String error;

        ValidateResultBuilder() {
        }

        public ValidateResult.ValidateResultBuilder result(boolean result) {
            this.result = result;
            return this;
        }

        public ValidateResult.ValidateResultBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ValidateResult build() {
            return new ValidateResult(this.result, this.error);
        }

        public String toString() {
            return "ValidateResult.ValidateResultBuilder(result=" + this.result + ", error=" + this.error + ")";
        }
    }

}
