package cms.sre.gitlab_to_hygieia_transformer.service;

import cms.sre.gitlab_to_hygieia_transformer.TestConfiguration;
import cms.sre.gitlab_to_hygieia_transformer.dao.GitlabCommitToHygieiaCommitEventReactiveRepository;
import cms.sre.gitlab_to_hygieia_transformer.dao.SourceControlCommitEventReactiveRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class MicroserviceTest {

    @Mock
    private GitlabCommitToHygieiaCommitEventReactiveRepository gitlabCommitToHygieiaCommitEventReactiveRepository;

    @Mock
    private SourceControlCommitEventReactiveRepository sourceControlCommitEventReactiveRepository;

    @InjectMocks
    private Microservice microservice;

    @Test
    public void autowiringTest(){
        Assert.assertNotNull(this.microservice);
    }
}
