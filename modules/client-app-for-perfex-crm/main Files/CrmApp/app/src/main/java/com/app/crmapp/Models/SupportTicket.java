package com.app.crmapp.Models;

import java.util.List;

/**
 * Created by LBMSOLUTIONS on 23-10-2019.
 */

public class SupportTicket {

    /**
     * data : [{"ticketid":"112","name":"manoj","subject":"invitation","status":"3","department":"3","message":"manoj mad","date":"2019-12-12 00:00:00","userid":"2"}]
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
         * ticketid : 112
         * name : manoj
         * subject : invitation
         * status : 3
         * department : 3
         * message : manoj mad
         * date : 2019-12-12 00:00:00
         * userid : 2
         */

        private String ticketid;
        private String name;
        private String subject;
        private String status;
        private String department;
        private String message;
        private String date;
        private String userid;

        public String getTicketid() {
            return ticketid;
        }

        public void setTicketid(String ticketid) {
            this.ticketid = ticketid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
