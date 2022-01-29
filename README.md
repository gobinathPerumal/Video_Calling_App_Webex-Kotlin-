VIDEO CALLING APP INTEGRATION - WEBEX(Kotlin)


App Screenshots:

![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/splashscreen.PNG)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/login_screen1.PNG)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/login_screen2.PNG)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/webex_login.PNG)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/webex_password.PNG)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/calling1_LI.jpg)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/call_waiting_LI.jpg)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/oncall1_LI.jpg)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/participants_LI.jpg)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/screenshare_LI.jpg)
![alt text](https://github.com/gobinathPerumal/Video_Calling_App_Webex-Kotlin-/blob/main_develop/app/sreenshots/call_not_connect_LI.jpg)

WEBEX SDK:
Add dependency in build.gradle(app):
implementation 'com.ciscowebex:androidsdk:3.2.1@aar'

Add below line in build.gradle file
        maven {
            url 'https://devhub.cisco.com/artifactory/webexsdk/'
        }

Add Below Permissions and Features in Manifest File

Features:
    <uses-feature
        android:name="android.hardware.camera"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.camera.autofocus"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.camera.flash"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.camera.front"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.usb.accessory"
        android:required="false" />

Permissions:
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <!--
    Used by PSTN calling to dial directly rather than open the dialer
    This has proven to be the only reliable way to ensure Samsung phones are able to
    dial into meetings over PSTN with the dial sequence.
    -->
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.READ_CALENDAR" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.WRITE_CONTACTS" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
    <uses-permission android:name="android.permission.MANAGE_ACCOUNTS" />
    <uses-permission android:name="android.permission.AUTHENTICATE_ACCOUNTS" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.USE_CREDENTIALS" />
    <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />

Description:
    In this project able to call or connect call through webex sdk as a guest user or webex login user,
    If you login as webex user, you need webex login credentials, If you connect call as guest user you need
    JWT token for connect call.

   Initially need to set the below mentioned fields in OAuthWebexModule file
             clientId
             clientSecret
             additionalScopes
             redirectUri

    And need meeting SIP address and meeting password to enter the meeting, so need to set these two fieds in HomeActivity.

    After set the above mentioned fields, now you ready to connect call.

Note: This Project works Above SDK 24