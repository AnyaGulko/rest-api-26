package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ColorsListResponseModel {
    int page;
    @JsonProperty("per_page")
    int perPage;
    int total;
    @JsonProperty("totalPages")
    int totalPages;
    List<ColorInfo> data;
    SupportInfo support;

    @Data
    public static class ColorInfo {
        int id;
        String name;
        int year;
        String color;
        @JsonProperty("pantoneValue")
        String pantoneValue;
    }

    @Data
    public static class SupportInfo {
        String url;
        String text;
    }
}
