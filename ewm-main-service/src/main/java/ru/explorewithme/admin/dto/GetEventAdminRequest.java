package ru.explorewithme.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

//@Data
@NoArgsConstructor
@Setter
@Getter
public class GetEventAdminRequest {
    private Set<Long> users;
    private Set<String> states;
    private Set<Long> Categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;

    public static GetEventAdminRequest of(Set<Long> users,
                                          Set<String> states,
                                          Set<Long> Categories,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd) {
        GetEventAdminRequest request = new GetEventAdminRequest();
        request.setUsers(users);
        request.setStates(states);
        request.setCategories(Categories);
        request.setRangeStart(rangeStart);
        request.setRangeEnd(rangeEnd);

        return request;
    }

    /*public boolean hasTags() {
        return tags != null && !tags.isEmpty();
    }

    public enum State {UNREAD, READ, ALL }
    public enum ContentType { ARTICLE, VIDEO, IMAGE, ALL }
    public enum Sort { NEWEST, OLDEST, TITLE, SITE }*/
}
