package com.app.crmapp.Models;

import java.util.List;

public class ReplyDetail {

    /**
     * data : [{"ticketid":"2","contactid":"0","id":"2","userid":"2","message":"jinvzdkjnjkzd nfbvjkdn zfj kjkdf vmk&nbsp; vjkdfzxc"}]
     * message : Successfully
     * status : 1
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ticketid : 2
         * contactid : 0
         * id : 2
         * userid : 2
         * message : jinvzdkjnjkzd nfbvjkdn zfj kjkdf vmk&nbsp; vjkdfzxc
         */

        private String ticketid;
        private String contactid;
        private String id;
        private String userid;
        private String message;

        public String getTicketid() {
            return ticketid;
        }

        public void setTicketid(String ticketid) {
            this.ticketid = ticketid;
        }

        public String getContactid() {
            return contactid;
        }

        public void setContactid(String contactid) {
            this.contactid = contactid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
