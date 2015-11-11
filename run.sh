#!/bin/bash
javadoc -docletpath target/freemarker-doclet-1.0.0-SNAPSHOT-jar-with-dependencies.jar -doclet br.com.diogoko.doclet.FreeMarkerDoclet "$@"
