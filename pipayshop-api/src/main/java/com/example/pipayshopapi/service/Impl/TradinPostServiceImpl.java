package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.TradinJournal;
import com.example.pipayshopapi.entity.TradinPost;
import com.example.pipayshopapi.entity.dto.TradinPostDTO;
import com.example.pipayshopapi.entity.dto.TransactionDTO;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.entity.vo.TraditionDetailVO;
import com.example.pipayshopapi.entity.vo.TraditionListVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ImageMapper;
import com.example.pipayshopapi.mapper.TradinJournalMapper;
import com.example.pipayshopapi.mapper.TradinPostMapper;
import com.example.pipayshopapi.service.TradinPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.StringUtil;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private TradinJournalMapper tradinJournalMapper;

    @Resource
    private RedissonClient redissonClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int publishTradition(String token) {
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

            String journalId=StringUtil.generateShortId();
            // 日志属性填充
            TradinJournal tradinJournal = new TradinJournal(journalId,publisherId,typeId,pointBalance,piBalance);
            // 插入数据
            insert += tradinJournalMapper.insert(tradinJournal);
            insert += tradinPostMapper.insert(new TradinPost(tradinId, publisherId, typeId, content, pointBalance, piBalance,journalId));
          if (insert < 2){
              throw new BusinessException("插入失败失败");
          }
          // 发布者换pi币
        } else if (typeId == 1) {
          // 获取发布者账户
          AccountInfoVO accountInfoVO = accountInfoMapper.selectAccountInfo(publisherId);
          if (accountInfoVO.getPointBalance().compareTo(pointBalance) < 0) {
              throw new BusinessException("账户余额不足");
          }
          // 计算积分
          BigDecimal oldPointBalance = accountInfoVO.getPointBalance();
          BigDecimal newPointBalance = oldPointBalance.divide(pointBalance);
          String journalId=StringUtil.generateShortId();
          String piAddress = dataFromToken.get("piAddress", String.class);
          // 日志属性填充
          TradinJournal tradinJournal = new TradinJournal(journalId,publisherId,typeId,pointBalance,piBalance,oldPointBalance,newPointBalance);
          insert +=tradinJournalMapper.insert(tradinJournal);
          insert += accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>().eq("uid", publisherId).set("point_balance", newPointBalance));
          insert += tradinPostMapper.insert(new TradinPost(tradinId,publisherId,typeId,content,pointBalance,piBalance,piAddress,journalId));
          if (insert < 3){
              throw new BusinessException("插入失败");
          }
        }
        return insert;
    }

    @Override
    public List<TraditionListVO> selectTraditionList(Integer typeId) {
        List<TraditionListVO> traditionVOS = tradinPostMapper.selectTraditionList(typeId);
        for (TraditionListVO traditionVO : traditionVOS) {
            traditionVO.setUserImage(imageMapper.selectPath(traditionVO.getUserImage()));
        }
        return traditionVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TraditionDetailVO selectTraditionDetail(String tradinId) throws InterruptedException {

        // 获取锁（可重入），指定锁的名称
        RLock lock = redissonClient.getLock(tradinId);
        // 尝试获取锁，参数分别是：获取锁的最大等待时间（期间会重试），锁自动释放时间，时间单位
        boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);
        TraditionDetailVO traditionDetailVO = null;
        // 判断释放获取成功
        if(isLock){
            try {
                //执行业务需求
                TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>()
                                                                    .eq("tradin_id", tradinId)
                                                                    .select("status","tradin_id","id"));
                // 判断是否有人查看
                if (tradinPost.getStatus() != 0){
                    // 有人在查看
                    return null;
                }
                // 上锁让其他人不能访问 更新状态，
                tradinPost.setStatus(1);
                int updateById = tradinPostMapper.updateById(tradinPost);
                if (updateById < 1){
                    throw new BusinessException("更新状态失败");
                }
                // 返回数据
                traditionDetailVO = tradinPostMapper.selectTraditionDetail(tradinId);
                traditionDetailVO.setUserImage(imageMapper.selectPath(traditionDetailVO.getUserImage()));
            }finally {
                // 释放锁
                lock.unlock();
            }
        }
        return  traditionDetailVO;
    }

    @Override
    public void updateStatusByTradinId(String tradinId) {
        TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>()
                .eq("tradin_id", tradinId)
                .select("status","tradin_id","id"));
        if (tradinPost.getStatus() == 1){
            tradinPost.setStatus(0);
            tradinPostMapper.updateById(tradinPost);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upLoadImg(MultipartFile file, TradinPostDTO tradinPostDTO) {
        if (file.isEmpty()) {
            throw new BusinessException("文件不能为空") ;
        }
        String fileName = file.getOriginalFilename();
        if ("".equals(fileName)) {
            throw new BusinessException("文件名不能为空") ;
        }
        String separator = File.separator;
        String postStr = fileName.substring(fileName.lastIndexOf("."));
        String preStr = StringUtil.generateShortId();
        fileName = preStr +  postStr;

        File readPath = new File(Constants.CERTIFICATE_IMAG_PATH + separator );
        if (!readPath.isDirectory()) {
            readPath.mkdirs();
        }
        // 将文件复制到指定路径
        File destFile = new File(readPath.getAbsolutePath()+ separator + fileName);
        // 此交易发布者需要换pi币
        if (tradinPostDTO.getTypeId() == 1){
            // 查询交易
            TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>().eq("tradin_id", tradinPostDTO.getTradinId()));

            // 更改交易状态为交易中
            tradinPost.setStatus(2);
            tradinPost.setImageUrl("/images/tradin_post_certificate/"+fileName);
            tradinPost.setTraderUid(tradinPostDTO.getTraderUid());
            int update = tradinPostMapper.updateById(tradinPost);
            if (update < 0){
                throw new BusinessException("提交失败") ;
            }
        }
        try {
            FileCopyUtils.copy(file.getBytes(), destFile);
        } catch (IOException e) {
            if (destFile.exists()){
                destFile.delete();
            }
            e.printStackTrace();
        }
        return true;
    }
}
