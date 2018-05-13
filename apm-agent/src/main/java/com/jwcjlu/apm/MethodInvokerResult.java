package com.jwcjlu.apm;

public class MethodInvokerResult {
    private boolean isContinue = true;
    public boolean isContinue(){
        return isContinue;
    }
    private Object ret = null;

    /**
     * define the new return value.
     *
     * @param ret new return value.
     */
    public void defineReturnValue(Object ret) {
        this.isContinue = false;
        this.ret = ret;
    }
    /**
     * @return the new return value.
     */
    Object _ret() {
        return ret;
    }
}
