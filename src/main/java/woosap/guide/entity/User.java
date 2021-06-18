package woosap.guide.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import woosap.guide.type.Job;

public class User {

    @JsonIgnore
    private static Long id = 1L;
    private String nickName;
    private String email;
    private Job job;
    @JsonIgnore
    private String password;
    private List<String> snsList;
    private String imagePath;
    private boolean socialJoined;
}
