package explorewithme.ewm.compilation.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewCompilationDto {

    List<Integer> events;
    boolean pinned;
    String title;

}
