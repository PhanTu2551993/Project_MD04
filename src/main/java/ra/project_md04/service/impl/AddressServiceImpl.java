package ra.project_md04.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md04.model.dto.request.AddressRequest;
import ra.project_md04.model.dto.response.AddressResponse;
import ra.project_md04.model.dto.response.converter.AddressConverter;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Users;
import ra.project_md04.repository.IAddressRepository;
import ra.project_md04.service.IAddressService;
import ra.project_md04.service.IUserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private IUserService userService;

    @Override
    public Address addNewAddress(AddressRequest addressRequest) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Address newAddress = Address.builder()
                .users(currentUser)
                .fullAddress(addressRequest.getFullAddress())
                .phone(addressRequest.getPhone())
                .receiveName(addressRequest.getReceiveName())
                .build();
        return addressRepository.save(newAddress);
    }

    @Override
    public List<AddressResponse> getUserAddresses() {
        Users currentUser = userService.getCurrentLoggedInUser();
        List<Address> addresses = addressRepository.findByUsers(currentUser);
        return addresses.stream().map(AddressConverter::toAddressResponse).collect(Collectors.toList());
    }

    @Override
    public AddressResponse getAddressByAddressId(Long addressId) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Address address = addressRepository.findByAddressIdAndUsers(addressId, currentUser)
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại địa chỉ có ID: " + addressId));
        return AddressConverter.toAddressResponse(address);
    }


    @Override
    public void deleteAddressById(Long addressId) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Address address = addressRepository.findByAddressIdAndUsers(addressId, currentUser)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy địa chỉ"));

        addressRepository.delete(address);
    }
}
