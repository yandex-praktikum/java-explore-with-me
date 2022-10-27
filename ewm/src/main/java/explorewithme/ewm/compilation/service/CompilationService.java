package explorewithme.ewm.compilation;

import explorewithme.ewm.compilation.dto.CompilationDto;
import explorewithme.ewm.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getComilations(int from, int size, boolean pinned);

    CompilationDto getComilationById(long compId);

    CompilationDto create(NewCompilationDto newCompilationDto);

    void delete(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    void unpinCompilation(long compId);

    void pinCompilation(long compId);
}
