package cms.sre.gitlab_to_hygieia_transformer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import java.util.List;

@Document(collection = "commits")
public class Commit {
    private static enum CommitType{
        NotBuilt, //maven release commits that are not built
        Merge, //github merge commits that dont show up in build change set
        New;

        public static CommitType fromString(String value) {
            for (CommitType commitType : values()) {
                if (commitType.toString().equalsIgnoreCase(value)) {
                    return commitType;
                }
            }
            throw new IllegalArgumentException(value + " is not a valid Commit Type");
        }
    }

    @Id
    private ObjectId id;
    private ObjectId collectorItemId;
    private long timestamp;
    private boolean firstEverCommit;

    protected String scmUrl;
    protected String scmBranch;
    protected String scmRevisionNumber;
    protected String scmCommitLog;
    protected String scmAuthor;
    protected String scmAuthorLogin;
    protected List<String> scmParentRevisionNumbers;
    protected long scmCommitTimestamp;
    protected long numberOfChanges;
    protected CommitType type;
    protected String pullNumber;

    public ObjectId getId() {
        return id;
    }

    public Commit setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public ObjectId getCollectorItemId() {
        return collectorItemId;
    }

    public Commit setCollectorItemId(ObjectId collectorItemId) {
        this.collectorItemId = collectorItemId;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Commit setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public boolean isFirstEverCommit() {
        return firstEverCommit;
    }

    public Commit setFirstEverCommit(boolean firstEverCommit) {
        this.firstEverCommit = firstEverCommit;
        return this;
    }

    public String getScmUrl() {
        return scmUrl;
    }

    public Commit setScmUrl(String scmUrl) {
        this.scmUrl = scmUrl;
        return this;
    }

    public String getScmBranch() {
        return scmBranch;
    }

    public Commit setScmBranch(String scmBranch) {
        this.scmBranch = scmBranch;
        return this;
    }

    public String getScmRevisionNumber() {
        return scmRevisionNumber;
    }

    public Commit setScmRevisionNumber(String scmRevisionNumber) {
        this.scmRevisionNumber = scmRevisionNumber;
        return this;
    }

    public String getScmCommitLog() {
        return scmCommitLog;
    }

    public Commit setScmCommitLog(String scmCommitLog) {
        this.scmCommitLog = scmCommitLog;
        return this;
    }

    public String getScmAuthor() {
        return scmAuthor;
    }

    public Commit setScmAuthor(String scmAuthor) {
        this.scmAuthor = scmAuthor;
        return this;
    }

    public String getScmAuthorLogin() {
        return scmAuthorLogin;
    }

    public Commit setScmAuthorLogin(String scmAuthorLogin) {
        this.scmAuthorLogin = scmAuthorLogin;
        return this;
    }

    public List<String> getScmParentRevisionNumbers() {
        return scmParentRevisionNumbers;
    }

    public Commit setScmParentRevisionNumbers(List<String> scmParentRevisionNumbers) {
        this.scmParentRevisionNumbers = scmParentRevisionNumbers;
        return this;
    }

    public long getScmCommitTimestamp() {
        return scmCommitTimestamp;
    }

    public Commit setScmCommitTimestamp(long scmCommitTimestamp) {
        this.scmCommitTimestamp = scmCommitTimestamp;
        return this;
    }

    public long getNumberOfChanges() {
        return numberOfChanges;
    }

    public Commit setNumberOfChanges(long numberOfChanges) {
        this.numberOfChanges = numberOfChanges;
        return this;
    }

    public CommitType getType() {
        return type;
    }

    public Commit setType(CommitType type) {
        this.type = type;
        return this;
    }

    public String getPullNumber() {
        return pullNumber;
    }

    public Commit setPullNumber(String pullNumber) {
        this.pullNumber = pullNumber;
        return this;
    }
}
