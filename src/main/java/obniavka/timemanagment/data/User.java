package obniavka.timemanagment.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String employeeId;
    @Column
    private String phoneNumber;
    @Column
    private String country;
    @Column
    private  String city;
    @Column
    private String street;
    @Column
    private String houseNumber;
    @Column
    private String postCode;
    @Column
    private String email;
    @Column
    private byte[] imageUrl;
    @Column
    private String role;
    @Column
    private String password;

    @Column(length = 1000)
    private String about;
}
