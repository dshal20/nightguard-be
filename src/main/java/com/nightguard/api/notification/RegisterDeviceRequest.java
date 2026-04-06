package com.nightguard.api.notification;

public class RegisterDeviceRequest {

  private String fcmToken;

  public String getFcmToken() { return fcmToken; }
  public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }
}
