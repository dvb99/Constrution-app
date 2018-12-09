
package com.example.admin.constructionsite.firstpageafterLogin;


public class Card {

    private String cdtitle;
    private String url;
    private int cdcolor;

    public Card(String cdtitle) {
        this.cdtitle = cdtitle;
    }

    public Card(String cdtitle, String url, int cdcolor) {
        this.cdtitle = cdtitle;
        this.url = url;
        this.cdcolor = cdcolor;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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