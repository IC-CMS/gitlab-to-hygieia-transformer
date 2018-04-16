package cms.sre.gitlab_to_hygieia_transformer.dao;

import com.capitalone.dashboard.model.GitlabGitRepo;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HygieiaGitlabRepoRepository extends CrudRepository<GitlabGitRepo, ObjectId> {
    List<GitlabGitRepo> findByRepoUrl(String repoUrl);
}
