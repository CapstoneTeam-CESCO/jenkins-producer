package capstone.tcp.server.service;

import capstone.tcp.server.domain.SPU;

public interface MessageService {


    /**
     * parseing SPU-header
     * 
     * @param dst byte[]
     * @return
     * @throws Exception
     */
    public SPU getHeader(byte[] dst) throws Exception;

    /**
     * run
     * 
     * @param dst byte[]
     * @param command
     * @throws Exception
     */
    public void run(byte[] dst, SPU command) throws Exception;
    
}
