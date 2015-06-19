package cn.yht.model.mapper;

import cn.yht.model.Periodical;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by YHT on 2015/6/12.
 */
@Repository
public interface PeriodicalMapper {
    List<Periodical> getSamePVRecord(Periodical periodical);

    Periodical getSamePRecord(Periodical periodical);

    long getPVMaxId();

    long getPMaxId();



    void insertPV(Periodical periodical);

    void updatePV(Periodical periodical);

    void insertPeriodical(Periodical periodical);


}
