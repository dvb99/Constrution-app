
package com.example.admin.constructionsite.firstpageafterLogin;


public class Card {

    private String cdtitle;
    private int cdimageid;
    private int cdcolor;

    public Card(String cdtitle) {
        this.cdtitle = cdtitle;
    }

    public Card(String cdtitle, int cdimageid, int cdcolor) {
        this.cdtitle = cdtitle;
        this.cdimageid = cdimageid;
        this.cdcolor = cdcolor;
    }

    public int getCdimageid() {

        return cdimageid;
    }

    public void setCdimageid(int cdimageid) {
        this.cdimageid = cdimageid;
    }

    public String getCdtitle() {
        return cdtitle;
    }

    public void setCdtitle(String cdtitle) {
        this.cdtitle = cdtitle;
    }

    public int getCdcolor() {
        return cdcolor;
    }

    public void setCdcolor(int cdcolor) {
        this.cdcolor = cdcolor;
    }


}