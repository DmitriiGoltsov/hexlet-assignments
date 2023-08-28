package exercise.controller;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    private final WeatherService weatherService;

    // BEGIN
    @GetMapping(path = "/cities/{id}")
    public Map<String, String> getCityInformation(@PathVariable Long id) {
        return weatherService.getWeather(id);
    }

    @GetMapping(path = "/search")
    public List<Map<String, String>> getCityListOrdered(@RequestParam(value = "name", required = false) String name) {

        List<City> result;

        if (name == null) {
            result = cityRepository.findAllByOrderByName();
        } else {
            result = cityRepository.findByNameIgnoreCaseStartingWith(name);
        }

        return result.stream()
                .map(city -> {
                    Map<String, String> cityWeather = weatherService.getWeather(city.getId());
                    return Map.of("name", city.getName(), "temperature", cityWeather.get("temperature"));
                })
                .toList();
    }
    // END
}

