package exercise.controller;
import exercise.model.User;
import exercise.repository.UserRepository;
import exercise.service.SearchCriteria;
import exercise.service.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserRepository userRepository;

    // BEGIN
    @GetMapping(path = "")
    public Iterable<User> getUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        List<Specification> specifications = new ArrayList<>();
        if (firstName != null) {
            specifications.add(new UserSpecification(new SearchCriteria<String>("firstName", firstName)));
        }

        if (lastName != null) {
            specifications.add(new UserSpecification(new SearchCriteria<String>("lastName", lastName)));
        }

        Specification<User> result = specifications.stream()
                .reduce(null, (specificationResult, specification) -> {
                    if (specificationResult == null) {
                        return specification;
                    }
                    return  specificationResult.and(specification);
                });

        if (!specifications.isEmpty()) {
            return userRepository.findAll(result);
        }

        return userRepository.findAll();
    }

    //    Альтернативное решение с использованием Querydsl (дополнительная задача)
//    if (firstName == null && lastName == null) {
//        return userRepository.findAll();
//    }
//
//        if (firstName == null) {
//        return userRepository.findAll(
//                QUser.user.lastName.containsIgnoreCase(lastName)
//        );
//    }
//
//        if (lastName == null) {
//        return userRepository.findAll(
//                QUser.user.firstName.containsIgnoreCase(firstName)
//        );
//    }
//
//        return userRepository.findAll(
//                QUser.user.firstName
//                        .containsIgnoreCase(firstName)
//            .and(
//            QUser.user.lastName
//                    .containsIgnoreCase(lastName)
//                )
//                        );




//     Альтернативное решение с использованием Querydsl web support (дополнительная задача)
//
//     @GetMapping(path = "")
//     public Iterable<User> getUsers(@QuerydslPredicate(root = User.class) Predicate predicate) {
//         return userRepository.findAll(predicate);
//     }

}
