package com.app.crmapp.Models;

import java.util.List;

/**
 * Created by LBMSOLUTIONS on 24-10-2019.
 */

public class ProposalDetail {

    /**
     * value : {"total":"1000.00"}
     * data : [{"date":"2019-10-22","currency":"200","status":"1","open_till":"2019-10-30","rel_id":"2","datecreated":"2019-10-22 00:00:00"}]
     * message : Successfully
     * status : 1
     */

    private ValueBean value;
    private String message;
    private int status;
    private List<DataBean> data;

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

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

    public static class ValueBean {
        /**
         * total : 1000.00
         */

        private String total;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public static class DataBean {
        /**
         * date : 2019-10-22
         * currency : 200
         * status : 1
         * open_till : 2019-10-30
         * rel_id : 2
         * datecreated : 2019-10-22 00:00:00
         */

        private String date;
        private String currency;
        private String status;
        private String open_till;
        private String rel_id;
        private String datecreated;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOpen_till() {
            return open_till;
        }

        public void setOpen_till(String open_till) {
            this.open_till = open_till;
        }

        public String getRel_id() {
            return rel_id;
        }

        public void setRel_id(String rel_id) {
            this.rel_id = rel_id;
        }

        public String getDatecreated() {
            return datecreated;
        }

        public void setDatecreated(String datecreated) {
            this.datecreated = datecreated;
        }
    }
}