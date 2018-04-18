package cms.sre.gitlab_to_hygieia_transformer.service;

import cms.sre.dna_common_data_model.hygieia.GitlabCommitToHygieiaCommitEvent;
import cms.sre.dna_common_data_model.sourceControl.SourceControlCommitEvent;
import cms.sre.gitlab_to_hygieia_transformer.dao.GitlabCommitToHygieiaCommitEventReactiveRepository;
import cms.sre.gitlab_to_hygieia_transformer.dao.HygieiaGitlabRepoImperativeRepository;
import cms.sre.gitlab_to_hygieia_transformer.dao.SourceControlCommitEventReactiveRepository;
import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.model.GitlabGitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

//@Component
public class Microservice implements CommandLineRunner {

    private HygieiaGitlabRepoImperativeRepository hygieiaGitlabRepoImperativeRepository;
    private SourceControlCommitEventReactiveRepository sourceControlCommitEventReactiveRepository;
    private GitlabCommitToHygieiaCommitEventReactiveRepository gitlabCommitToHygieiaCommitEventReactiveRepository;

    @Autowired
    public Microservice (HygieiaGitlabRepoImperativeRepository hygieiaGitlabRepoImperativeRepository,
                         SourceControlCommitEventReactiveRepository sourceControlCommitEventReactiveRepository,
                         GitlabCommitToHygieiaCommitEventReactiveRepository gitlabCommitToHygieiaCommitEventReactiveRepository){
        this.hygieiaGitlabRepoImperativeRepository = hygieiaGitlabRepoImperativeRepository;
        this.sourceControlCommitEventReactiveRepository = sourceControlCommitEventReactiveRepository;
        this.gitlabCommitToHygieiaCommitEventReactiveRepository = gitlabCommitToHygieiaCommitEventReactiveRepository;
    }

    private GitlabGitRepo findRepo(SourceControlCommitEvent evt){
        List<GitlabGitRepo> repos = this.hygieiaGitlabRepoImperativeRepository.findByRepoUrl(evt.getSshUrl());
        GitlabGitRepo repo = null;
        if(repos.size() == 0){
            repo = new GitlabGitRepo();
            repo.setBranch(evt.getBranchName());
            repo.setLastUpdateTime(evt.getTimestamp().getTime());
            repo.setRepoUrl(evt.getSshUrl());
            repo.setDescription("Gitlab Repo found by webhook");
            repo.setEnabled(false);
            repo.setNiceName(evt.getRepositoryName());
            repo.setPushed(true);
            this.hygieiaGitlabRepoImperativeRepository.save(repo);
            repo = this.hygieiaGitlabRepoImperativeRepository
                    .findByRepoUrl(evt.getSshUrl())
                    .get(0);
        } else if (repos.size() == 1){
            repo = repos.get(0);
        } else {
            for(GitlabGitRepo r : repos){
                if(r.getBranch().equals(evt.getBranchName()) && r.getRepoUrl().equals(evt.getSshUrl())){
                    repo = r;
                    break;
                }
            }
        }
        return repo;
    }

    private void incrementalUpkeep(){
        this.sourceControlCommitEventReactiveRepository.findWithTailableCursorBy()
                .doOnNext(evt -> {
                    Commit commit = new Commit();
                    commit.setCollectorItemId(this.findRepo(evt).getCollectorId());
                    commit.setTimestamp(evt.getTimestamp().getTimeInMillis());
                    commit.setNumberOfChanges(evt.getNumberOfChanges());
                    commit.setScmAuthor(evt.getUserName());
                    commit.setScmBranch(evt.getBranchName());
                    commit.setScmCommitLog(evt.getCommitMessage());
                    commit.setScmCommitTimestamp(evt.getTimestamp().getTimeInMillis());
                    commit.setScmRevisionNumber(evt.getRevisionNumber());
                    commit.setScmUrl(evt.getSshUrl());

                    GitlabCommitToHygieiaCommitEvent event = new GitlabCommitToHygieiaCommitEvent()
                            .setCommit(commit);
                    this.gitlabCommitToHygieiaCommitEventReactiveRepository.save(event)
                            .doOnSuccess(System.out::println)
                            .doOnError(System.err::println);
                })
        .doOnNext(System.out::println);
    }



    @Override
    public void run(String... args) throws Exception {
        this.incrementalUpkeep();
    }
}
