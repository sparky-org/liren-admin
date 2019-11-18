package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ServiceItem;

import java.util.List;

public interface ServiceItemBO {

    void createUpdateServiceItem(ServiceItem serviceItem);

    ServiceItem getServiceItem(Long id);

    List<ServiceItem> getServiceItemByShopNo(Long shoNo);

    Integer countServiceItem(Long shopNo);

    List<ServiceItem> pagingQueryServiceItem(Long shopNo, Integer start, Integer pageSize);
}
