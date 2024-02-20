package com.app.crmapp.Models;

import java.util.List;

/**
 * Created by LBMSOLUTIONS on 06-11-2019.
 */

public class DepartmentModel {
    /**
     * data : [{"departmentid":"1","name":"Development"},{"departmentid":"5","name":"hirring"},{"departmentid":"2","name":"invitation"},{"departmentid":"4","name":"invite"},{"departmentid":"3","name":"Urgent Work"}]
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
         * departmentid : 1
         * name : Development
         */

        private String departmentid;
        private String name;

        public String getDepartmentid() {
            return departmentid;
        }

        public void setDepartmentid(String departmentid) {
            this.departmentid = departmentid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
