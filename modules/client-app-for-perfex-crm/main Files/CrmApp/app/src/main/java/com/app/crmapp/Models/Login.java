package com.app.crmapp.Models;

/**
 * Created by monika on 23-10-2019.
 */

public class Login {

    /**
     * message :  Login Successfully.
     * success : 200
     * user_detail : {"id":"2","userid":"2","is_primary":"1","firstname":"prabh","lastname":"jot","email":"prabhjot@gmail.com","phonenumber":"98989898","title":"name","datecreated":"0000-00-00 00:00:00","password":"$2a$08$8xeiHdR2Y0kfsQ50Qb3Rkuvon9xYzvk0jrlCEmTQx73GdRXEDxpSi","new_pass_key":null,"new_pass_key_requested":null,"email_verified_at":null,"email_verification_key":null,"email_verification_sent_at":null,"last_ip":null,"last_login":null,"last_password_change":"2019-10-22 13:43:24","active":"1","profile_image":"fcc18fcecc9123854b5b519369a8fdf9.jpg","direction":"","invoice_emails":"1","estimate_emails":"1","credit_note_emails":"1","contract_emails":"1","task_emails":"1","project_emails":"1","ticket_emails":"1"}
     */

    private String message;
    private int success;
    private UserDetailBean user_detail;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public UserDetailBean getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetailBean user_detail) {
        this.user_detail = user_detail;
    }

    public static class UserDetailBean {
        /**
         * id : 2
         * userid : 2
         * is_primary : 1
         * firstname : prabh
         * lastname : jot
         * email : prabhjot@gmail.com
         * phonenumber : 98989898
         * title : name
         * datecreated : 0000-00-00 00:00:00
         * password : $2a$08$8xeiHdR2Y0kfsQ50Qb3Rkuvon9xYzvk0jrlCEmTQx73GdRXEDxpSi
         * new_pass_key : null
         * new_pass_key_requested : null
         * email_verified_at : null
         * email_verification_key : null
         * email_verification_sent_at : null
         * last_ip : null
         * last_login : null
         * last_password_change : 2019-10-22 13:43:24
         * active : 1
         * profile_image : fcc18fcecc9123854b5b519369a8fdf9.jpg
         * direction :
         * invoice_emails : 1
         * estimate_emails : 1
         * credit_note_emails : 1
         * contract_emails : 1
         * task_emails : 1
         * project_emails : 1
         * ticket_emails : 1
         */

        private String id;
        private String userid;
        private String is_primary;
        private String firstname;
        private String lastname;
        private String email;
        private String phonenumber;
        private String title;
        private String datecreated;
        private String password;
        private Object new_pass_key;
        private Object new_pass_key_requested;
        private Object email_verified_at;
        private Object email_verification_key;
        private Object email_verification_sent_at;
        private Object last_ip;
        private Object last_login;
        private String last_password_change;
        private String active;
        private String profile_image;
        private String direction;
        private String invoice_emails;
        private String estimate_emails;
        private String credit_note_emails;
        private String contract_emails;
        private String task_emails;
        private String project_emails;
        private String ticket_emails;

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

        public String getIs_primary() {
            return is_primary;
        }

        public void setIs_primary(String is_primary) {
            this.is_primary = is_primary;
        }

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDatecreated() {
            return datecreated;
        }

        public void setDatecreated(String datecreated) {
            this.datecreated = datecreated;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getNew_pass_key() {
            return new_pass_key;
        }

        public void setNew_pass_key(Object new_pass_key) {
            this.new_pass_key = new_pass_key;
        }

        public Object getNew_pass_key_requested() {
            return new_pass_key_requested;
        }

        public void setNew_pass_key_requested(Object new_pass_key_requested) {
            this.new_pass_key_requested = new_pass_key_requested;
        }

        public Object getEmail_verified_at() {
            return email_verified_at;
        }

        public void setEmail_verified_at(Object email_verified_at) {
            this.email_verified_at = email_verified_at;
        }

        public Object getEmail_verification_key() {
            return email_verification_key;
        }

        public void setEmail_verification_key(Object email_verification_key) {
            this.email_verification_key = email_verification_key;
        }

        public Object getEmail_verification_sent_at() {
            return email_verification_sent_at;
        }

        public void setEmail_verification_sent_at(Object email_verification_sent_at) {
            this.email_verification_sent_at = email_verification_sent_at;
        }

        public Object getLast_ip() {
            return last_ip;
        }

        public void setLast_ip(Object last_ip) {
            this.last_ip = last_ip;
        }

        public Object getLast_login() {
            return last_login;
        }

        public void setLast_login(Object last_login) {
            this.last_login = last_login;
        }

        public String getLast_password_change() {
            return last_password_change;
        }

        public void setLast_password_change(String last_password_change) {
            this.last_password_change = last_password_change;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getInvoice_emails() {
            return invoice_emails;
        }

        public void setInvoice_emails(String invoice_emails) {
            this.invoice_emails = invoice_emails;
        }

        public String getEstimate_emails() {
            return estimate_emails;
        }

        public void setEstimate_emails(String estimate_emails) {
            this.estimate_emails = estimate_emails;
        }

        public String getCredit_note_emails() {
            return credit_note_emails;
        }

        public void setCredit_note_emails(String credit_note_emails) {
            this.credit_note_emails = credit_note_emails;
        }

        public String getContract_emails() {
            return contract_emails;
        }

        public void setContract_emails(String contract_emails) {
            this.contract_emails = contract_emails;
        }

        public String getTask_emails() {
            return task_emails;
        }

        public void setTask_emails(String task_emails) {
            this.task_emails = task_emails;
        }

        public String getProject_emails() {
            return project_emails;
        }

        public void setProject_emails(String project_emails) {
            this.project_emails = project_emails;
        }

        public String getTicket_emails() {
            return ticket_emails;
        }

        public void setTicket_emails(String ticket_emails) {
            this.ticket_emails = ticket_emails;
        }
    }
}
