package capstone.tcp.server.mariaConnect.service;

import java.util.Collections;
import java.util.List;

import capstone.tcp.server.mariaConnect.domain.PacketDTO;
import capstone.tcp.server.mariaConnect.mapper.PacketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PacketServiceImpl implements PacketService {

    @Autowired
    private PacketMapper packetMapper;

    @Override
    public boolean registerPacket(PacketDTO params) {
        int queryResult = 0;


        queryResult = packetMapper.insertPacket(params);
        return (queryResult == 1) ? true : false;
    }

//    @Override
//    public PacketDTO getPacketDetail(Long idx) {
//        return boardMapper.selectBoardDetail(idx);
//    }
//
//

//    @Override
//    public List<BoardDTO> getBoardList() {
//        List<BoardDTO> boardList = Collections.emptyList();
//
//        int boardTotalCount = boardMapper.selectBoardTotalCount();
//
//        if (boardTotalCount > 0) {
//            boardList = boardMapper.selectBoardList();
//        }
//
//        return boardList;
//    }

}