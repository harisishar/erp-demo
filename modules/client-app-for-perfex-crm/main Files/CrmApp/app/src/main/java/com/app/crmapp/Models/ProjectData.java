package com.app.crmapp.Models;

import java.util.List;

/**
 * Created by LBMSOLUTIONS on 23-10-2019.
 */

public class ProjectData {

    /**
     * data : [{"name":"test","description":"dfsdsfdsfsdfdsfsdfsdffdf","status":"1","progress":"3","start_date":"2019-10-22"}]
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
         * name : test
         * description : dfsdsfdsfsdfdsfsdfsdffdf
         * status : 1
         * progress : 3
         * start_date : 2019-10-22
         */

        private String name;
        private String description;
        private String status;
        private String progress;
        private String start_date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }
    }
}
