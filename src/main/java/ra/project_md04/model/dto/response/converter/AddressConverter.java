package ra.project_md04.model.dto.response.converter;

import ra.project_md04.model.dto.response.AddressResponse;
import ra.project_md04.model.dto.response.ProductResponse;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Product;

public class AddressConverter {
    public static AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .fullAddress(address.getFullAddress())
                .phone(address.getPhone())
                .receiveName(address.getReceiveName())
                .build();
    }
}
