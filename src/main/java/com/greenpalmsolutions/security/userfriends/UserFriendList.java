package com.greenpalmsolutions.security.userfriends;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_friend_lists")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class UserFriendList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_friend_lists_seq")
    @SequenceGenerator(name = "user_friend_lists_seq", sequenceName = "user_friend_lists_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "created_date_time_in_utc")
    private ZonedDateTime createdDateTime;

    @OneToMany(mappedBy = "userFriendList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFriend> friends = new ArrayList<>();
}
