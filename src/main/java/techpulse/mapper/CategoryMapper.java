package techpulse.mapper;

import org.springframework.stereotype.Component;
import techpulse.domain.Category;
import techpulse.dto.CategoryDTO;

@Component
public class CategoryMapper {

    public CategoryDTO categoryToCategoryDTO(Category category) {
        if (category == null) return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category categoryToCategoryEntity(CategoryDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }
}
