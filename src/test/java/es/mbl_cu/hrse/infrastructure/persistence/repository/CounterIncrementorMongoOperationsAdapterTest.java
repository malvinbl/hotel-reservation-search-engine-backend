package es.mbl_cu.hrse.infrastructure.persistence.repository;

import com.mongodb.client.result.UpdateResult;
import es.mbl_cu.hrse.domain.vo.Constant;
import es.mbl_cu.hrse.infrastructure.persistence.entity.SearchEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CounterIncrementorMongoOperationsAdapterTest {

    @Mock
    private MongoOperations operations;

    @InjectMocks
    private CounterIncrementorMongoOperationsAdapter counterIncrementorMongoOperationsAdapter;

    @Test
    void should_call_updateFirst_with_correct_query_and_update() {
        var searchId = "searchid1";
        var value = Constant.COUNTER_INCREMENT;

        counterIncrementorMongoOperationsAdapter.incCounterBySearchId(searchId, value);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        ArgumentCaptor<Update> updateCaptor = ArgumentCaptor.forClass(Update.class);

        verify(operations).updateFirst(queryCaptor.capture(), updateCaptor.capture(), eq(SearchEntity.class));

        var capturedQuery = queryCaptor.getValue();
        var capturedUpdate = updateCaptor.getValue();
        var queryDoc = capturedQuery.getQueryObject();
        var updateDoc = capturedUpdate.getUpdateObject();

        Assertions.assertAll(
                () -> assertThat(queryDoc.get("_id")).isEqualTo(searchId),
                () -> assertThat(updateDoc.get("$inc"))
                        .extracting("counter")
                        .isEqualTo(value)
        );
    }

    @Test
    void should_handle_when_no_document_is_updated() {
        var searchId = "no-exists";
        var value = Constant.COUNTER_INCREMENT;

        when(operations.updateFirst(any(Query.class), any(Update.class), eq(SearchEntity.class)))
                .thenReturn(UpdateResult.acknowledged(0L, 0L, null));

        counterIncrementorMongoOperationsAdapter.incCounterBySearchId(searchId, value);

        ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
        verify(operations).updateFirst(queryCaptor.capture(), any(Update.class), eq(SearchEntity.class));

        var queryDoc = queryCaptor.getValue().getQueryObject();

        Assertions.assertAll(
                () -> assertThat(queryDoc.get("_id")).isEqualTo(searchId),
                () -> verify(operations).updateFirst(any(), any(), eq(SearchEntity.class))
        );
    }

}
