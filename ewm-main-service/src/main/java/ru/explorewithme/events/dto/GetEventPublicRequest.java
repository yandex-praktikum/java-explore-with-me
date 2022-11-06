package ru.explorewithme.events.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

//@Data
@NoArgsConstructor
@Setter
@Getter
public class GetEventPublicRequest {
    private String text;
    private Set<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    //@Builder.Default
    private Boolean onlyAvailable;
    private String sort;

    public static GetEventPublicRequest of(String text,
                                           Set<Long> categories,
                                           Boolean paid,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Boolean onlyAvailable,
                                           String sort) {
        GetEventPublicRequest request = new GetEventPublicRequest();
        request.setText(text);
        request.setCategories(categories);
        request.setPaid(paid);
        request.setRangeStart(rangeStart);
        request.setRangeEnd(rangeEnd);
        request.setOnlyAvailable(onlyAvailable);
        request.setSort(sort);

        return request;
    }

    /*public boolean hasTags() {
        return tags != null && !tags.isEmpty();
    }

    public enum State {UNREAD, READ, ALL }
    public enum ContentType { ARTICLE, VIDEO, IMAGE, ALL }
    public enum Sort { NEWEST, OLDEST, TITLE, SITE }*/
}
