package ru.explorewithme.users.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipationRequestDto {
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private String status;

    @Override
    public String toString() {
        return "ParticipationRequestDto{" +
                "created='" + created + '\'' +
                ", event=" + event +
                ", id=" + id +
                ", requester=" + requester +
                ", status='" + status + '\'' +
                '}';
    }
}
