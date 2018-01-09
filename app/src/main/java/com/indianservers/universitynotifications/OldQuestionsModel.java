package com.indianservers.universitynotifications;

/**
 * Created by JNTUH on 18-11-2017.
 */

public class OldQuestionsModel {
    public OldQuestionsModel(){

    }
    private String subject;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String year;
    private String pdflink;
    private String examname;
    private String examtype;
    public OldQuestionsModel(String subject, String year, String pdflink, String examname, String examtype) {
        this.subject = subject;
        this.year = year;
        this.pdflink = pdflink;
        this.examname = examname;

        this.examtype = examtype;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPdflink() {
        return pdflink;
    }

    public void setPdflink(String pdflink) {
        this.pdflink = pdflink;
    }

    public String getExamname() {
        return examname;
    }

    public void setExamname(String examname) {
        this.examname = examname;
    }

    public String getExamtype() {
        return examtype;
    }

    public void setExamtype(String examtype) {
        this.examtype = examtype;
    }
}
