package br.com.diogoko.doclet;

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
    public void error() throws IOException {
        File outputFile = outputDir.newFile();
        String templateFile = "/source/freemarker/error/template.ftl";
        JavadocResult result = callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-ct", templateFile);

        assertThat(result.getReturnCode()).isNotZero();
        assertThat(result.getErr()).contains("Unknown directive");
    }

    private static JavadocResult callJavadoc(String... extraArgs) {
        final String docletClass = "br.com.diogoko.doclet.FreeMarkerDoclet";
        return JavadocUtil.callJavadoc(docletClass, extraArgs);
    }
}
