/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.admin.constructionsite.firstpageafterLogin;


public class firstpage {

    private String cardtitle;
    private int mimageid;
    private int mcolor;

    public firstpage(String cardtitle) {
        this.cardtitle = cardtitle;
    }

    public firstpage(String cardtitle, int mimageid, int mcolor) {
        this.cardtitle = cardtitle;
        this.mimageid = mimageid;
        this.mcolor = mcolor;
    }

    public int getMimageid() {

        return mimageid;
    }

    public void setMimageid(int mimageid) {
        this.mimageid = mimageid;
    }

    public String getCardtitle() {
        return cardtitle;
    }

    public void setCardtitle(String cardtitle) {
        this.cardtitle = cardtitle;
    }

    public int getMcolor() {
        return mcolor;
    }

    public void setMcolor(int mcolor) {
        this.mcolor = mcolor;
    }


}