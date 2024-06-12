package models;

import lombok.Data;

import java.util.Date;

@Data
public class UserCreationResponseModel {
    String name;
    String job;
    String id;
    Date createdAt;
}
