package com.github.chen0040.bootslingshot.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chen0040.bootslingshot.models.Claim;
import com.github.chen0040.bootslingshot.models.ClaimInfo;
import com.github.chen0040.bootslingshot.repositories.ClaimInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;


/**
 * Created by xschen on 10/9/2017.
 */
@Service
public class ClaimServiceImpl implements ClaimService {

   private static final Logger logger = LoggerFactory.getLogger(ClaimServiceImpl.class);

   @Autowired
   private ElasticSearchService elasticSearchService;

   @Autowired
   private ClaimInfoRepository claimInfoRepository;

   @Transactional
   private void updateClaimInfo(Claim claim) {
      logger.info("Update claim info: \n{}", JSON.toJSONString(claim, SerializerFeature.PrettyFormat));
      String cacheId = claim.getCacheId();
      ClaimInfo claimInfo = claimInfoRepository.findFirstByCacheId(cacheId);

      if(claimInfo==null) {
         claimInfo = new ClaimInfo();
      }

      claimInfo.setCacheId(cacheId);
      claimInfo.setClaimStatus(claim.getStatusId());
      claimInfo.setClaimType(claim.getClaimType());
      claimInfo.setUsername(claim.getUsername());

      claimInfoRepository.save(claimInfo);
   }

   @Override public Receipt save(Receipt receipt) {

      try {
         String cacheId = receipt.getCacheId();
         logger.info("Save receipt {} to elastic search", cacheId);
         elasticSearchService.index(receipt, "receipt", "receipt", cacheId);

         updateClaimInfo(receipt);

      }
      catch (IOException e) {
         e.printStackTrace();
      }
      return receipt;
   }

   @Override public Mileage save(Mileage mileage) {

      try {
         String cacheId = mileage.getCacheId();
         logger.info("Save mileage {} to elastic search", cacheId);
         elasticSearchService.index(mileage, "mileage", "mileage", cacheId);
         updateClaimInfo(mileage);
      }
      catch (IOException e) {
         e.printStackTrace();
      }
      return mileage;
   }
}
