package exercise;

import exercise.daytimes.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {

    @Autowired
    Daytime currentDayTime;

    @Autowired
    Meal currentMeal;

    @GetMapping(path = "/daytime")
    public String sayBonneAppetite() {
        String time = currentDayTime.getName();
        String meal = currentMeal.getMealForDaytime(time);
        return "It is " + time + " now. Enjoy your " + meal;
    }
}
// END
