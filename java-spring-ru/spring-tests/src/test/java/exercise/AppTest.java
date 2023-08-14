package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.dto.PersonDto;
import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;

import java.net.http.HttpResponse;

@SpringBootTest
// При тестировании можно вообще не запускать сервер
// Spring будет обрабатывать HTTP запрос и направлять его в контроллер
// Код вызывается точно так же, как если бы он обрабатывал настоящий запрос
// Такие тесты обходятся дешевле в плане ресурсов
// Для этого нужно внедрить MockMvc

// BEGIN
@AutoConfigureMockMvc
// END

// Чтобы исключить влияние тестов друг на друга,
// каждый тест будет выполняться в транзакции.
// После завершения теста транзакция автоматически откатывается
@Transactional
// Для наполнения БД данными перед началом тестирования
// воспользуемся возможностями библиотеки Database Rider
@DBRider
// Файл с данными для тестов (фикстуры)
@DataSet("people.yml")
public class AppTest {

    // Автоматическое создание экземпляра класса MockMvc
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PersonRepository repository;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void testRootPage() throws Exception {
        // Выполняем запрос и получаем ответ
        MockHttpServletResponse response = mockMvc
            // Выполняем GET запрос по указанному адресу
            .perform(get("/"))
            // Получаем результат MvcResult
            .andReturn()
            // Получаем ответ MockHttpServletResponse из класса MvcResult
            .getResponse();

        // Проверяем статус ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что ответ содержит определенный текст
        assertThat(response.getContentAsString()).contains("Welcome to Spring");
    }

    @Test
    void testCreatePerson() throws Exception {
        PersonDto dto = new PersonDto();
        dto.setFirstName("Jackson");
        dto.setLastName("Bind");
        dto.setEmail("a@a.com");
        mockMvc.perform(
                // Выполняем POST-запрос
                post("/people")
                    // Устанавливаем тип содержимого тела запроса
                    .contentType(MediaType.APPLICATION_JSON)
                    // Добавляем содержимое тела
                    .content(mapper.writeValueAsString(dto))
        )
                // Убеждаемся, что запрос выполнился успешно
                .andExpect(status().isOk());

        // Проверяем, что сущность добавилась в базу
        assertNotNull(repository.findByEmail("a@a.com"));
    }

    @Test
    void testGetPerson() throws Exception {
        var existingUserEmail = "jack@mail.com";
        // Используем утилиту для получения идентификатора пользователя по его email
        // Так как мы не знаем заранее, с каким идентификатором сущность создастся в базе данных
        var existingUserId = TestUtils.getUserIdByEmail(mockMvc, existingUserEmail);
        MockHttpServletResponse response = mockMvc
            .perform(get("/people/{id}", existingUserId))
            .andReturn()
            .getResponse();

        // Проверяем статус ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что тип значения в ответе - json
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        // Проверяем, что тело ответа содержит пользователя
        assertThat(response.getContentAsString()).contains(existingUserEmail);
    }

    // BEGIN
    @Test
    void testGetListOfPersons() throws Exception {

        MockHttpServletResponse response = mockMvc
                .perform(get("/people"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
        assertThat(response.getContentType()).isEqualTo("application/json");
        assertThat(response.getContentAsString()).contains("John", "Smith");
        assertThat(response.getContentAsString()).contains("Jack", "Doe");
        assertThat(response.getContentAsString()).contains("Jassica", "Simpson");
        assertThat(response.getContentAsString()).contains("Richard", "Lionheart");
    }

    @Test
    void testPatchPersonById() throws Exception {

        String existingPersonEmail = "saladinisafriend@templemail.uk";
        int existingPersonId = TestUtils.getUserIdByEmail(mockMvc, existingPersonEmail);
        String newLastName = "Plantagenet";

        PersonDto personDto = new PersonDto();
        personDto.setLastName(newLastName);

        MockHttpServletResponse response = mockMvc
                .perform(
                        patch("/people/{id}", existingPersonId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(personDto)))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);

        var newResponse = mockMvc
                .perform(get("/people/{id}", existingPersonId))
                .andReturn()
                .getResponse();

        assertThat(newResponse.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);
        assertThat(newResponse.getContentType()).isEqualTo("application/json");
        assertThat(newResponse.getContentAsString()).contains(newLastName);
    }

    @Test
    void testDeletePersonById() throws Exception {

        String existingPersonEmail = "jassika@yahoo.com";
        int existingPeronId = TestUtils.getUserIdByEmail(mockMvc, existingPersonEmail);

        var response = mockMvc
                .perform(delete("/people/{id}", existingPeronId))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(MockHttpServletResponse.SC_OK);

        var responseOnNonExistingPerson = mockMvc
                .perform(get("/people/{id}", existingPeronId))
                .andReturn()
                .getResponse();

        assertThat(responseOnNonExistingPerson.getContentType()).isNull();
    }
    // END
}
