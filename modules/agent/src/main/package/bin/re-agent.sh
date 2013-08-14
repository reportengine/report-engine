#****************************************************************
#Author: Jeeva Kandasamy (jkandasa@redhat.com)
#Date: 27, May 2013
#****************************************************************
#!/bin/bash

JVM_MIN_MAX_SIZE="-Xms4m -Xmx16m"

java $JVM_MIN_MAX_SIZE  -Djava.library.path=../lib/sigar-bin/lib -cp .:../lib/* com.redhat.reportengine.agent.AgentMain
