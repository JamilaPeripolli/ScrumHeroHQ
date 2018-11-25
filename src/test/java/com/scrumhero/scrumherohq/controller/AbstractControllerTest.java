package com.scrumhero.scrumherohq.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrumhero.scrumherohq.config.SecurityTestConfig;
import com.scrumhero.scrumherohq.config.TestConfig;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SecurityTestConfig.class, TestConfig.class })
@WithMockUser
public abstract class AbstractControllerTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    protected String parseJson(Class clazz, String resource) throws IOException {
        try (InputStream stream = clazz.getResourceAsStream(resource)) {
            return IOUtils.toString(stream);
        }
    }

    protected String createJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    protected MockHttpServletResponse doRequest(String path, HttpMethod method, String requestBody) throws Exception {
        switch (method) {
            case GET: return doGet(path);
            case POST: return doPost(path, requestBody);
            case PUT: return doPut(path);
            case DELETE: return doDelete(path);
        }

        throw new IllegalStateException("Invalid HTTP method.");
    }

    private MockHttpServletResponse doGet(String path) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(path)
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc.perform(requestBuilder).andReturn().getResponse();
    }

    private MockHttpServletResponse doPost(String path, String requestBody) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        return this.mvc.perform(requestBuilder).andReturn().getResponse();
    }

    private MockHttpServletResponse doPut(String path) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put(path)
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc.perform(requestBuilder).andReturn().getResponse();
    }

    private MockHttpServletResponse doDelete(String path) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = delete(path)
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc.perform(requestBuilder).andReturn().getResponse();
    }
}
