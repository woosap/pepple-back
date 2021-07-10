package woosap.Pepple.entity;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import woosap.Pepple.entity.type.Job;

@Entity
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    // nickname -> should not be same
    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Job job;

    @ElementCollection
    @CollectionTable(name ="sns_list", joinColumns = @JoinColumn(name = "userId"))
    private List<String> snsList;
}
