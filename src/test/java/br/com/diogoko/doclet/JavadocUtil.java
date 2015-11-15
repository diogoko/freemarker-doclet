package br.com.diogoko.doclet;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JavadocUtil {
    /**
     * Calls Javadoc tool.
     *
     * <p>The arguments needed to call this project's doclet
     * and use the test resource's directory as source path
     * are already included.</p>
     *
     * @param extraArgs Any extra arguments to Javadoc
     */
    public static JavadocResult callJavadoc(String docletClass, String... extraArgs) {
        final String programName = "TEST";

        Path root = getPathFromResources("source/java");

        List<String> baseArgs = Arrays.asList(
                "-sourcepath", root.toString()
        );
        List<String> args = new LinkedList<String>(baseArgs);
        for (String arg : extraArgs) {
            args.add(arg);
        }

        JavadocResult result = new JavadocResult();
        int returnCode = com.sun.tools.javadoc.Main.execute(
                programName,
                result.getErrWriter(),
                result.getWarnWriter(),
                result.getNoticeWriter(),
                docletClass,
                args.toArray(new String[0])
        );
        result.setReturnCode(returnCode);

        return result;
    }

    public static Path getPathFromResources(String path) {
        try {
            Path root = Paths.get(FreeMarkerDocletTest.class.getResource("/root.txt").toURI())
                    .getParent();
            return root.resolve(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("This should never happen", e);
        }
    }
}
