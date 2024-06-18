package ra.project_md04.service;

import ra.project_md04.model.dto.request.AddressRequest;
import ra.project_md04.model.dto.response.AddressResponse;
import ra.project_md04.model.entity.Address;

import java.util.List;

public interface IAddressService {
    Address addNewAddress(AddressRequest addressRequest);
    List<AddressResponse> getUserAddresses();
    AddressResponse getAddressByAddressId(Long addressId);
    void deleteAddressById(Long addressId);
}
