package uz.pdp.appcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "CorpName cant be empty")
    private String corpName;

    @NotNull(message = "DirectorName cant be empty")
    private String directorName;

    @NotNull(message = "AddressId cant be empty")
    private Integer addressId;



}
