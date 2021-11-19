package com.xbidi.spring.content.example.infrastructure.controller;

import com.google.gson.Gson;
import com.xbidi.spring.content.example.domain.Example;
import com.xbidi.spring.content.example.domain.ExampleMapper;
import com.xbidi.spring.content.example.infrastructure.controller.dto.input.ExampleInputDTO;
import com.xbidi.spring.content.example.infrastructure.controller.dto.output.ExampleOutputDTO;
import com.xbidi.spring.content.example.infrastructure.repository.ExampleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExampleControllerMockMvcTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ExampleRepository exampleRepository;

  @BeforeEach
  void setUp() {
    exampleRepository.deleteAll();
  }

  @Test
  void findById_ok() throws Exception {
    // inserta los datos iniciales en la base de datos
    Example example = new Example();
    Example savedExample = exampleRepository.save(example);

    // respuesta esperada
    Example mockExample = new Example();
    mockExample.setId(savedExample.getId());
    ExampleOutputDTO expectedResponse = ExampleMapper.INSTANCE.toExampleOutputDTO(mockExample);
    String expectedJsonResponse = new Gson().toJson(expectedResponse);

    // test
    this.mockMvc
        .perform(get("/api/v0/examples/{id}", savedExample.getId()))
        .andExpect(status().isOk())
        .andExpect(
            mvcResult -> mvcResult.getResponse().getContentAsString().equals(expectedJsonResponse));
  }

  @Test
  void findById_ko() throws Exception {
    this.mockMvc
        .perform(get("/api/v0/examples/{id}", "0"))
        .andExpect(status().isOk())
        .andDo(
            mvcResult -> Assertions.assertEquals("", mvcResult.getResponse().getContentAsString()));
  }

  @Test
  void create_ok() throws Exception {

    // test
    Gson gson = new Gson();
    ExampleInputDTO exampleInputDTO = new ExampleInputDTO();
    String body = gson.toJson(exampleInputDTO);
    this.mockMvc
        .perform(post("/api/v0/examples").content(body).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(
            mvcResult -> {
              String responseBody = mvcResult.getResponse().getContentAsString();
              ExampleOutputDTO exampleOutputDTO =
                  gson.fromJson(responseBody, ExampleOutputDTO.class);
              // TODO test
            });
  }

  @Test
  void update_ok() throws Exception {

    // inserta los datos iniciales en la base de datos
    Example example = new Example();
    Example savedExample = exampleRepository.save(example);

    // test
    Gson gson = new Gson();
    ExampleInputDTO exampleInputDTO = new ExampleInputDTO();
    String body = gson.toJson(exampleInputDTO);
    this.mockMvc
        .perform(
            put(String.format("/api/v0/examples/%s", savedExample.getId()))
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(
            mvcResult -> {
              String responseBody = mvcResult.getResponse().getContentAsString();
              ExampleOutputDTO exampleOutputDTO =
                  gson.fromJson(responseBody, ExampleOutputDTO.class);
              Assertions.assertEquals(exampleOutputDTO.getId(), savedExample.getId());
              // TODO test
            });
  }

  @Test
  void delete_ok() throws Exception {

    // inserta los datos iniciales en la base de datos
    Example example = new Example();
    Example savedExample = exampleRepository.save(example);

    // test
    Gson gson = new Gson();
    this.mockMvc
        .perform(
            delete(String.format("/api/v0/examples/%s", savedExample.getId()))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(
            mvcResult -> {
              String responseBody = mvcResult.getResponse().getContentAsString();
              ExampleOutputDTO exampleOutputDTO =
                  gson.fromJson(responseBody, ExampleOutputDTO.class);
              Assertions.assertEquals(exampleOutputDTO.getId(), savedExample.getId());
              // TODO test
            });
  }
}
