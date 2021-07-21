package uz.pdp.appcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {
    @NotNull(message = "Name cant be empty")
    private String name;

    @NotNull(message = "PhoneNumber cant be empty")
    private String phoneNumber;

    @NotNull(message = "AddressId cant be empty")
    private Integer addressId;

    @NotNull(message = "DepartmentId cant be empty")
    private Integer departmentId;


}
