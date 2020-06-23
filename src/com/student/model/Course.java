package com.student.model;

/**
 * @Description: Course
 * @ClassName: Course
 * 
 */
public class Course {

    private int cno;
    private String cname;
    private int credit;
    private String cdept;
    private String tname;

    public Course(int cno){
        this.cno = cno;
    }
    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getCdept() {
        return cdept;
    }

    public void setCdept(String cdept) {
        this.cdept = cdept;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
}
