package com.redhat.reportengine.agent.monitor.jvm;

public class ConsoleOutputFormat implements OutputFormat {

    public ConsoleOutputFormat(String vmSubSystem){
        subSystem = vmSubSystem;
    }
    
    @Override
    public void writeOutput(String output) {
        if (firstTime) {
            System.out.printf(rowPrefixFormat, header[0], header[1]);
            
            if(subSystem.equalsIgnoreCase("h")) {
                
                System.out.printf(hostMetricsFormat, header[2]);
                
            } else if(subSystem.equalsIgnoreCase("m")) {
                
                System.out.printf(memHeaderFormat, header[3], header[4], header[5], header[6]);
                
            } else if(subSystem.equalsIgnoreCase("c")){
                
                System.out.printf(clasHeaderFormat, header[7], header[8], header[9]); 
                
            } else if(subSystem.equalsIgnoreCase("t")){
                
                System.out.printf(thdHeaderFormat, header[10], header[11], header[12], header[13]);
                
            } else if(subSystem.equalsIgnoreCase("n")){
                
                System.out.printf(compHeaderFormat, header[14]);
                
            } else if(subSystem.equalsIgnoreCase("o")){
                
                System.out.printf("");
            
            } else {    
            
                System.out.printf(memHeaderFormat, header[3], header[4], header[5], header[6]); // default
            
            }
            firstTime = false;
        }

        String[] countersList = output.split(";");
        for(String countersPerVM : countersList){
            String counters[] = countersPerVM.split(",");
            int totalCounters = counters.length;
            
            System.out.println();
            String timestamp[] = counters[0].split(" ");
            System.out.printf(rowPrefixFormat, timestamp[1], counters[1]);

            if(subSystem.equalsIgnoreCase("h")){

                System.out.printf(hostMetricsFormat, counters[2]);

            } else if(subSystem.equalsIgnoreCase("m")){

                System.out.printf(memHeaderFormat, counters[3], counters[4], counters[5], counters[6]);

            } else if(subSystem.equalsIgnoreCase("c")){

                System.out.printf(clasHeaderFormat, counters[7], counters[8], counters[9]);

            } else if(subSystem.equalsIgnoreCase("t")){

                System.out.printf(thdHeaderFormat, counters[10], counters[11], counters[12], counters[13]);

            } else if(subSystem.equalsIgnoreCase("n")){

                System.out.printf(compHeaderFormat, counters[14]);

            } else if(subSystem.equalsIgnoreCase("o")) {
                for(int i=15; i < totalCounters; i++)
                    System.out.printf(counters[i] + "  ");
            } else {

                System.out.printf(memHeaderFormat, counters[3], counters[4], counters[5], counters[6]);
            }

        }
        System.out.println();
    }
    private boolean firstTime = true;
    private static String subSystem;
    private static final String rowPrefixFormat = "%-10s %-20s ";
    private static final String hostMetricsFormat = "%-15s";
    private static final String memHeaderFormat = "%-6s %-6s %-11s %-11s ";
    private static final String clasHeaderFormat = "%-12s %-12s %-13s ";
    private static final String thdHeaderFormat = "%-6s %-7s %-8s %-12s ";
    private static final String compHeaderFormat = "%-12s";
    private static final String[] header = {"timestamp", "proc_id", // mandatory columns
                                            "1min_sys_ld_avg", // host
                                            "usd_hp", "com_hp", "usd_non-hp", "com_non-hp", // memory
                                            "cur_ld_clas", "tot_ld_clas", "tot_uld_clas", // classes
                                            "d_thd", "pk_thd", "cur_thd", "tot_std_thd", // threads
                                            "tot_comp_tm"}; // compilation
    
    
}