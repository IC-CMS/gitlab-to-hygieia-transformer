package cms.sre.gitlab_to_hygieia_transformer.service;

import cms.sre.dna_common_data_model.hygieia.GitlabCommitToHygieiaCommitEvent;
import cms.sre.dna_common_data_model.sourceControl.SourceControlCommitEvent;
import cms.sre.gitlab_to_hygieia_transformer.dao.GitlabCommitToHygieiaCommitEventReactiveRepository;
import cms.sre.gitlab_to_hygieia_transformer.dao.SourceControlCommitEventReactiveRepository;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.model.GitlabGitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Microservice implements CommandLineRunner{

    @Autowired
    private String classification;

    @Autowired
    private SourceControlCommitEventReactiveRepository sourceControlCommitEventReactiveRepository;

    @Autowired
    private GitlabCommitToHygieiaCommitEventReactiveRepository gitlabCommitToHygieiaCommitEventReactiveRepository;

    private Commit getCommit(SourceControlCommitEvent evt){
        Commit commit = new Commit();
        commit.setTimestamp(evt.getTimestamp().getTimeInMillis());
        commit.setNumberOfChanges(evt.getNumberOfChanges());
        commit.setScmAuthor(evt.getUserName());
        commit.setScmBranch(evt.getBranchName());
        commit.setScmCommitLog(evt.getCommitMessage());
        commit.setScmCommitTimestamp(evt.getTimestamp().getTimeInMillis());
        commit.setScmRevisionNumber(evt.getRevisionNumber());
        commit.setScmUrl(evt.getSshUrl());
        return commit;
    }

    private CollectorItem getCollectorItem(SourceControlCommitEvent evt){
        GitlabGitRepo repo = new GitlabGitRepo();
        repo.setBranch(evt.getBranchName());
        repo.setLastUpdateTime(evt.getTimestamp().getTime());
        repo.setRepoUrl(evt.getSshUrl());
        repo.setDescription("Gitlab Repo found by webhook");
        repo.setEnabled(false);
        repo.setNiceName(evt.getRepositoryName());
        repo.setPushed(true);
        return repo;
    }

    public void incrementalUpkeep(){
        this.sourceControlCommitEventReactiveRepository.findWithTailableCursorBy()
                .doOnNext(evt -> {
                    GitlabCommitToHygieiaCommitEvent event = new GitlabCommitToHygieiaCommitEvent(this.classification, "gitlab-to-hygieia-transformer")
                            .setCommit(this.getCommit(evt))
                            .setCollectorItem(this.getCollectorItem(evt));

                    this.gitlabCommitToHygieiaCommitEventReactiveRepository.save(event)
                            .doOnSuccess(System.out::println)
                            .doOnError(System.err::println);
                })
        .doOnNext(System.out::println);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("######### Starting up Incremental Upkeep ##########");
       this.incrementalUpkeep();
    }
}
