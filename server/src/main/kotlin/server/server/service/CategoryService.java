package server.server.service;

import org.springframework.http.ResponseEntity;
import server.server.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategorys();
}
