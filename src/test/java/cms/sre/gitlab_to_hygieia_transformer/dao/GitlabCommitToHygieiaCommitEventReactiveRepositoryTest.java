package cms.sre.gitlab_to_hygieia_transformer.dao;

import cms.sre.dna_common_data_model.hygieia.GitlabCommitToHygieiaCommitEvent;
import cms.sre.gitlab_to_hygieia_transformer.TestConfiguration;
import com.capitalone.dashboard.model.Commit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class GitlabCommitToHygieiaCommitEventReactiveRepositoryTest {
    @Autowired
    private GitlabCommitToHygieiaCommitEventReactiveRepository gitlabCommitToHygieiaCommitEventReactiveRepository;

    @Test
    public void autowiringTest(){
        Assert.assertNotNull(this.gitlabCommitToHygieiaCommitEventReactiveRepository);
    }

}
