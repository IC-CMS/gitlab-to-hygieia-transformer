package cms.sre.gitlab_to_hygieia_transformer.dao;

import cms.sre.gitlab_to_hygieia_transformer.model.Commit;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CommitReactiveRepository extends ReactiveCrudRepository<Commit, ObjectId> {
}
