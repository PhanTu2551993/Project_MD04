package ra.project_md04.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md04.model.dto.request.WishListRequest;
import ra.project_md04.model.dto.response.WishListResponse;
import ra.project_md04.model.dto.response.converter.WishListConverter;
import ra.project_md04.model.entity.Product;
import ra.project_md04.model.entity.Users;
import ra.project_md04.model.entity.WishList;
import ra.project_md04.repository.IProductRepository;
import ra.project_md04.repository.IWishListRepository;
import ra.project_md04.service.IUserService;
import ra.project_md04.service.IWishListService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
public class WishListServiceImpl implements IWishListService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IWishListRepository wishListRepository;


    @Override
    public WishListResponse addWishList(WishListRequest wishListRequest) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Product product = productRepository.findById(wishListRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm"));

        WishList newWishList = WishList.builder()
                .user(currentUser)
                .product(product)
                .build();

        WishList savedWishList = wishListRepository.save(newWishList);

        return WishListConverter.toWishListResponse(savedWishList);
    }

    @Override
    public List<WishListResponse> getWishList() {
        Users currentUser = userService.getCurrentLoggedInUser();
        List<WishList> wishListResponseList = wishListRepository.findAllByUser(currentUser);
        return wishListResponseList.stream().map(WishListConverter::toWishListResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteWishList(Long wishListId) {
        Users currentUser = userService.getCurrentLoggedInUser();
        WishList wishList = wishListRepository.findByWishListIdAndUser(wishListId,currentUser)
                .orElseThrow(() -> new NoSuchElementException("Không có sản phẩm"));
        wishListRepository.delete(wishList);
    }
}
