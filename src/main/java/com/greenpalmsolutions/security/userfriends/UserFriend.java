package com.greenpalmsolutions.security.userfriends;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user_friends")
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class UserFriend {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_friends_seq")
    @SequenceGenerator(name = "user_friends_seq", sequenceName = "user_friends_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "user_friend_list_id")
    private long userFriendListId;

    @Column(name = "friend_user_id", nullable = false)
    private String friendUserId;

    @ManyToOne
    @JoinColumn(name = "user_friend_list_id", insertable = false, updatable = false)
    private UserFriendList userFriendList;
}
