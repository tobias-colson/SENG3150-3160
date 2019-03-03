package seng3150.group4.dao;

import org.springframework.stereotype.Repository;
import seng3150.group4.entity.FlightHistoryEntity;
import seng3150.group4.entity.UserEntity;

import java.util.List;

@Repository
public interface IUserDao
{
    UserEntity getUserByUserName(String userName);

    void createNewUser(String username, String title, String firstname, String lastname, String phonenum, String dob, String email, String hashedPassword);

    void updateUser(UserEntity currentUser, String title, String firstname, String lastname, String phonenum, String dob, String email, String password);

    List<FlightHistoryEntity> getFlightHistory(UserEntity currentUser);
}
