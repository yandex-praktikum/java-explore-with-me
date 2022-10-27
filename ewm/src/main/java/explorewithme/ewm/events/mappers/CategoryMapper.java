package explorewithme.ewm.events.mappers;

import explorewithme.ewm.events.dto.CategoryDto;
import explorewithme.ewm.events.dto.NewCategoryDto;
import explorewithme.ewm.events.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CategoryMapper {

    public static Category fromNewCategoryDto(NewCategoryDto categegoryDto){
        log.debug("Mapping from dto to category");
        Category category = new Category(categegoryDto.getName());
        return category;
    }

    public static CategoryDto fromCategory(Category category){
        log.debug("Mapping from category to dto");
        CategoryDto categoryDto = new CategoryDto(
                category.getId(),
                category.getName()
        );
        return categoryDto;
    }

}
