package explorewithme.ewm.compilation.mappers;

import explorewithme.ewm.compilation.model.Compilation;
import explorewithme.ewm.compilation.dto.CompilationDto;
import explorewithme.ewm.compilation.dto.NewCompilationDto;
import org.springframework.stereotype.Component;

@Component
public class CompilationMapper {

    public static Compilation fromNewCompilationDto(NewCompilationDto newCompilationDto){
        Compilation compilation = new Compilation(
                newCompilationDto.getTitle(),
                newCompilationDto.isPinned()
        );
          return compilation;
    }

    public static CompilationDto fromCompilation(Compilation compilation){
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilationDto.getTitle());
        return compilationDto;
    }

}
