package br.com.diogoko.doclet;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class FreeMarkerDocletTest
{
    @Rule
    public TemporaryFolder outputDir = new TemporaryFolder();

    @Test
    public void fileTemplate() throws IOException {
        File outputFile = outputDir.newFile();
        File templateFile = JavadocUtil.getPathFromResources("source/freemarker/singleFile/template.ftl").toFile();
        JavadocResult result = callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-ft", templateFile.toString());

        assertThat(result.getReturnCode()).isZero();
        File expectedFile = JavadocUtil.getPathFromResources("expected-output/singleFile/output.html").toFile();
        assertThat(outputFile).hasSameContentAs(expectedFile);
    }

    @Test
    public void classTemplate() throws IOException {
        File outputFile = outputDir.newFile();
        String templateFile = "/source/freemarker/singleFile/template.ftl";
        JavadocResult result = callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-ct", templateFile);

        assertThat(result.getReturnCode()).isZero();
        File expectedFile = JavadocUtil.getPathFromResources("expected-output/singleFile/output.html").toFile();
        assertThat(outputFile).hasSameContentAs(expectedFile);
    }

    @Test
    public void include() throws IOException {
        File outputFile = outputDir.newFile();
        String templateFile = "/source/freemarker/include/index.ftl";
        JavadocResult result = callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-ct", templateFile);

        assertThat(result.getReturnCode()).isZero();
        File expectedFile = JavadocUtil.getPathFromResources("expected-output/include/output.html").toFile();
        assertThat(outputFile).hasSameContentAs(expectedFile);
    }

    @Test
    public void absoluteIncludeClasspath() throws IOException {
        File outputFile = outputDir.newFile();
        String templateFile = "/source/freemarker/include/absoluteIndex.ftl";
        JavadocResult result = callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-ct", templateFile);

        assertThat(result.getReturnCode()).isZero();
        File expectedFile = JavadocUtil.getPathFromResources("expected-output/include/output.html").toFile();
        assertThat(outputFile).hasSameContentAs(expectedFile);
    }

    @Test
    public void absoluteIncludeFile() throws IOException {
        File functionsFile = JavadocUtil.getPathFromResources("source/freemarker/include/functions.ftl").toFile();

        StringBuilder sb = new StringBuilder();
        sb.append("<#include \"");
        sb.append(functionsFile.toString());
        sb.append("\">\n");
        sb.append("<p>The sum of 3 with 5 is ${my_sum(3, 5)}</p>");

        File templateFile = outputDir.newFile();
        FileUtils.writeStringToFile(templateFile, sb.toString());

        File outputFile = outputDir.newFile();
        JavadocResult result = callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-ft", templateFile.toString());

        assertThat(result.getReturnCode()).isZero();
        File expectedFile = JavadocUtil.getPathFromResources("expected-output/include/output.html").toFile();
        assertThat(outputFile).hasSameContentAs(expectedFile);
    }

    @Test
    public void error() throws IOException {
        File outputFile = outputDir.newFile();
        String templateFile = "/source/freemarker/error/template.ftl";
        JavadocResult result = callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-ct", templateFile);

        assertThat(result.getReturnCode()).isNotZero();
        assertThat(result.getErr()).contains("Unknown directive");
    }

    @Test
    public void extraArgs() throws IOException {
        File outputFile = outputDir.newFile();
        String templateFile = "/source/freemarker/extraArgs/template.ftl";
        JavadocResult result = callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-ct", templateFile,
                "-extra", "title", "Example",
                "-extra", "phone", "555-5555");

        assertThat(result.getReturnCode()).isZero();
        File expectedFile = JavadocUtil.getPathFromResources("expected-output/extraArgs/output.html").toFile();
        assertThat(outputFile).hasSameContentAs(expectedFile);
    }

    private static JavadocResult callJavadoc(String... extraArgs) {
        final String docletClass = "br.com.diogoko.doclet.FreeMarkerDoclet";
        return JavadocUtil.callJavadoc(docletClass, extraArgs);
    }
}
