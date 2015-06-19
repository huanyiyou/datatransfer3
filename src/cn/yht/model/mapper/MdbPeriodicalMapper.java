package cn.yht.model.mapper;

import cn.yht.model.MdbPeriodical;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YHT on 2015/6/18.
 */
@Repository
public interface MdbPeriodicalMapper {
    List<MdbPeriodical> getPeriodicalByMdb(String periodicalName);
}
