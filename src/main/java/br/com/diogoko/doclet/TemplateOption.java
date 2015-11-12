package br.com.diogoko.doclet;

import freemarker.template.Configuration;

public interface TemplateOption {
    String getName();

    void configure(Configuration cfg);
}
