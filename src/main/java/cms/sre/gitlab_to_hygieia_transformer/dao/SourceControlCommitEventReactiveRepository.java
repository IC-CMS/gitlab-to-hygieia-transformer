package cms.sre.gitlab_to_hygieia_transformer.dao;

import cms.sre.dna_common_data_model.sourceControl.SourceControlCommitEvent;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface SourceControlCommitEventReactiveRepository extends ReactiveCrudRepository<SourceControlCommitEvent, UUID> {
    @Tailable
    public Flux<SourceControlCommitEvent> findWithTailableCursorBy();

    @Tailable
    public Flux<SourceControlCommitEvent> findWithTailableCursorByLabelsNotIn(String label);
}
