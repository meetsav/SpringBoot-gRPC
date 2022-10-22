package com.meetsav.protobuf;

import com.meetsav.model.Credentials;
import com.meetsav.model.EmailCredentials;
import com.meetsav.model.PhoneCredentials;

public class OneofDemo {
    public static void main(String[] args) {

        EmailCredentials emailcred = EmailCredentials.newBuilder().setEmail("meetsavaliya998@gmail.com")
                .setPassword("admin123").build();

        PhoneCredentials phone = PhoneCredentials.newBuilder().setNumber(1231231234).setOTP(123321).build();

        Credentials credentials = Credentials.newBuilder().setEmailcredentials(emailcred).setPhonecredentials(phone).build();
        // only one method can exist, in above case phone credentials will overwrite email credentials

        login(credentials);
    }
    private static void login(Credentials credential){

       // System.out.println(credential.hasPhonecredentials()); this is also useful
        switch (credential.getModeCase()){
            case EMAILCREDENTIALS:
                System.out.println(credential.getEmailcredentials());
                break;
            case PHONECREDENTIALS:
                System.out.println(credential.getPhonecredentials());
        }

    }
}
