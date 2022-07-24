package com.HCL;


import com.HCL.controller.NACEController;
import com.HCL.dto.NACEDTO;
import com.HCL.entity.NACE;
import com.HCL.service.NACEService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = NACEController.class)
class NaceDataTestCases {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MultipartFile multipartFile;
    @MockBean
    private NACEService naceService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    NACEDTO nacedto = new NACEDTO(398481L, 1, "A", "", "AGRICULTURE, FORESTRY AND FISHING"
            , "This section includes the exploitation of vegetal and animal natural resources, comprising the activities of growing of crops, raising and breeding of animals, harvesting of timber and other plants, animals or animal products from a farm or their natural habitats.",
            "", "", "", "A");



    @Test
    public void putNACEDetailsTest() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "tempCSV.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE, "Test Data".getBytes(UTF_8));
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/nace/importData")
                        .file(file).accept(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andReturn();
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }


    @Test
    public void getNaceDetailsTest() throws Exception {

        Mockito.when(naceService.getNACEDetails(Mockito.anyLong())).thenReturn(nacedto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/nace/getData/")
                .queryParam("orderId", "398481")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"Order\": 398481,\n" +
                "  \"Level\": 1,\n" +
                "  \"Code\": \"A\",\n" +
                "  \"Parent\": \"\",\n" +
                "  \"Description\": \"AGRICULTURE, FORESTRY AND FISHING\",\n" +
                "  \"This item includes\": \"This section includes the exploitation of vegetal and animal natural resources, comprising the activities of growing of crops, raising and breeding of animals, harvesting of timber and other plants, animals or animal products from a farm or their natural habitats.\",\n" +
                "  \"This item also includes\": \"\",\n" +
                "  \"Rulings\": \"\",\n" +
                "  \"The item excludes\": \"\",\n" +
                "  \"Reference to ISIC Rev. 4\": \"A\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }






}
