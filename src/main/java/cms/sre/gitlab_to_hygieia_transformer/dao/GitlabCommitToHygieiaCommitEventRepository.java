package cms.sre.gitlab_to_hygieia_transformer.dao;

import cms.sre.dna_common_data_model.hygieia.GitlabCommitToHygieiaCommitEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface GitlabCommitToHygieiaCommitEventRepository extends ReactiveCrudRepository<GitlabCommitToHygieiaCommitEvent, UUID> {
}
