package vn.Second_Hand.marketplace.service.Impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.DeliveryAddressRequest;
import vn.Second_Hand.marketplace.dto.requests.UpdateDefaultAddressRequest;
import vn.Second_Hand.marketplace.dto.requests.UpdateDeliveryAddressRequest;
import vn.Second_Hand.marketplace.dto.responses.DeliveryAddressResponse;
import vn.Second_Hand.marketplace.entity.DeliveryAddress;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.DeliveryAddressMapper;
import vn.Second_Hand.marketplace.repository.DeliveryAddressRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.IDeliveryAddressService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryAddressServiceImpl implements IDeliveryAddressService {
    DeliveryAddressRepository deliveryAddressRepository;
    UserRepository userRepository;
    DeliveryAddressMapper deliveryAddressMapper;

    @Override
    public List<DeliveryAddressResponse> getAddressesByUser() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<DeliveryAddress> addresses = deliveryAddressRepository.findByBuyer(user);
        return deliveryAddressMapper.toResponseList(addresses);
    }

    @Override
    @Transactional
    public DeliveryAddressResponse addDeliveryAddress(DeliveryAddressRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean isDefault = (request.getDefaultFlag() == 1);

        // In ra log để debug
//        System.out.println("defaultFlag từ request: " + request.getDefaultFlag());
//        System.out.println("Chuyển đổi thành isDefault: " + isDefault);

        if (isDefault) {
            deliveryAddressRepository.updateAllDefaultFalse(user.getId());
        }

        DeliveryAddress address = DeliveryAddress.builder()
                .addressName(request.getAddressName())
                .nameBuyer(request.getNameBuyer())
                .phoneNumber(request.getPhoneNumber())
                .defaultAddress(request.getDefaultFlag())
                .buyer(user)
                .build();

        return deliveryAddressMapper.toResponse(deliveryAddressRepository.save(address));
    }

    @Override
    @Transactional
    public DeliveryAddressResponse updateDefaultAddress(UpdateDefaultAddressRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        deliveryAddressRepository.updateAllDefaultFalse(user.getId());
        
        deliveryAddressRepository.setDefaultAddress(request.getAddressId());

        DeliveryAddress address = deliveryAddressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        return deliveryAddressMapper.toResponse(address);
    }

    @Override
    @Transactional
    public DeliveryAddressResponse updateDeliveryAddress(UpdateDeliveryAddressRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        DeliveryAddress address = deliveryAddressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        // Verify that the address belongs to the authenticated user
        if (address.getBuyer().getId() != user.getId()) {
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
        }

        // Update address fields
        address.setAddressName(request.getAddressName());
        address.setNameBuyer(request.getNameBuyer());
        address.setPhoneNumber(request.getPhoneNumber());

        boolean isDefault = (request.getDefaultFlag() != null && request.getDefaultFlag() == 1);
        if (isDefault && address.getDefaultAddress() != 1) {
            deliveryAddressRepository.updateAllDefaultFalse(user.getId());
            address.setDefaultAddress(1);
        } else if (request.getDefaultFlag() != null) {
            address.setDefaultAddress(request.getDefaultFlag());
        }


        return deliveryAddressMapper.toResponse(deliveryAddressRepository.save(address));
    }


}
