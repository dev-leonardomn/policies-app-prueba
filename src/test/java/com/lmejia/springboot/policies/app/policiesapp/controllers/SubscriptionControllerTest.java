package com.lmejia.springboot.policies.app.policiesapp.controllers;

import com.lmejia.springboot.policies.app.policiesapp.mapper.SubscriptionMapper;
import com.lmejia.springboot.policies.app.policiesapp.services.ISubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubscriptionController.class)
class SubscriptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ISubscriptionService subscriptionService;

    @MockitoBean
    private SubscriptionMapper subscriptionMapper;

    @Test
    void getBeneficiaries_Success() throws Exception {
        when(subscriptionService.getBeneficiariesBySubscriptionId(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/subscription/1/beneficiaries"))
                .andExpect(status().isOk());
    }
}
