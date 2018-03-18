package com.github.bh.aconf.mapper;

import com.github.bh.aconf.domain.DragonSuggestStaffDto;
import com.github.bh.aconf.domain.vo.DragonSuggestStaffVo;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/2/22
 * Time: 18:12
 */
@Mapper
public abstract class DragonSuggestMapper {
    public static final DragonSuggestMapper INSTANCE = Mappers.getMapper(DragonSuggestMapper.class);

    @Mappings({
            @Mapping(target = "passport", source = "username"),
            @Mapping(target = "realName", source = "nickname")
    })
    public abstract DragonSuggestStaffVo getVo(DragonSuggestStaffDto dto);

    public List<DragonSuggestStaffVo> getVoList(List<DragonSuggestStaffDto> dtoList) {
        if (dtoList == null) {
            return null;
        }
        List<DragonSuggestStaffVo> list = new ArrayList<DragonSuggestStaffVo>();
        for (DragonSuggestStaffDto dto : dtoList) {
            if (StringUtils.isNotBlank(dto.getUsername()) && StringUtils.isNotBlank(dto.getNickname())) {
                list.add(getVo(dto));
            }
        }
        return list;
    }
}
