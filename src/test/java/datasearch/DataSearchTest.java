package datasearch;

import com.github.nyamnyam.DataSearchApplication;
import com.github.nyamnyam.datasearch.enums.ApiPlatform;
import com.github.nyamnyam.datasearch.model.RequestSearchBlogModel;
import com.github.nyamnyam.datasearch.service.DataSearchService;
import com.github.nyamnyam.datasearch.service.KakaoDataSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = DataSearchApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DataSearchTest {

    @MockBean private KakaoDataSearchService kakaoDataSearchServiceMock;
    @Autowired private DataSearchService dataSearchService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;


    private String defaultQuery;
    private RequestSearchBlogModel defaultRequestModel;

    @BeforeEach
    void beforeEach() {
        defaultQuery = "김밥";
        defaultRequestModel = new RequestSearchBlogModel(defaultQuery,null,null,null,null);
    }

    @Test
    @DisplayName("validation ")
    public void validationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog?query=김밥"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog?query=김밥&page=0"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.get("/search/blog?query=김밥&sortType=ABC"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("when 'call Kakao API Exception' then 'call Naver API' Test")
    public void kakaoBlogApiErrorTest() throws Exception {
        given(kakaoDataSearchServiceMock.searchBlog(defaultRequestModel)).willThrow(Exception.class);
        assertEquals(dataSearchService.searchBlog(defaultRequestModel).apiPlatform, ApiPlatform.NAVER_API);
    }

}
