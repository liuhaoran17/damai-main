package com.damai.service;

import com.baidu.fsg.uid.UidGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.damai.core.RedisKeyManage;
import com.damai.dto.ChannelDataAddDto;
import com.damai.dto.GetChannelDataByCodeDto;
import com.damai.entity.ChannelTableData;
import com.damai.enums.Status;
import com.damai.mapper.ChannelDataMapper;
import com.damai.redis.RedisCache;
import com.damai.redis.RedisKeyBuild;
import com.damai.util.DateUtils;
import com.damai.vo.GetChannelDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
public class ChannelDataService {
    
    @Autowired
    private ChannelDataMapper channelDataMapper;
    
    @Autowired
    private UidGenerator uidGenerator;
    
    @Autowired
    private RedisCache redisCache; 
    
    public GetChannelDataVo getByCode(GetChannelDataByCodeDto dto){
        GetChannelDataVo getChannelDataVo = new GetChannelDataVo();
        LambdaQueryWrapper<ChannelTableData> wrapper = Wrappers.lambdaQuery(ChannelTableData.class)
                .eq(ChannelTableData::getStatus, Status.RUN.getCode())
                .eq(ChannelTableData::getCode,dto.getCode());
        Optional.ofNullable(channelDataMapper.selectOne(wrapper)).ifPresent(channelData -> {
            BeanUtils.copyProperties(channelData,getChannelDataVo);
        });
        return getChannelDataVo;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void add(ChannelDataAddDto channelDataAddDto) {
        ChannelTableData channelData = new ChannelTableData();
        BeanUtils.copyProperties(channelDataAddDto,channelData);
        channelData.setId(uidGenerator.getUid());
        channelData.setCreateTime(DateUtils.now());
        channelDataMapper.insert(channelData);
        addRedisChannelData(channelData);
    }
    
    private void addRedisChannelData(ChannelTableData channelData){
        GetChannelDataVo getChannelDataVo = new GetChannelDataVo();
        BeanUtils.copyProperties(channelData,getChannelDataVo);
        redisCache.set(RedisKeyBuild.createRedisKey(RedisKeyManage.CHANNEL_DATA,getChannelDataVo.getCode()),getChannelDataVo);
    }
}
