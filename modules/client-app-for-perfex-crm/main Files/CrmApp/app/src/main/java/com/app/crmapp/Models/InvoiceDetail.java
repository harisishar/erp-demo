package com.app.crmapp.Models;

import java.util.List;

/**
 * Created by LBMSOLUTIONS on 29-10-2019.
 */

public class InvoiceDetail {

    /**
     * total : 90000000.00
     * pending : 89997600
     * invoiceDetail : [{"id":"1","duedate":"2019-10-31","date":"2019-10-23","total":"90000000.00","currency":"7676","status":"1","clientid":"2","number":"2132513","prefix":"ojoihji"}]
     * message : Successfully
     * status : 200
     */

    private String total;
    private int pending;
    private String message;
    private int status;
    private List<InvoiceDetailBean> invoiceDetail;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
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

    public List<InvoiceDetailBean> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<InvoiceDetailBean> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public static class InvoiceDetailBean {
        /**
         * id : 1
         * duedate : 2019-10-31
         * date : 2019-10-23
         * total : 90000000.00
         * currency : 7676
         * status : 1
         * clientid : 2
         * number : 2132513
         * prefix : ojoihji
         */

        private String id;
        private String duedate;
        private String date;
        private String total;
        private String currency;
        private String status;
        private String clientid;
        private String number;
        private String prefix;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDuedate() {
            return duedate;
        }

        public void setDuedate(String duedate) {
            this.duedate = duedate;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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

        public String getClientid() {
            return clientid;
        }

        public void setClientid(String clientid) {
            this.clientid = clientid;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
