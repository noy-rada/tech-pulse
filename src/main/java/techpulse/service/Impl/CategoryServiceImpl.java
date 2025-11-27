package techpulse.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import techpulse.domain.Category;
import techpulse.dto.CategoryDTO;
import techpulse.mapper.CategoryMapper;
import techpulse.repository.CategoryRepository;
import techpulse.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("Category name already exists");
        }

        Category category = categoryMapper.categoryToCategoryEntity(categoryDTO);
        Category saved = categoryRepository.save(category);

        return categoryMapper.categoryToCategoryDTO(saved);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return categoryMapper.categoryToCategoryDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        Category updated = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(updated);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
