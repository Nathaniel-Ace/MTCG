package at.fhtw.sampleapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @JsonAlias({"id"})
    private Integer id;

    @Getter
    @Setter
    @JsonAlias({"username"})
    private String username;

    @Getter
    @Setter
    @JsonAlias({"password"})
    private String password;

    @Getter
    @Setter
    @JsonAlias({"coins"})
    private Integer coins;

    @Getter
    @Setter
    @JsonAlias({"name"})
    private String name;

    @Getter
    @Setter
    @JsonAlias({"bio"})
    private String bio;

    @Getter
    @Setter
    @JsonAlias({"image"})
    private String image;

    // Constructor with @JsonCreator and @JsonProperty annotations for deserialization
    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    public User() {

    }
}
