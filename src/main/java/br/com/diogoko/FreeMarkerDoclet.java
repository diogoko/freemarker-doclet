package br.com.diogoko;

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.RootDoc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Hello world!
 */
public class FreeMarkerDoclet {

    private static String output = null;

    private static String template = null;

    public static boolean start(RootDoc root) {
        try {
            Configuration cfg = createConfiguration();
            Template t = cfg.getTemplate(new File(template).getName());
            Writer w = new FileWriter(new File(output));
            t.process(root, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private static Configuration createConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File(template).getParentFile());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);

        return cfg;
    }

    public static int optionLength(String option) {
        if (option.equals("-o")) {
            return 2;
        } else if (option.equals("-t")) {
            return 2;
        }

        return 0;
    }

    public static boolean validOptions(String options[][],
                                       DocErrorReporter reporter) {
        boolean foundOutput = false;
        boolean foundTemplate = false;

        for (int i = 0; i < options.length; i++) {
            String[] opt = options[i];
            if (opt[0].equals("-o")) {
                foundOutput = true;
                output = opt[1];
            } else if (opt[0].equals("-t")) {
                foundTemplate = true;
                template = opt[1];
            }
        }

        if (!foundOutput || !foundTemplate) {
            reporter.printError("Usage: javadoc -o OUTPUT_FILE -t TEMPLATE_FILE ...");
            return false;
        }

        return true;
    }

}
