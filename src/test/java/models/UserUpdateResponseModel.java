package models;

import lombok.Data;

import java.util.Date;

@Data
public class UserUpdateResponseModel {
    String name;
    String job;
    Date updatedAt;
}
