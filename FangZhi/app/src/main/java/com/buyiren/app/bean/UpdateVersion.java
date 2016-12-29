package com.buyiren.app.bean;

/**
 * Created by smacr on 2016/11/4.
 */
public class UpdateVersion extends BaseResponseBean{
    Version editionMap;

    public Version getEditionMap() {
        return editionMap;
    }

    public void setEditionMap(Version editionMap) {
        this.editionMap = editionMap;
    }

    public class Version{
        String EDITION_NO;
        String EDITION_STATUS;
        String URL;

        public String getEDITION_NO() {
            return EDITION_NO;
        }

        public void setEDITION_NO(String EDITION_NO) {
            this.EDITION_NO = EDITION_NO;
        }

        public String getEDITION_STATUS() {
            return EDITION_STATUS;
        }

        public void setEDITION_STATUS(String EDITION_STATUS) {
            this.EDITION_STATUS = EDITION_STATUS;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }
    }
}
