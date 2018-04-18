package cms.sre.gitlab_to_hygieia_transformer.dao;

import com.capitalone.dashboard.model.GitlabGitRepo;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class HygieiaGitlabRepoImperativeRepository  {
    private MongoTemplate mongoTemplate;

    @Autowired
    public HygieiaGitlabRepoImperativeRepository(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<GitlabGitRepo> findByRepoUrl(String repoUrl){
        List<GitlabGitRepo> repos = this.mongoTemplate.findAll(GitlabGitRepo.class);
        List<GitlabGitRepo> ret = new LinkedList<>();
        for(GitlabGitRepo r : repos){
            if(r.getRepoUrl() != null && r.getRepoUrl().equalsIgnoreCase(repoUrl)){
                ret.add(r);
            }
        }
        return ret;
    }

    public void save(GitlabGitRepo obj){
        this.mongoTemplate.save(obj);
    }
}
