package ra.project_md04.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.project_md04.model.dto.request.AddressRequest;
import ra.project_md04.model.dto.request.UpdateUserRequest;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Users;
import ra.project_md04.repository.IAddressRepository;
import ra.project_md04.repository.IUserRepository;
import ra.project_md04.service.IUserService;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    IAddressRepository addressRepository;

    @Override
    public Page<Users> getUserPaging(String searchName, Integer page, Integer itemPage, String orderBy, String direction) {
        Pageable pageable = null;
        if(orderBy!=null && !orderBy.isEmpty()){
            // co sap xep
            Sort sort = null;
            switch (direction){
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page, itemPage,sort);
        }else{
            //khong sap xep
            pageable = PageRequest.of(page, itemPage);
        }

        //xu ly ve tim kiem
        if(searchName!=null && !searchName.isEmpty()){
            //co tim kiem
            return userRepository.findUsersByUsernameAndSorting(searchName,pageable);
        }else{
            //khong tim kiem
            return userRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Users> getAllUser(Integer page, Integer itemPage, String orderBy, String direction) {
        Pageable pageable = null;
        if(orderBy!=null && !orderBy.isEmpty()){
            Sort sort = null;
            switch (direction){
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page, itemPage,sort);
        }else{
            pageable = PageRequest.of(page, itemPage);
        }
        return userRepository.findAll(pageable);
    }

    @Override
    public Users getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new NoSuchElementException("Không tồn tại người dùng"));
    }

    @Override
    public Users getUserByUserName(String username) {
        return userRepository.findUsersByUsername(username).orElseThrow(()->new NoSuchElementException("Không tồn tại người dùng"));
    }

    @Override
    public Users updateUserStatus(Long userId, Boolean status) {
        Users user = getUserById(userId);
        user.setStatus(status);
        return userRepository.save(user);
    }

    @Override
    public boolean changePassword(String oldPass, String newPass, String confirmNewPass) {
        Users currentUser = getCurrentLoggedInUser();

        if (!passwordEncoder.matches(oldPass, currentUser.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng !");
        }
        if (!newPass.equals(confirmNewPass)) {
            throw new IllegalArgumentException("Nhập lại mật khẩu không chính xác !");
        }

        currentUser.setPassword(passwordEncoder.encode(newPass));
        currentUser.setUpdatedAt(new Date());
        userRepository.save(currentUser);
        return true;
    }

    @Override
    public Users updateUser(UpdateUserRequest updateUserRequest) {
        Users updateUser = getCurrentLoggedInUser();

        if (updateUserRequest.getFullName() != null) {
            updateUser.setFullName(updateUserRequest.getFullName());
        }
        if (updateUserRequest.getEmail() != null) {
            updateUser.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getPhone() != null) {
            updateUser.setPhone(updateUserRequest.getPhone());
        }
        if (updateUserRequest.getAddress() != null) {
            updateUser.setAddress(updateUserRequest.getAddress());
        }
        if (updateUserRequest.getAvatar() != null) {
            updateUser.setAvatar(updateUserRequest.getAvatar());
        }
        updateUser.setUpdatedAt(new Date());
        return userRepository.save(updateUser);
    }

    @Override
    public Users getCurrentLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUsersByUsername(username).orElseThrow(() -> new NoSuchElementException("Không có người dùng"));
    }

    @Override
    public Address addNewAddress(AddressRequest addressRequest) {
        Users currentUser = getCurrentLoggedInUser();

        Address newAddress = Address.builder()
                .users(currentUser)
                .fullAddress(addressRequest.getFullAddress())
                .phone(addressRequest.getPhone())
                .receiveName(addressRequest.getReceiveName())
                .build();
        return addressRepository.save(newAddress);
    }

}
