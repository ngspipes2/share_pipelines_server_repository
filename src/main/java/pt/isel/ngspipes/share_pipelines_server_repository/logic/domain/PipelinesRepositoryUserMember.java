package pt.isel.ngspipes.share_pipelines_server_repository.logic.domain;

import pt.isel.ngspipes.share_authentication_server.logic.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "PipelinesRepositoryUserMember")
public class PipelinesRepositoryUserMember {

    @Id
    @SequenceGenerator(name="pipelines_repository_user_member_sequence", sequenceName="pipelines_repository_user_member_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pipelines_repository_user_member_sequence")
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NotNull
    private Date creationDate;
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    private User user;
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @ManyToOne(targetEntity = PipelinesRepositoryMetadata.class, optional = false, fetch = FetchType.EAGER)
    private PipelinesRepositoryMetadata repository;
    public PipelinesRepositoryMetadata getRepository() { return repository; }
    public void setRepository(PipelinesRepositoryMetadata repository) { this.repository = repository; }

    private boolean writeAccess;
    public boolean getWriteAccess() { return writeAccess; }
    public void setWriteAccess(boolean writeAccess) { this.writeAccess = writeAccess; }



    public PipelinesRepositoryUserMember(int id, Date creationDate, User user, PipelinesRepositoryMetadata repository, boolean writeAccess) {
        this.id = id;
        this.creationDate = creationDate;
        this.user = user;
        this.repository = repository;
        this.writeAccess = writeAccess;
    }

    public PipelinesRepositoryUserMember() { }

}
