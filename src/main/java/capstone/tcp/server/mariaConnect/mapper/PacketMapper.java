package capstone.tcp.server.mariaConnect.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import capstone.tcp.server.mariaConnect.domain.PacketDTO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PacketMapper {

    public int insertPacket(PacketDTO params);

    // 이후 필요시 제작
    public PacketDTO selectPacketDetail(Long id);

    public List<PacketDTO> selectBoardList();

    public int selectPacketTotalCount();

}