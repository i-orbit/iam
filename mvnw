#!/bin/sh
# Maven Wrapper
# Licensed under Apache License 2.0

if [ -z "$MAVEN_PROJECTBASEDIR" ]; then
  MAVEN_PROJECTBASEDIR="$( cd "$( dirname "$0" )" >/dev/null 2>&1 && pwd )"
fi

if [ -z "$MAVEN_USER_HOME" ]; then
  export MAVEN_USER_HOME="$HOME/.m2"
fi

JAVA_CMD="java"
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

exec "$JAVA_CMD" \
  "-Dmaven.multiModuleProjectDirectory=$MAVEN_PROJECTBASEDIR" \
  -classpath "$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar" \
  org.apache.maven.wrapper.MavenWrapperMain \
  "$@"
