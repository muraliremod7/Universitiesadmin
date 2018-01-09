package com.indianservers.universitynotifications;

/**
 * Created by Prabhu on 04-01-2018.
 */

public class ModelSets {
    public ModelSets(){

    }

    public ModelSets(String modelname) {
        this.modelname = modelname;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    private String modelname;

    public String getQno() {
        return Qno;
    }

    public void setQno(String qno) {
        Qno = qno;
    }

    private String Qno;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String getOpt5() {
        return opt5;
    }

    public void setOpt5(String opt5) {
        this.opt5 = opt5;
    }

    private String Question;

    public ModelSets(String modelname, String question, String modelkey, String answer, String opt1, String opt2, String opt3, String opt4, String opt5) {
        this.modelname = modelname;
        Question = question;
        this.modelkey = modelkey;
        Answer = answer;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.opt5 = opt5;
    }

    public String getModelkey() {
        return modelkey;
    }

    public void setModelkey(String modelkey) {
        this.modelkey = modelkey;
    }

    private String modelkey;
    private String Answer;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;
    private String opt5;
}
