package com.sparta.moim.user.constants;

public class JwtConstants {

  public static class Expiry {
    public static final int ACCESS_TOKEN = 60 * 30;
    public static final int REFRESH_TOKEN = 60 * 60 * 24 * 7;
  }

  public static class CookieName {
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
  }

  public static class Claim {
    public static final String USERNAME = "username";
  }
}
