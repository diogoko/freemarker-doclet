package br.com.diogoko.doclet;

import freemarker.template.Configuration;

import java.io.File;

public class ClasspathTemplateOption implements TemplateOption {
    private final String path;

    public ClasspathTemplateOption(String path) {
        this.path = path;
    }

    @Override
    public String getName() {
        return path;
    }

    @Override
    public void configure(Configuration cfg) {
        cfg.setClassForTemplateLoading(this.getClass(), "/");
    }
}
