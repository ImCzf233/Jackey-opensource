package com.thealtening.utilities;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: Jackey Client b2.jar:com/thealtening/utilities/SSLVerification.class */
public class SSLVerification {
    private boolean verified = false;

    public void verify() {
        if (!this.verified) {
            bypassSSL();
            whitelistTheAltening();
            this.verified = true;
        }
    }

    private void bypassSSL() {
        TrustManager[] trustAllCerts = {new X509TrustManager() { // from class: com.thealtening.utilities.SSLVerification.1
            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }

    private void whitelistTheAltening() {
        HttpsURLConnection.setDefaultHostnameVerifier(hostname, sslSession -> {
            return hostname.equals("authserver.thealtening.com") || hostname.equals("sessionserver.thealtening.com");
        });
    }
}
