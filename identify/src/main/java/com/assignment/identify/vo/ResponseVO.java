package com.assignment.identify.vo;

import lombok.Data;

@Data
public class ResponseVO {
    private Contact contact;

    @Data
    public static class Contact {
        private Long primaryContactId;
        private String[] emails;
        private String[] phoneNumbers;
        private Long[] secondaryContactIds;
    }
}
