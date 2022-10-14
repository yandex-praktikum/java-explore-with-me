package ru.practikum.explorewithme.p_admin.compilation;

import ru.practikum.explorewithme.dto.CompilationDto;
import ru.practikum.explorewithme.dto.in.NewCompilationDto;

public interface AdminCompilationService {

    CompilationDto createCompilation(NewCompilationDto dto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    void unpinToMainPage(long compId);

    void pinToMainPage(long compId);
}
