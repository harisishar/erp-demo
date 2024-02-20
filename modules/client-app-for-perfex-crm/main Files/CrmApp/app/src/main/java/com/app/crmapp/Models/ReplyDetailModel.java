package com.app.crmapp.Models;

import java.util.List;

public class ReplyDetailModel {

    /**
     * data : [{"id":"233","ticketid":"112","file_name":"Screenshot_20191204_201630_com.android.chrome.jpg","admin":null,"message":"Be happy","firstname":null,"lastname":null},{"id":"315","ticketid":"112","file_name":null,"admin":null,"message":"Hii","firstname":null,"lastname":null}]
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
         * id : 233
         * ticketid : 112
         * file_name : Screenshot_20191204_201630_com.android.chrome.jpg
         * admin : null
         * message : Be happy
         * firstname : null
         * lastname : null
         */

        private String id;
        private String ticketid;
        private String file_name;
        private Object admin;
        private String message;
        private Object firstname;
        private Object lastname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTicketid() {
            return ticketid;
        }

        public void setTicketid(String ticketid) {
            this.ticketid = ticketid;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public Object getAdmin() {
            return admin;
        }

        public void setAdmin(Object admin) {
            this.admin = admin;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getFirstname() {
            return firstname;
        }

        public void setFirstname(Object firstname) {
            this.firstname = firstname;
        }

        public Object getLastname() {
            return lastname;
        }

        public void setLastname(Object lastname) {
            this.lastname = lastname;
        }
    }
}
