package ru.practikum.explorewithme.p_admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practikum.explorewithme.client.BaseClient;
import ru.practikum.explorewithme.dto.in.NewCompilationDto;

@Service
public class AdminClientCompilations extends BaseClient {

    @Autowired
    public AdminClientCompilations(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(serverUrl, builder, "/");
    }

    public ResponseEntity<Object> createCompilation(NewCompilationDto dto) {
        return post("admin/compilations", dto);
    }

    public ResponseEntity<Object> deleteCompilation(long compId) {
        return delete("admin/compilations/" + compId);
    }

    public ResponseEntity<Object> deleteEventFromCompilation(long compId, long eventId) {
        return delete("admin/compilations/" + compId + "/events/" + eventId);
    }

    public ResponseEntity<Object> addEventToCompilation(long compId, long eventId) {
        return patch("admin/compilations/" + compId + "/events/" + eventId);
    }

    public ResponseEntity<Object> unpinToMainPage(long compId) {
        return delete("admin/compilations/" + compId + "/pin");
    }

    public ResponseEntity<Object> pinToMainPage(long compId) {
        return patch("admin/compilations/" + compId + "/pin");
    }
}
