package com.changgou.util;

/**
 * @Company: CUG
 * @Description: 实现FastDFS文件上传管理
 * @Author: LiYangyong
 * @Date: 2021/2/17/017 14:41
 **/

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 实现FastDFS文件上传管理
 * 文件上传
 * 文件删除
 * 文件下载
 * 文件信息获取
 * Storage信息获取
 * Tracker信息获取
 */
public class FastDFSUtil {

    /**
     * 加载Tracker链接信息
     */

    static {
        try {
            // 查找classpath下的文件路径
            String fileName = new ClassPathResource("fdfs_client.conf").getPath();
            // 加载Tracker链接信息
            ClientGlobal.init(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件上传
     * @param fastDFSFile : 上传的文件信息封装
     * @return
     */

    public static String[] upload(FastDFSFile fastDFSFile) throws Exception{
        // 附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", fastDFSFile.getAuthor());

        // 创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();

        // 通过TrackerClient访问TrackerServer服务，获取链接信息
        TrackerServer trackerServer = trackerClient.getConnection();

        // 通过TrackerServer的链接信息可以获取Storage的链接信息，创建StorageClient对象存储Storage的链接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);

        // 通过StorageClient访问Storage，实现文件上传，并且获取文件上传后的存储信息
        /**
         * 1.上传文件字节数组
         * 2. 文件扩展名
         * 3. 附加数据  比如：拍摄地址：黑龙江
         *
         * uploads[]
         *      uploads[0]:文件上传所储存的Storage 的组名字 group1
         *      uploads[1]:文件存储到Storage上的文件名字   M00/02/44/itheima.jpg
         */

        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return uploads;
    }


    /**
     * 获取文件信息
     * @param groupName: 文件组名
     * @param remoteFileName: 文件的存储路径的名字    M00/00/00/wKjThGAvv4iAOOr7AAEmqkotx98761.jpg
     */

    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception{

        // 获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        // 通过TrackerServer获取Storage信息，创建StorageClient对象存储Storage信息
        StorageClient storageClient = new StorageClient(trackerServer, null);

        // 获取文件信息
        return storageClient.get_file_info(groupName, remoteFileName);

    }

    /**
     * 文件下载
     * @param groupName: 文件组名
     * @param remoteFileName: 文件的存储路径的名字    M00/00/00/wKjThGAvv4iAOOr7AAEmqkotx98761.jpg
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();

        // 通过TrackerClient对象访问TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        // 通过TrackerServer获取Storage信息，创建StorageClient对象存储Storage信息
        StorageClient storageClient = new StorageClient(trackerServer, null);

        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }


    /**
     * 删除文件
     * @param groupName: 文件组名
     * @param remoteFileName: 文件的存储路径的名字    M00/00/00/wKjThGAvv4iAOOr7AAEmqkotx98761.jpg
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();

        // 通过TrackerClient对象访问TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        // 通过TrackerServer获取Storage信息，创建StorageClient对象存储Storage信息
        StorageClient storageClient = new StorageClient(trackerServer, null);

        // 删除文件
        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 互获取Storage信息
     */
    public static StorageServer getStorage() throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();

        // 通过TrackerClient对象访问TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        // 获取Storage信息
        return trackerClient.getStoreStorage(trackerServer);

    }

    /**
     * 获取Storage的IP和端口信息
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();

        // 通过TrackerClient对象访问TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //获取Storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }


    public static String getTrackerInfo() throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();

        // 通过TrackerClient对象访问TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        // Tracker的IP，HTTP端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int tracker_http_port = ClientGlobal.getG_tracker_http_port(); // 8080
        String url = "http://" + ip + ":" + tracker_http_port;
        return url;
    }


    /**
     * 获取Tracker
     * @return
     * @throws Exception
     */
    public static TrackerServer getTrackerServer() throws Exception{
        // 创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();

        // 通过TrackerClient对象访问TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    public static void main(String[] args) throws Exception{
        /*FileInfo fileInfo = getFile("group1", "M00/00/00/wKjThGAvv4iAOOr7AAEmqkotx98761.jpg");
        System.out.println(fileInfo.getSourceIpAddr());
        System.out.println(fileInfo.getFileSize());*/

        // 文件下载
        /*InputStream is = downloadFile("group1", "M00/00/00/wKjThGAvv4iAOOr7AAEmqkotx98761.jpg");

        // 将文件写入到本地磁盘
        FileOutputStream os = new FileOutputStream("H:/os.jpg");

        // 定义一个缓冲区
        byte[] buffer = new byte[1024];
        while (is.read(buffer) != 1) {
            os.write(buffer);
        }
        os.flush();
        os.close();
        is.close();*/


        // 文件删除
//        deleteFile("group1", "M00/00/00/wKjThGAvv4iAOOr7AAEmqkotx98761.jpg");

        // 获取Storage信息
        /*StorageServer storageServer = getStorage();
        System.out.println(storageServer.getStorePathIndex());
        System.out.println(storageServer.getInetSocketAddress().getHostString());*/


        // 获取Storage组的IP和端口信息
       /* ServerInfo[] groups = getServerInfo("group1", "M00/00/00/wKjThGAvyTCAQhAaAAEmqkotx98531.jpg");
        for (ServerInfo group : groups) {
            System.out.println(group.getIpAddr());
            System.out.println(group.getPort());
        }*/


        // 获取Tracker的信息
        System.out.println(getTrackerInfo());
    }


}
