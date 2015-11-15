package br.com.diogoko.doclet;

import java.io.PrintWriter;
import java.io.StringWriter;

public class JavadocResult {
    private StringWriter serrWriter;

    private StringWriter swarnWriter;

    private StringWriter snoticeWriter;

    private PrintWriter perrWriter;

    private PrintWriter pwarnWriter;

    private PrintWriter pnoticeWriter;

    private int returnCode;

    public JavadocResult() {
        serrWriter = new StringWriter();
        perrWriter = new PrintWriter(serrWriter);

        swarnWriter = new StringWriter();
        pwarnWriter = new PrintWriter(swarnWriter);

        snoticeWriter = new StringWriter();
        pnoticeWriter = new PrintWriter(snoticeWriter);
    }

    public PrintWriter getErrWriter() {
        return perrWriter;
    }

    public PrintWriter getWarnWriter() {
        return pwarnWriter;
    }

    public PrintWriter getNoticeWriter() {
        return pnoticeWriter;
    }

    public String getErr() {
        perrWriter.flush();
        return serrWriter.toString();
    }

    public String getWarn() {
        pwarnWriter.flush();
        return swarnWriter.toString();
    }

    public String getNotice() {
        pnoticeWriter.flush();
        return snoticeWriter.toString();
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
}
