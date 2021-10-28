#!/bin/bash

pushd ../
mvn install:install-file -Dfile=./libs/com.ibm.mq.commonservices.jar -DgroupId=com.ibm.mq -DartifactId=commonservices -Dversion=p750-001-130308 -Dpackaging=jar
mvn install:install-file -Dfile=./libs/com.ibm.mq.jar -DgroupId=com.ibm.mq -DartifactId=mq -Dversion=7.5.0.1 -Dpackaging=jar
mvn install:install-file -Dfile=./libs/com.ibm.mq.headers.jar -DgroupId=com.ibm.mq -DartifactId=headers -Dversion=7.5.0.1 -Dpackaging=jar
mvn install:install-file -Dfile=./libs/com.ibm.mq.jmqi.jar -DgroupId=com.ibm.mq -DartifactId=jmqi -Dversion=7.5.0.1 -Dpackaging=jar
mvn install:install-file -Dfile=./libs/com.ibm.mq.pcf.jar -DgroupId=com.ibm.mq -DartifactId=pcf -Dversion=7.5.0.5 -Dpackaging=jar
mvn install:install-file -Dfile=./libs/jr-afiliacion-susalud-1.0.0.jar -DgroupId=pe.gob.susalud -DartifactId=jr-afiliacion-susalud -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=./libs/jr-acreditacion-susalud-1.0.0.jar -DgroupId=pe.gob.susalud -DartifactId=jr-acreditacion-susalud -Dversion=1.0.0 -Dpackaging=jar
popd
