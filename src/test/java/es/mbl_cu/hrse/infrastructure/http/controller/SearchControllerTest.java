package es.mbl_cu.hrse.infrastructure.http.controller;

import es.mbl_cu.hrse.MotherSearch;
import es.mbl_cu.hrse.domain.dto.SavedSearchDTO;
import es.mbl_cu.hrse.domain.service.SearchService;
import es.mbl_cu.hrse.domain.vo.Constant;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.CounterResponse;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchCounterResponse;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchRequest;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchResponse;
import es.mbl_cu.hrse.infrastructure.http.controller.mapper.SearchMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @Mock
    private SearchService searchService;

    @Mock
    private SearchMapper searchMapper;

    @InjectMocks
    private SearchController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void should_return_searchResponse_when_post_search() throws Exception {
        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);

        var request = new SearchRequest(hotelId, checkIn, checkOut, List.of(30));

        var search = MotherSearch.buildValid();
        var searchId = "searchid1";
        var savedSearchDTO = new SavedSearchDTO(searchId);

        SearchResponse response = new SearchResponse(searchId);

        when(searchMapper.toSearch(request)).thenReturn(search);
        when(searchService.search(search)).thenReturn(savedSearchDTO);
        when(searchMapper.toSearchResponse(savedSearchDTO)).thenReturn(response);

        String json = """
            {"hotelId":"1","checkIn":"25/06/2025","checkOut":"25/06/2025","ages":[30]}
        """;

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value(searchId));
    }

    @Test
    void should_return_counterResponse_when_get_count() throws Exception {
        var searchId = "searchid1";
        var search = MotherSearch.buildValid();

        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);

        var searchCounterResponse = new SearchCounterResponse(hotelId, checkIn, checkOut, List.of(30));
        var counterResponse = new CounterResponse(searchId, searchCounterResponse, Constant.COUNTER_INCREMENT);

        when(searchService.getSearchCount(searchId)).thenReturn(search);
        when(searchMapper.toCounterResponse(search)).thenReturn(counterResponse);

        mockMvc.perform(get("/count")
                        .param("searchId", searchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(searchId))
                .andExpect(jsonPath("$.search.hotelId").value(hotelId))
                .andExpect(jsonPath("$.count").value(Constant.COUNTER_INCREMENT));
    }

}
