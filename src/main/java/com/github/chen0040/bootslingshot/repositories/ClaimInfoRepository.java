package com.github.chen0040.bootslingshot.repositories;


import com.github.chen0040.bootslingshot.models.ClaimInfo;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by xschen on 10/9/2017.
 */
public interface ClaimInfoRepository  extends CrudRepository<ClaimInfo,Long> {
   ClaimInfo findFirstByCacheId(String cacheId);
}
