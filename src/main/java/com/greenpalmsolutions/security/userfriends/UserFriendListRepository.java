package com.greenpalmsolutions.security.userfriends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface UserFriendListRepository extends JpaRepository<UserFriendList, Long> {

    @Query("""
    SELECT CASE WHEN COUNT(uf) > 0 THEN true ELSE false END
    FROM UserFriend uf
    JOIN uf.userFriendList ufl
    WHERE ufl.userId = :secondUserId
    AND uf.friendUserId = :firstUserId
    """)
    boolean isUserFriend(
            @Param("firstUserId") String firstUserId,
            @Param("secondUserId") String secondUserId
    );
}
