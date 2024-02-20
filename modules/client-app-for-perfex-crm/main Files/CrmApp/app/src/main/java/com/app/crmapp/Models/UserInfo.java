package com.app.crmapp.Models;

import java.util.List;

/**
 * Created by LBMSOLUTIONS on 07-11-2019.
 */

public class UserInfo {

    /**
     * detail : {"firstname":"Rahul","lastname":"Kumar","profile_image":"Tulips.jpg","userid":"2"}
     * company_name : [{"company":"solutions","userid":"2"}]
     * message : Successfully
     * status : 1
     */

    private DetailBean detail;
    private String message;
    private int status;
    private List<CompanyNameBean> company_name;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
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

    public List<CompanyNameBean> getCompany_name() {
        return company_name;
    }

    public void setCompany_name(List<CompanyNameBean> company_name) {
        this.company_name = company_name;
    }

    public static class DetailBean {
        /**
         * firstname : Rahul
         * lastname : Kumar
         * profile_image : Tulips.jpg
         * userid : 2
         */

        private String firstname;
        private String lastname;
        private String profile_image;
        private String userid;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

    public static class CompanyNameBean {
        /**
         * company : solutions
         * userid : 2
         */

        private String company;
        private String userid;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
