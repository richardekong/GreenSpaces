package com.daveace.greenspaces.lightweight;

import com.daveace.greenspaces.user.User;
import com.daveace.greenspaces.user.UserRepo;
import com.daveace.greenspaces.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.daveace.greenspaces.util.Constants.DELETED_SUCCESSFULLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepo repo;

    @InjectMocks
    UserService service;

    private User user;

    @BeforeEach
    void init(){
        user = User
                .builder()
                .id("b583b456-9300-4cbd-4bcd-199225f5d42c")
                .username("fake@mail.com")
                .build();
    }

    @Test
    @DisplayName("Verify that user can be saved")
    void verifyThatUserCanBeSaved() {
        when(repo.save(user)).thenReturn(user);
        User savedUser = service.save(user);
        assertThat(savedUser).isNotNull();
        verify(repo, times(1)).save(user);
    }

    @Test
    @DisplayName("Verify that a non existent record can't be read")
    void verifyThatNonExistentRecordCantBeRead(){
        String id = "fake_id";
        when(repo.findUserById(id)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findUserById(id));
        assertThat(ex.getMessage()).isNotBlank();
    }

    @Test
    @DisplayName("Verify that a user can be read")
    void verifyThatUserCanBeRead() {
        String id = user.getId();
        when(repo.findUserById(id)).thenReturn(Optional.of(user));
        User foundUser = service.findUserById(id);
        assertThat(foundUser).isNotNull();
        verify(repo, times(1)).findUserById(id);
    }

    @Test
    @DisplayName("Verify that users can be read")
    void verifyThatUsersCanBeRead() {
        List<User> users = List.of(user);
        when(repo.findAll()).thenReturn(users);
        List<User> foundUsers = service.findAllUsers();
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).isNotEmpty();
        verify(repo, times(1)).findAll();
    }

    @Test
    @DisplayName("Verify that a user can be updated")
    void verifyThatUserCanBeUpdated() {
        String username = user.getUsername();
        Function<String, User> ops = name -> {
            var userToUpdate = service.findUserByUsername(name);
            userToUpdate.setId("000");
            return userToUpdate;
        };
        when(repo.save(user)).thenReturn(user);
        User savedUser = service.save(user);
        when(repo.findUserByUsername(username)).thenReturn(Optional.of(savedUser));
        User foundUser = service.findUserByUsername(username);
        service.update(username, ops);
        verify(repo, times(2)).save(foundUser);

    }

    @Test
    @DisplayName("Verify that a user can be deleted")
    void verifyThatUSerCanBeDeleted() {
        String id = user.getId();
        when(repo.save(user)).thenReturn(user);
        User savedUser = service.save(user);
        when(repo.findUserById(id)).thenReturn(Optional.of(savedUser));
        String deleteResult = service.delete(id);
        verify(repo, times(1)).deleteById(id);
        assertThat(deleteResult).isNotBlank();
        assertThat(deleteResult).isEqualTo(DELETED_SUCCESSFULLY);
    }

}

