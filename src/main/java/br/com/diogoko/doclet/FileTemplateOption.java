package br.com.diogoko.doclet;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;

public class FileTemplateOption implements TemplateOption {
    private final String path;

    public FileTemplateOption(String path) {
        this.path = path;
    }

    @Override
    public String getName() {
        return path;
    }

    @Override
    public void configure(Configuration cfg) {
        try {
            // TODO: handle Windows roots
            cfg.setDirectoryForTemplateLoading(new File("/"));
        } catch (IOException e) {
            throw new RuntimeException("Error while configuring file template: " + path, e);
        }
    }
}
