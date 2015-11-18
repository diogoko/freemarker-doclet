# freemarker-doclet

A [Doclet](https://en.wikipedia.org/wiki/Doclet) for using [FreeMarker](http://freemarker.org/) with [JavaDoc](https://en.wikipedia.org/wiki/Javadoc). It provides all data collected by JavaDoc in a [RootDoc](https://docs.oracle.com/javase/7/docs/jdk/api/javadoc/doclet/com/sun/javadoc/RootDoc.html) instance to a template whose output is directed to a file.

## Usage

This doclet accepts the following parameters:

|Parameter |Description|
|-----|-----------|
|`-ft TEMPLATE`|Path to the template file in the file system|
|`-ct TEMPLATE`|Package and template file name in the doclet classpath, separated by slashes|
|`-o OUTPUT`|Path to the output file in the file system|
|`-extra NAME VALUE`|Extra value that can be read in the template from the root object model's `options()` method (together with all other Javadoc parameters)|

Only one template file can be specified.

As many extra parameters as needed may be defined.

The rest of the parameters is the same as the [core Javadoc args](http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#javadocoptions). None of the [standard doclet's parameters](http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javadoc.html#standard) is available.

### Maven

Most of the time you'll probably use templates from the classpath:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>2.10.3</version>
    <configuration>
        <doclet>br.com.diogoko.doclet.FreeMarkerDoclet</doclet>

        <docletArtifacts>
            <docletArtifact>
                <groupId>com.example</groupId>
                <artifactId>sample-template</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </docletArtifact>
            <docletArtifact>
                <groupId>br.com.diogoko</groupId>
                <artifactId>freemarker-doclet</artifactId>
                <version>1.0.0</version>
            </docletArtifact>
        </docletArtifacts>

        <additionalparam>-o ${project.build.directory}/index.html -ct "/com/example/template/index.html.ftl"</additionalparam>
        <useStandardDocletOptions>false</useStandardDocletOptions>
    </configuration>
</plugin>
```

Nevertheless, you can also use templates from the file system:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>2.10.3</version>
    <configuration>
        <doclet>br.com.diogoko.doclet.FreeMarkerDoclet</doclet>

        <docletArtifacts>
            <docletArtifact>
                <groupId>br.com.diogoko</groupId>
                <artifactId>freemarker-doclet</artifactId>
                <version>1.0.0</version>
            </docletArtifact>
        </docletArtifacts>

        <additionalparam>-o ${project.build.directory}/index.html -ft "C:/example/templates/index.html.ftl"</additionalparam>
        <useStandardDocletOptions>false</useStandardDocletOptions>
    </configuration>
</plugin>
```

See the [maven-doclet-plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/examples/alternate-doclet.html) documentation for more details.

### Command line

To run from the command line, check `run.cmd` or `run.sh`. 

## License

freemarker-doclet is MIT licensed.
