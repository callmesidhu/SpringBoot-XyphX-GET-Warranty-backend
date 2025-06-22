package com.xyphx.getwarranty.dto;

public class AuthResponse {
        private String accessToken;
        private String refreshToken;

        public AuthResponse(String accessToken, String refreshToken) {
                this.accessToken = accessToken;
                this.refreshToken = refreshToken;
        }

        // ← Add these
        public String getAccessToken() {
                return accessToken;
        }

        public String getRefreshToken() {
                return refreshToken;
        }
}
