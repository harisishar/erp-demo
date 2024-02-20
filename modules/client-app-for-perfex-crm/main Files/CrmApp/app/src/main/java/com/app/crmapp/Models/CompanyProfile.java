package com.app.crmapp.Models;

import java.util.List;

public class CompanyProfile {
    /**
     * data : [{"id":"3","userid":"2","company":"LBM Solutions","vat":"2123","phonenumber":"9888898888","country":"InDia","city":"Nurpur","zip":"176022","state":"Himachal Pradesh","gstin_number":"0","address":"fghgfh","website":"lbmsolution.in","datecreated":"2019-10-23 00:00:00","active":"1","leadid":"4","billing_street":"fdgfg","billing_city":"gdfg","billing_state":"dfgdfg","billing_zip":"4454","billing_country":"0","shipping_street":"rt","shipping_city":"retet","shipping_state":"rtret","shipping_zip":"retret","shipping_country":"0","longitude":"ertrt","latitude":"trt","default_language":"retrt","default_currency":"0","show_primary_contact":"0","stripe_id":"4","registration_confirmed":"1","addedfrom":"0"}]
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
         * id : 3
         * userid : 2
         * company : LBM Solutions
         * vat : 2123
         * phonenumber : 9888898888
         * country : InDia
         * city : Nurpur
         * zip : 176022
         * state : Himachal Pradesh
         * gstin_number : 0
         * address : fghgfh
         * website : lbmsolution.in
         * datecreated : 2019-10-23 00:00:00
         * active : 1
         * leadid : 4
         * billing_street : fdgfg
         * billing_city : gdfg
         * billing_state : dfgdfg
         * billing_zip : 4454
         * billing_country : 0
         * shipping_street : rt
         * shipping_city : retet
         * shipping_state : rtret
         * shipping_zip : retret
         * shipping_country : 0
         * longitude : ertrt
         * latitude : trt
         * default_language : retrt
         * default_currency : 0
         * show_primary_contact : 0
         * stripe_id : 4
         * registration_confirmed : 1
         * addedfrom : 0
         */

        private String id;
        private String userid;
        private String company;
        private String vat;
        private String phonenumber;
        private String country;
        private String city;
        private String zip;
        private String state;
        private String gstin_number;
        private String address;
        private String website;
        private String datecreated;
        private String active;
        private String leadid;
        private String billing_street;
        private String billing_city;
        private String billing_state;
        private String billing_zip;
        private String billing_country;
        private String shipping_street;
        private String shipping_city;
        private String shipping_state;
        private String shipping_zip;
        private String shipping_country;
        private String longitude;
        private String latitude;
        private String default_language;
        private String default_currency;
        private String show_primary_contact;
        private String stripe_id;
        private String registration_confirmed;
        private String addedfrom;

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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getVat() {
            return vat;
        }

        public void setVat(String vat) {
            this.vat = vat;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getGstin_number() {
            return gstin_number;
        }

        public void setGstin_number(String gstin_number) {
            this.gstin_number = gstin_number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getDatecreated() {
            return datecreated;
        }

        public void setDatecreated(String datecreated) {
            this.datecreated = datecreated;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getLeadid() {
            return leadid;
        }

        public void setLeadid(String leadid) {
            this.leadid = leadid;
        }

        public String getBilling_street() {
            return billing_street;
        }

        public void setBilling_street(String billing_street) {
            this.billing_street = billing_street;
        }

        public String getBilling_city() {
            return billing_city;
        }

        public void setBilling_city(String billing_city) {
            this.billing_city = billing_city;
        }

        public String getBilling_state() {
            return billing_state;
        }

        public void setBilling_state(String billing_state) {
            this.billing_state = billing_state;
        }

        public String getBilling_zip() {
            return billing_zip;
        }

        public void setBilling_zip(String billing_zip) {
            this.billing_zip = billing_zip;
        }

        public String getBilling_country() {
            return billing_country;
        }

        public void setBilling_country(String billing_country) {
            this.billing_country = billing_country;
        }

        public String getShipping_street() {
            return shipping_street;
        }

        public void setShipping_street(String shipping_street) {
            this.shipping_street = shipping_street;
        }

        public String getShipping_city() {
            return shipping_city;
        }

        public void setShipping_city(String shipping_city) {
            this.shipping_city = shipping_city;
        }

        public String getShipping_state() {
            return shipping_state;
        }

        public void setShipping_state(String shipping_state) {
            this.shipping_state = shipping_state;
        }

        public String getShipping_zip() {
            return shipping_zip;
        }

        public void setShipping_zip(String shipping_zip) {
            this.shipping_zip = shipping_zip;
        }

        public String getShipping_country() {
            return shipping_country;
        }

        public void setShipping_country(String shipping_country) {
            this.shipping_country = shipping_country;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getDefault_language() {
            return default_language;
        }

        public void setDefault_language(String default_language) {
            this.default_language = default_language;
        }

        public String getDefault_currency() {
            return default_currency;
        }

        public void setDefault_currency(String default_currency) {
            this.default_currency = default_currency;
        }

        public String getShow_primary_contact() {
            return show_primary_contact;
        }

        public void setShow_primary_contact(String show_primary_contact) {
            this.show_primary_contact = show_primary_contact;
        }

        public String getStripe_id() {
            return stripe_id;
        }

        public void setStripe_id(String stripe_id) {
            this.stripe_id = stripe_id;
        }

        public String getRegistration_confirmed() {
            return registration_confirmed;
        }

        public void setRegistration_confirmed(String registration_confirmed) {
            this.registration_confirmed = registration_confirmed;
        }

        public String getAddedfrom() {
            return addedfrom;
        }

        public void setAddedfrom(String addedfrom) {
            this.addedfrom = addedfrom;
        }
    }
}
