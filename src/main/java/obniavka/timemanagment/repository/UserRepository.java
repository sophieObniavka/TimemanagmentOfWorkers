package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByEmail(String email);

    @Modifying
    @Query("update User u set u.firstName = :firstName, u.lastName = :lastName, u.birth = :birth, u.hired = :hired, u.phoneNumber = :phoneNumber, u.employeeId = :employeeId, u.role = :role, u.country = :country, u.city = :city, u.street = :street, u.houseNumber = :houseNumber, u.postCode = :postCode, u.email = :email, u.imageUrl = :imageUrl, u.vacationDays = :vacationDays, u.sickleaveDays = :sickleaveDays, u.salaryPerHour = :salaryPerHour where u.id = :id")
    void updateUser(@Param(value = "id") Long id,
                    @Param(value = "firstName") String firstName,
                    @Param(value = "lastName") String lastName,
                    @Param(value = "birth") LocalDate birth,
                    @Param(value = "hired") LocalDate hired,
                    @Param(value = "phoneNumber") String phoneNumber,
                    @Param(value = "employeeId") String employeeId,
                    @Param(value = "role") String role,
                    @Param(value = "country") String country,
                    @Param(value = "city") String city,
                    @Param(value = "street") String street,
                    @Param(value = "houseNumber") String houseNumber,
                    @Param(value = "postCode") String postCode,
                    @Param(value = "email") String email,
                    @Param(value = "imageUrl") byte[] imageUrl,
                     @Param(value = "vacationDays") Integer vacationDays,
                     @Param(value = "sickleaveDays") Integer sickleaveDays,
                     @Param(value = "salaryPerHour") Integer salaryPerHour);


    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updateUserPassword(@Param(value = "password") String password, @Param(value = "id") Long id);

}
