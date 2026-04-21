package org.whatsapp.springboot.utilities.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "whatsapp")
public class WhatsAppProperties {
    private String baseUrl;
    private String apiVersion;
    private String token;
    private String phoneNumberId;
    private String helloWorldTemplate;
    private String languageCode;

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getPhoneNumberId() { return phoneNumberId; }
    public void setPhoneNumberId(String phoneNumberId) { this.phoneNumberId = phoneNumberId; }

    public String getHelloWorldTemplate() { return helloWorldTemplate; }
    public void setHelloWorldTemplate(String helloWorldTemplate) { this.helloWorldTemplate = helloWorldTemplate; }

    public String getLanguageCode() { return languageCode; }
    public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }
}