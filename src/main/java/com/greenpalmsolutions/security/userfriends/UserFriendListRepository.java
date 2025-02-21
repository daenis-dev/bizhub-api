package com.greenpalmsolutions.security.userfriends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserFriendListRepository extends JpaRepository<UserFriendList, Long> {

    // TODO: Query - userIdIsNotInFriendListForOtherUserId
    boolean userIdIsNotInFriendListForOtherUserId(String userId, String otherUserId);
}
