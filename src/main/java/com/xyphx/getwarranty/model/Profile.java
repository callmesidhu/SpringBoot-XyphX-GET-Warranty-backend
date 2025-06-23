package com.xyphx.getwarranty.model;

public class Profile {
        private String phone;
        private String location;
        private String jobTitle;
        private String company;
        private String website;
        private String imageUrl;

        // Constructors
        public Profile() {
        }

        // Getters and Setters
        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getLocation() {
                return location;
        }

        public void setLocation(String location) {
                this.location = location;
        }

        public String getJobTitle() {
                return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
                this.jobTitle = jobTitle;
        }

        public String getCompany() {
                return company;
        }

        public void setCompany(String company) {
                this.company = company;
        }

        public String getWebsite() {
                return website;
        }

        public void setWebsite(String website) {
                this.website = website;
        }

        public String getImageUrl() {
                return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
        }
}
