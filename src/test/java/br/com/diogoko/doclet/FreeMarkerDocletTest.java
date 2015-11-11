package br.com.diogoko.doclet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class FreeMarkerDocletTest
{
    @Rule
    public TemporaryFolder outputDir = new TemporaryFolder();

    @Test
    public void singleOutput() throws IOException {
        File outputFile = outputDir.newFile();
        File templateFile = getPathFromResources("source/freemarker/singleFile/template.ftl").toFile();
        callJavadoc("com.sample",
                "-o", outputFile.toString(),
                "-t", templateFile.toString());

        File expectedFile = getPathFromResources("expected-output/singleFile/output.html").toFile();
        assertThat(outputFile).hasSameContentAs(expectedFile);
    }

    /**
     * Calls Javadoc tool.
     *
     * <p>The arguments needed to call this project's doclet
     * and use the test resource's directory as source path
     * are already included.</p>
     *
     * @param extraArgs Any extra arguments to Javadoc
     */
    private static void callJavadoc(String ...extraArgs) {
        final String docletClass = "br.com.diogoko.doclet.FreeMarkerDoclet";

        Path root = getPathFromResources("source/java");

        List<String> baseArgs = Arrays.asList(
                "-doclet", docletClass,
                "-sourcepath", root.toString()
        );
        List<String> args = new LinkedList<String>(baseArgs);
        for (String arg : extraArgs) {
            args.add(arg);
        }

        com.sun.tools.javadoc.Main.execute(args.toArray(new String[0]));
    }

    private static Path getPathFromResources(String path) {
        try {
            Path root = Paths.get(FreeMarkerDocletTest.class.getResource("/root.txt").toURI())
                    .getParent();
            return root.resolve(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("This should never happen", e);
        }
    }
}
