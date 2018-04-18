package cms.sre.gitlab_to_hygieia_transformer.dao;

import cms.sre.dna_common_data_model.hygieia.GitlabCommitToHygieiaCommitEvent;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface GitlabCommitToHygieiaCommitEventReactiveRepository extends ReactiveCrudRepository<GitlabCommitToHygieiaCommitEvent, UUID> {
    @Tailable
    public Flux<GitlabCommitToHygieiaCommitEvent> findWithTailableCursorBy();
}
