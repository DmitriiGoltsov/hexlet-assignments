package exercise.controller;

import exercise.dto.ArticleDto;
import exercise.model.Article;
import exercise.model.Category;
import exercise.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticleRepository articleRepository;

    @GetMapping(path = "")
    public Iterable<Article> getArticles() {
        return articleRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticle(@PathVariable long id) {
        articleRepository.deleteById(id);
    }

    // BEGIN
    @PostMapping(path = "")
    public void createArticle(@RequestBody Article article) {
        this.articleRepository.save(article);
    }

    @PatchMapping(path = "/{id}")
    public void updateArticle(@PathVariable("id") Long id, @RequestBody Article updatedArticle) {
        Article oldArticle = this.articleRepository.findById(id).get();

        String newName = updatedArticle.getName();
        String newBody = updatedArticle.getBody();
        Category newCategory = updatedArticle.getCategory();

        if (newName != null && !newName.isEmpty()) {
            oldArticle.setName(newName);
        }

        if (newBody != null && !newBody.isEmpty()) {
            oldArticle.setBody(newBody);
        }

        if (newCategory != null) {
            oldArticle.setCategory(newCategory);
        }

        this.articleRepository.save(oldArticle);
    }

    @GetMapping(path = "/{id}")
    public Article showArticleById(@PathVariable Long id) {
        return this.articleRepository.findById(id).orElseThrow();
    }
    // END
}
