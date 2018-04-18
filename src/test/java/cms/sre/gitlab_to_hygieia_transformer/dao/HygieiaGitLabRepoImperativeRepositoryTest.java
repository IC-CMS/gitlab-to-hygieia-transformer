package cms.sre.gitlab_to_hygieia_transformer.dao;

import cms.sre.gitlab_to_hygieia_transformer.TestConfiguration;
import static org.junit.Assert.*;

import com.capitalone.dashboard.model.GitlabGitRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class HygieiaGitLabRepoImperativeRepositoryTest {

    @Autowired
    private HygieiaGitlabRepoImperativeRepository hygieiaGitlabRepoImperativeRepository;

    @Test
    public void autowiringTest(){
        assertNotNull(this.hygieiaGitlabRepoImperativeRepository);
    }
}
