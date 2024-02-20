package com.app.crmapp.Models;

import java.util.List;

public class TicketStatus {

    /**
     * data : [{"ticketstatusid":"1","name":"Open"},{"ticketstatusid":"2","name":"In progress"},{"ticketstatusid":"3","name":"Answered"},{"ticketstatusid":"4","name":"On Hold"},{"ticketstatusid":"5","name":"Closed"}]
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
         * ticketstatusid : 1
         * name : Open
         */

        private String ticketstatusid;
        private String name;

        public String getTicketstatusid() {
            return ticketstatusid;
        }

        public void setTicketstatusid(String ticketstatusid) {
            this.ticketstatusid = ticketstatusid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
