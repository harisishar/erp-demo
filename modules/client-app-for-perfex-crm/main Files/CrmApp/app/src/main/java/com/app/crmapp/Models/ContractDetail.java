package com.app.crmapp.Models;

import java.util.List;

/**
 * Created by LBMSOLUTIONS on 29-10-2019.
 */

public class ContractDetail {
    /**
     * data : [{"datestart":"2019-10-09","dateend":"2019-10-23","contract_value":"45435.00","description":"dggdfgfdgfgdfgdfg"}]
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
         * datestart : 2019-10-09
         * dateend : 2019-10-23
         * contract_value : 45435.00
         * description : dggdfgfdgfgdfgdfg
         */

        private String datestart;
        private String dateend;
        private String contract_value;
        private String description;

        public String getDatestart() {
            return datestart;
        }

        public void setDatestart(String datestart) {
            this.datestart = datestart;
        }

        public String getDateend() {
            return dateend;
        }

        public void setDateend(String dateend) {
            this.dateend = dateend;
        }

        public String getContract_value() {
            return contract_value;
        }

        public void setContract_value(String contract_value) {
            this.contract_value = contract_value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
