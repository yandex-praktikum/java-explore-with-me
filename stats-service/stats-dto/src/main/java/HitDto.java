package dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HitDto {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}