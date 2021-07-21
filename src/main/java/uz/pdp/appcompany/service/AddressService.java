package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.payload.AddressDto;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;


    public List<Address> getAddressList() {
        List<Address> addressList = addressRepository.findAll();
        return addressList;
    }


    public Address getAddressById(Integer id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        return addressOptional.orElse(null);
//        return addressOptional.orElseGet(Address::new);
    }


    public ApiResponse addAddress(AddressDto addressDto) {
        boolean existsByHomeNumber = addressRepository.existsByHomeNumber(addressDto.getHomeNumber());
        if (existsByHomeNumber)
            return new ApiResponse("Such a homeNumber address is exists!",false);
        Address address = new Address();
        address.setHomeNumber(addressDto.getHomeNumber());
        address.setStreet(addressDto.getStreet());
        addressRepository.save(address);
        return new ApiResponse("Address added",true);
    }

    public ApiResponse editAddress(AddressDto addressDto,Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("No address with such id was found",false);
        boolean existsByHomeNumberAndIdNot = addressRepository.existsByHomeNumberAndIdNot(addressDto.getHomeNumber(), id);
        if (existsByHomeNumberAndIdNot)
            return new ApiResponse("Such a homeNumber address is exists!",false);
        Address editingAddress = optionalAddress.get();
        editingAddress.setStreet(addressDto.getStreet());
        editingAddress.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(editingAddress);
        return new ApiResponse("Address edited",true);

    }

    public ApiResponse deleteAddress(Integer id){
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address deleted!",true);
        }catch (Exception e){
            return new ApiResponse("No address with such id was found",false);
        }
    }
}
