package com.zxh.dao.daoimpl;

import com.zxh.dao.FileDao;
import com.zxh.pojo.IFile;
import com.zxh.service.serviceimpl.FileServiceimpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository(value = "filedao")
public class FileDaoimpl implements FileDao {



    @Override
    public void SelectfileDao(IFile ifile) {
        Connection conn =null;
        PreparedStatement p = null;
        int flag=-1;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/file?useUnicode=true&characterEncoding=utf8","root","root");
            //INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)

            String sql ="insert into fileinformation (uuid,size,type,filename,time,address) values(?,?,?,?,?,?)";
            p = conn.prepareStatement(sql);
            p.setString(1, ifile.getUuid());
            p.setString(2, ifile.getSize());
            p.setString(3, ifile.getType());
            p.setString(4, ifile.getFilename());
            p.setString(5, ifile.getTime());
            p.setString(6, ifile.getAddress());
            flag= p.executeUpdate();


        } catch (Exception e) {

            e.printStackTrace();
        }
        finally{
            try {
                p.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }


        }




    }
}
