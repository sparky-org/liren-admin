package com.sparky.lirenadmin.bo;

import com.sparky.lirenadmin.entity.ServiceItem;

public interface ServiceItemBO {

    void createUpdateServiceItem(ServiceItem serviceItem);

    ServiceItem getServiceItem(Long id);

}
