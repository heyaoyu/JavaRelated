package org.heyaoyu.tutorials;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

public class HadoopFileSystemSample {

    public static void main(String[] args) throws Exception {
        String uri = "hdfs://localhost:9000/";
        Configuration config = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), config);

        // 列出hdfs上/user/fkong/目录下的所有文件和目录
        FileStatus[] statuses = fs.listStatus(new Path("/"));
        for (FileStatus status : statuses) {
            System.out.println(status);
        }
    }

}
