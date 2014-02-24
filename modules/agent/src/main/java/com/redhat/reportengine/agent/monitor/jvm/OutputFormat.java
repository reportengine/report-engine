package com.redhat.reportengine.agent.monitor.jvm;
public interface OutputFormat {

    public void writeOutput(String args) throws Exception;
}
