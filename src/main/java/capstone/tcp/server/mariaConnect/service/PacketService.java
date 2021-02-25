package capstone.tcp.server.mariaConnect.service;

import capstone.tcp.server.mariaConnect.domain.PacketDTO;
import capstone.tcp.server.mariaConnect.mapper.PacketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import java.util.List;


public interface PacketService {

    public boolean registerPacket(PacketDTO params);

    //public PacketDTO getPacketDetail(Long id);

    //public List<PacketDTO> getBoardList();

}
