package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.TradinPost;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ImageMapper;
import com.example.pipayshopapi.mapper.TradinPostMapper;
import com.example.pipayshopapi.service.TradinPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.service.UserInfoService;
import com.example.pipayshopapi.util.StringUtil;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wzx
 * @since 2023-09-09
 */
@Service
public class TradinPostServiceImpl extends ServiceImpl<TradinPostMapper, TradinPost> implements TradinPostService {

    @Resource
    private TradinPostMapper tradinPostMapper;
    @Resource
    private ImageMapper imageMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    @Autowired
    private UserInfoService userInfoService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishTradition(String token) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String publisherId = dataFromToken.get("publisherId", String.class);
        Integer typeId = dataFromToken.get("typeId", Integer.class);
        String content = dataFromToken.get("content", String.class);
        BigDecimal pointBalance = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("pointBalance", String.class)));
        BigDecimal piBalance = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("piBalance", String.class)));
        String tradinId = StringUtil.generateShortId();
        int insert = 0;
        // 发布者换积分
        if (typeId == 0){
        insert += tradinPostMapper.insert(new TradinPost(tradinId, publisherId, typeId, content, pointBalance, piBalance,null));
        if (insert < 1){
           throw new BusinessException("插入失败失败");
        }
          // 发布者换pi币
        } else if (typeId == 1) {
          // 获取发布者账户
          AccountInfoVO accountInfoVO = accountInfoMapper.selectAccountInfo(publisherId);
          if (accountInfoVO.getAvailableBalance().compareTo(pointBalance) < 0) {
              throw new BusinessException("账户可用余额不足");
          }

          // 计算积分、冻结积分
          BigDecimal subtractPointBalance = accountInfoVO.getAvailableBalance().subtract(pointBalance);
          BigDecimal addFrozenBalance = accountInfoVO.getFrozenBalance().add(pointBalance);

          String piAddress = dataFromToken.get("piAddress", String.class);
          // 更新账户
          insert += accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                  .eq("uid", publisherId)
                  .set("available_balance", subtractPointBalance)
                  .set("frozen_balance",addFrozenBalance));
          insert += tradinPostMapper.insert(new TradinPost(tradinId,publisherId,typeId,content,pointBalance,piBalance,piAddress));
          if (insert < 2){
              throw new BusinessException("插入失败");
          }
        }
        return true;
    }


    //批量发布
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchPublishTradition(String token, int total) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String publisherId = dataFromToken.get("publisherId", String.class);
        Integer typeId = dataFromToken.get("typeId", Integer.class);
        String content = dataFromToken.get("content", String.class);
        BigDecimal pointBalance = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("pointBalance", String.class)));
        BigDecimal piBalance = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("piBalance", String.class)));
        String tradinId = StringUtil.generateShortId();
        int insert = 0;
        // 发布者换积分
        if (typeId == 0){
            insert += tradinPostMapper.insert(new TradinPost(tradinId, publisherId, typeId, content, pointBalance, piBalance,null));
            if (insert < 1){
                throw new BusinessException("插入失败失败");
            }
            // 发布者换pi币
        } else if (typeId == 1) {
            // 获取发布者账户
            AccountInfoVO accountInfoVO = accountInfoMapper.selectAccountInfo(publisherId);
            if (accountInfoVO.getAvailableBalance().compareTo(pointBalance) < 0) {
                throw new BusinessException("账户可用余额不足");
            }

            // 计算积分、冻结积分
            BigDecimal subtractPointBalance = accountInfoVO.getAvailableBalance().subtract(pointBalance);
            BigDecimal addFrozenBalance = accountInfoVO.getFrozenBalance().add(pointBalance);

            String piAddress = dataFromToken.get("piAddress", String.class);
            // 更新账户
            insert += accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                    .eq("uid", publisherId)
                    .set("available_balance", subtractPointBalance)
                    .set("frozen_balance",addFrozenBalance));
            List<TradinPost> list = new ArrayList<>();
            for (int i=0;i<total;i++){
                list.add(new TradinPost(tradinId,publisherId,typeId,content,pointBalance,piBalance,piAddress));
            }
            boolean b = this.saveBatch(list);
//            insert += tradinPostMapper.insert(new TradinPost(tradinId,publisherId,typeId,content,pointBalance,piBalance,piAddress));
            if (insert < 1|| b==false){
                throw new BusinessException("插入失败");
            }
        }
        return true;
    }



    @Override
    public TraditionDetailVO selectTraditionDetail(String tradinId) throws InterruptedException {
        // 获取锁（可重入），指定锁的名称
//        RLock lock = redissonClient.getLock(tradinId);
        // 尝试获取锁，参数分别是：获取锁的最大等待时间（期间会重试），锁自动释放时间，时间单位
//        boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);
        TraditionDetailVO traditionDetailVO ;
        // 判断释放获取成功
//        if(isLock){
//            try {
                //执行业务需求
//                TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>()
//                                                                    .eq("tradin_id", tradinId)
//                                                                    .select("status","tradin_id","id"));
//                // 判断是否有人查看
//                if (tradinPost.getStatus() != 0){
//                    // 有人在查看
//                    return null;
//                }
                // 上锁让其他人不能访问 更新状态，
//                tradinPost.setStatus(1);
//                int updateById = tradinPostMapper.updateById(tradinPost);
//                if (updateById < 1){
//                    throw new BusinessException("更新状态失败");
//                }
                // 返回数据
                traditionDetailVO = tradinPostMapper.selectTraditionDetail(tradinId);
                traditionDetailVO.setUserImage(imageMapper.selectPath(traditionDetailVO.getUserImage()));
//            }finally {
//                // 释放锁
//                lock.unlock();
//            }
//        }
        return  traditionDetailVO;
    }




    @Override
    public List<TraditionListVO> selectTradinPostByUid(String token) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String userId = dataFromToken.get("userId", String.class);
        List<TraditionListVO>  traditionListVOS=tradinPostMapper.selectTradinPostByUid(userId);
        for (TraditionListVO traditionVO : traditionListVOS) {
            traditionVO.setUserImage(imageMapper.selectPath(traditionVO.getUserImage()));
        }
        return traditionListVOS;
    }

    @Override
    public PageDataVO selectTraditionListByPiName(Integer typeId, Integer page, Integer limit, String piName) {

        Integer count = tradinPostMapper.selectCountTraditionList(typeId, piName);
        List<TraditionListVO> traditionVOS = tradinPostMapper.selectTraditionListByPiName(typeId,(page-1)*limit,limit, piName);
        for (TraditionListVO traditionVO : traditionVOS) {
            traditionVO.setUserImage(imageMapper.selectPath(traditionVO.getUserImage()));
        }
        return new PageDataVO(count,traditionVOS);
    }

    //范围选择
    @Override
    public PageDataVO selectTraditionScopeListByPiName(Integer typeId, Integer page, Integer limit,
                                                       String piName,String start,String end) {

        Integer count = tradinPostMapper.selectCountTraditionScopeList(typeId, piName,start,end);
        List<TraditionListVO> traditionVOS = tradinPostMapper.selectTraditionScopeListByPiName(typeId,
                (page-1)*limit,limit, piName,start,end);
        for (TraditionListVO traditionVO : traditionVOS) {
            traditionVO.setUserImage(imageMapper.selectPath(traditionVO.getUserImage()));
        }
        return new PageDataVO(count,traditionVOS);
    }

    @Override
    @Transactional
    public void cancelTradition(String tradinId, String piName) {
        // 查看交易状态
        TradinPost tradition = getOne(new QueryWrapper<TradinPost>()
                .eq("tradin_id", tradinId));
        if (tradition.getStatus() == 4) {
            throw new BusinessException("交易已取消");
        }
        if (tradition.getStatus() == 3) {
            throw new BusinessException("交易已完成");
        }
        if (tradition.getStatus() == 2) {
            throw new BusinessException("交易进行中");
        }
        // 修改交易状态
        update(null, new UpdateWrapper<TradinPost>()
                .eq("tradin_id",tradinId)
                .set("status",4));

        if (tradition.getTypeId() == 1) {
            // 积分换pi币返还积分
            accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                    .eq("uid", userInfoService.getOne(new QueryWrapper<UserInfo>()
                            .eq("pi_name", piName)).getUid())
                    .setSql("available_balance = available_balance + "+ tradition.getPointBalance())
                    .setSql("frozen_balance = frozen_balance - "+tradition.getPointBalance()));
        }
    }
}
