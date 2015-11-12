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
        return new File(path).getName();
    }

    @Override
    public void configure(Configuration cfg) {
        try {
            cfg.setDirectoryForTemplateLoading(new File(path).getParentFile());
        } catch (IOException e) {
            throw new RuntimeException("Error while configuring file template: " + path, e);
        }
    }
}
