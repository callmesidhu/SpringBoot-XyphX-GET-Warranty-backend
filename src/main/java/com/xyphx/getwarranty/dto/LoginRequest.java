package com.xyphx.getwarranty.dto;

public class LoginRequest {
        private String email;
        private String password;

        // No-arg constructor for Jackson
        public LoginRequest() {
        }

        // All-args constructor (optional)
        public LoginRequest(String email, String password) {
                this.email = email;
                this.password = password;
        }

        // Real getter implementations
        public String getEmail() {
                return email;
        }

        public String getPassword() {
                return password;
        }

        // Setters so Jackson can bind incoming JSON
        public void setEmail(String email) {
                this.email = email;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}
