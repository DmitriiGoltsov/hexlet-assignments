package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCorses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    // BEGIN
    @GetMapping(path = "/{id}/previous")
    public List<Course> getPreviousCourses(@PathVariable long id) {
        Course course = courseRepository.findById(id);

        String coursePath = course.getPath();

        List<Long> ids;

        if (!coursePath.contains(".")) {
            ids = List.of(Long.parseLong(coursePath));
        } else {
            ids = Arrays.stream(coursePath.split("."))
                    .map(Long::parseLong)
                    .toList();
        }

        return (List<Course>) courseRepository.findAllById(ids);
    }
    // END

}
