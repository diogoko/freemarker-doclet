package br.com.diogoko.doclet;

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.nio.charset.Charset;

public class FreeMarkerDoclet {

    private static String output = null;

    private static TemplateOption template = null;

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
    
    public static boolean start(RootDoc root) {
        try {
            Configuration cfg = createConfiguration();
            Template t = cfg.getTemplate(template.getName());

            File f = new File(output);
            FileOutputStream fos = new FileOutputStream(f);
            OutputStreamWriter w = new OutputStreamWriter(fos, Charset.forName("UTF-8"));

            t.process(root, w);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            root.printError(sw.toString());

            return false;
        }
        return true;
    }

    private static Configuration createConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        template.configure(cfg);

        return cfg;
    }

    public static int optionLength(String option) {
        if (option.equals("-o")) {
            return 2;
        } else if (option.equals("-ft")) {
            return 2;
        } else if (option.equals("-ct")) {
            return 2;
        } else if (option.equals("-extra")) {
            return 3;
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
            } else if (opt[0].equals("-ft")) {
                foundTemplate = true;
                template = new FileTemplateOption(opt[1]);
            } else if (opt[0].equals("-ct")) {
                foundTemplate = true;
                template = new ClasspathTemplateOption(opt[1]);
            }
        }

        if (!foundOutput || !foundTemplate) {
            reporter.printError("Usage: javadoc -o OUTPUT_FILE -t TEMPLATE_FILE ...");
            return false;
        }

        return true;
    }

}
