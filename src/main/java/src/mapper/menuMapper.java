package src.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import src.pojo.Menu;

import java.util.List;

public interface menuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}
