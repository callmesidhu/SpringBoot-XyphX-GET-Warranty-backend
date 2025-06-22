package com.xyphx.getwarranty.dto;

public class SignupRequest {
        private String name;
        private String email;
        private String password;

        // ✅ Constructors
        public SignupRequest() {
        }

        public SignupRequest(String name, String email, String password) {
                this.name = name;
                this.email = email;
                this.password = password;
        }

        // ✅ Getters
        public String getName() {
                return name;
        }

        public String getEmail() {
                return email;
        }

        public String getPassword() {
                return password;
        }

        // ✅ Setters (optional but useful if you're binding JSON)
        public void setName(String name) {
                this.name = name;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}
