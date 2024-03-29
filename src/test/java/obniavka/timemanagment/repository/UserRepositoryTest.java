package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;
    @BeforeEach
    public void initEach(){
        user = userRepository.save(User.builder()
                .id(4L)
                .firstName("Anna")
                .lastName("Hey")
                .country("Ukraine")
                .city("Lviv")
                .street("Some")
                .role("designer")
                .email("hey@gmail.com")
                .phoneNumber("1234567890")
                .postCode("79052")
                .password("jjojoj123")
                .build()
        );

        User user1 = new User();
        user1.setFirstName("Rolf");
        user1.setLastName("Brzoskwinia");
        user1.setId(2L);
        user1.setEmail("example@gmail.com");


        userRepository.save(user1);
    }

    @Test
    @DisplayName("create and save user")
    void testCreateAndSaveUser(){
        User user = new User();

        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Monty");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setImageUrl("default.jpg".getBytes());
        user.setStreet("Sample Street");
        user.setHouseNumber("12");
        user.setCountry("United States");
        user.setCity("Springfield");
        user.setRole("ROLE_USER");
        user.setPostCode("12345");
        user.setPhoneNumber("0123456789");

        User saveUser = userRepository.save(user);

        assertNotNull(saveUser);
        assertNotNull(saveUser.getId());
        assertNotNull(saveUser.getFirstName());
        assertNotNull(saveUser.getLastName());
        assertNotNull(saveUser.getEmail());
        assertNotNull(saveUser.getPassword());
        assertNotNull(saveUser.getImageUrl());
        assertNotNull(saveUser.getStreet());
        assertNotNull(saveUser.getCity());
        assertNotNull(saveUser.getHouseNumber());
        assertNotNull(saveUser.getPhoneNumber());
        assertNotNull(saveUser.getPostCode());
        assertNotNull(saveUser.getCountry());
        assertNotNull(saveUser.getRole());
    }

    @Test
    @DisplayName("find user by id")
    void testFindUserById(){
        User user = new User();

        user.setFirstName("John");

        user = userRepository.save(user);
        System.out.println(user.getId());

        assertNotNull(user);
        User userRes = userRepository.findById(user.getId()).get();
        assertNotNull(userRes);
    }

    @Test
    @DisplayName("find all users")
    void testFindAllUsers(){
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    @DisplayName("delete user by id")
    void testDeleteUserById(){
       User user1 = userRepository.findById(user.getId()).get();

       assertNotNull(user1);

       userRepository.deleteById(user1.getId());

       assertFalse(userRepository.findById(user.getId()).isPresent());
    }
}
