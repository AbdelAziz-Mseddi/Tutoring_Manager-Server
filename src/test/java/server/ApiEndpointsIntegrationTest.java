package test.java.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import server.entity.User;
import server.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ApiEndpointsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void allImplementedApiEndpointsWorkEndToEnd() throws Exception {
        User user = new User();
        user.setFirstName("Ada");
        user.setLastName("Lovelace");
        user.setEmail("ada@example.com");
        user.setPasswordHash("hashed-password");
        user = userRepository.save(user);

        JsonNode studentCreated = createStudent(user.getId());
        int studentId = studentCreated.get("id").asInt();

        mockMvc.perform(get("/students")
                        .param("userId", user.getId().toString())
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(studentId));

        mockMvc.perform(get("/students/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.firstName").value("Ada"));

        mockMvc.perform(put("/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "firstName": "Grace",
                                  "lastName": "Hopper",
                                  "phone": "555-0001",
                                  "email": "grace@example.com"
                                }
                                """.formatted(user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.firstName").value("Grace"));

        JsonNode classCreated = createClass(user.getId());
        int classId = classCreated.get("id").asInt();

        mockMvc.perform(get("/tutoring-classes")
                        .param("userId", user.getId().toString())
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(classId));

        mockMvc.perform(get("/tutoring-classes/{id}", classId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(classId))
                .andExpect(jsonPath("$.name").value("Algebra 101"));

        mockMvc.perform(put("/tutoring-classes/{id}", classId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "name": "Algebra 201",
                                  "subject": "Mathematics",
                                  "hourlyRate": 65.00
                                }
                                """.formatted(user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(classId))
                .andExpect(jsonPath("$.name").value("Algebra 201"));

        JsonNode enrollmentCreated = createEnrollment(studentId, classId);
        int enrollmentId = enrollmentCreated.get("id").asInt();

        mockMvc.perform(get("/enrollments")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(enrollmentId));

        mockMvc.perform(get("/enrollments/{id}", enrollmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(enrollmentId))
                .andExpect(jsonPath("$.status").value("active"));

        mockMvc.perform(put("/enrollments/{id}", enrollmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "studentId": %d,
                                  "classId": %d,
                                  "enrolledAt": "2026-05-01",
                                  "status": "paused"
                                }
                                """.formatted(studentId, classId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(enrollmentId))
                .andExpect(jsonPath("$.status").value("paused"));

        mockMvc.perform(patch("/enrollments/{id}/status", enrollmentId)
                        .param("status", "completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(enrollmentId))
                .andExpect(jsonPath("$.status").value("completed"));

        JsonNode paymentCreated = createPayment(user.getId());
        int paymentId = paymentCreated.get("id").asInt();

        mockMvc.perform(get("/payments")
                        .param("userId", user.getId().toString())
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(paymentId));

        mockMvc.perform(get("/payments/{id}", paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentId))
                .andExpect(jsonPath("$.totalAmount").value(120.50));

        mockMvc.perform(put("/payments/{id}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "totalAmount": 150.75,
                                  "notes": "Updated payment"
                                }
                                """.formatted(user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paymentId))
                .andExpect(jsonPath("$.totalAmount").value(150.75));

        JsonNode sessionCreated = createSession(enrollmentId, paymentId);
        int sessionId = sessionCreated.get("id").asInt();

        mockMvc.perform(get("/tutoring-sessions")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(sessionId));

        mockMvc.perform(get("/tutoring-sessions/{id}", sessionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sessionId))
                .andExpect(jsonPath("$.paymentId").value(paymentId));

        mockMvc.perform(put("/tutoring-sessions/{id}", sessionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "enrollmentId": %d,
                                  "paymentId": %d,
                                  "scheduledAt": "2026-05-03T10:00:00",
                                  "durationMin": 90,
                                  "notes": "Updated session"
                                }
                                """.formatted(enrollmentId, paymentId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sessionId))
                .andExpect(jsonPath("$.durationMin").value(90));

        mockMvc.perform(delete("/tutoring-sessions/{id}", sessionId))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/tutoring-sessions/{id}", sessionId))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/payments/{id}", paymentId))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/payments/{id}", paymentId))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/enrollments/{id}", enrollmentId))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/enrollments/{id}", enrollmentId))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/tutoring-classes/{id}", classId))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/tutoring-classes/{id}", classId))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/students/{id}", studentId))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/students/{id}", studentId))
                .andExpect(status().isNotFound());
    }

    private JsonNode createStudent(Integer userId) throws Exception {
        String response = mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "firstName": "Ada",
                                  "lastName": "Lovelace",
                                  "phone": "555-0000",
                                  "email": "ada.student@example.com"
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readTree(response);
    }

    private JsonNode createClass(Integer userId) throws Exception {
        String response = mockMvc.perform(post("/tutoring-classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "name": "Algebra 101",
                                  "subject": "Math",
                                  "hourlyRate": 60.00
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readTree(response);
    }

    private JsonNode createEnrollment(Integer studentId, Integer classId) throws Exception {
        String response = mockMvc.perform(post("/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "studentId": %d,
                                  "classId": %d,
                                  "enrolledAt": "2026-05-01",
                                  "status": "active"
                                }
                                """.formatted(studentId, classId)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readTree(response);
    }

    private JsonNode createPayment(Integer userId) throws Exception {
        String response = mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": %d,
                                  "totalAmount": 120.50,
                                  "notes": "Initial payment"
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readTree(response);
    }

    private JsonNode createSession(Integer enrollmentId, Integer paymentId) throws Exception {
        String response = mockMvc.perform(post("/tutoring-sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "enrollmentId": %d,
                                  "paymentId": %d,
                                  "scheduledAt": "2026-05-02T14:30:00",
                                  "durationMin": 60,
                                  "notes": "First session"
                                }
                                """.formatted(enrollmentId, paymentId)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readTree(response);
    }
}