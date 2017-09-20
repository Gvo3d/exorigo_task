package org.yakimovdenis.exorigo_task.model;

import lombok.Data;

@Data
public class TelephoneEntity {
    public static final String TABLE_NAME = "phones";
    public static final String TABLE_NAME_FOR_USER_RELATION = "phones_users";

    private int id;
    private String phoneNumber;
}
