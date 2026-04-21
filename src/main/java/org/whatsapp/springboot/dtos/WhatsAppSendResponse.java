package org.whatsapp.springboot.dtos;

import java.util.List;

public class WhatsAppSendResponse {
    private String messaging_product;
    private List<ContactDto> contacts;
    private List<MessageDto> messages;

    public String getMessaging_product() { return messaging_product; }
    public void setMessaging_product(String messaging_product) { this.messaging_product = messaging_product; }

    public List<ContactDto> getContacts() { return contacts; }
    public void setContacts(List<ContactDto> contacts) { this.contacts = contacts; }

    public List<MessageDto> getMessages() { return messages; }
    public void setMessages(List<MessageDto> messages) { this.messages = messages; }

    public static class ContactDto {
        private String input;
        private String wa_id;

        public String getInput() { return input; }
        public void setInput(String input) { this.input = input; }

        public String getWa_id() { return wa_id; }
        public void setWa_id(String wa_id) { this.wa_id = wa_id; }
    }

    public static class MessageDto {
        private String id;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
    }
}