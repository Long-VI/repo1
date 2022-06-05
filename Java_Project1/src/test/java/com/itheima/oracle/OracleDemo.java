package com.itheima.oracle;

import oracle.jdbc.OracleTypes;
import org.junit.Test;

import java.sql.*;

public class OracleDemo {
    @Test
    public void javaCallOracle() throws ClassNotFoundException, SQLException {
        //加载数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //得到connection链接
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.106.10:1521:orcl", "itheima", "itheima");
        //得到预编译的statement对象
        PreparedStatement pstm = connection.prepareStatement("select * from emp where empno = ?");
        //给参数赋值
        pstm.setObject(1,7782);
        //执行数据库查询操作
        ResultSet rs = pstm.executeQuery();
        //输出结果
        while (rs.next())
        {
            System.out.println(rs.getString("ename"));
        }
        //释放资源
        rs.close();
        pstm.close();
        connection.close();
    }


    /**
     * {?= call <procedure-name>[(<arg1>,<arg2>, ...)]} 调用存储函数使用
     * {call <procedure-name>[(<arg1>,<arg2>, ...)]} 调用存储过程使用
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void javaCallProcedure() throws ClassNotFoundException, SQLException {
        //加载数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");

        //得到connection链接
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.106.10:1521:orcl", "itheima", "itheima");

        //得到预编译的statement对象
        //PreparedStatement pstm = connection.prepareStatement("select * from emp where empno = ?");
        CallableStatement pstm = connection.prepareCall("{call P_YEARSAL(?,?)}");

        //给参数赋值
        pstm.setObject(1,"7782");
        pstm.registerOutParameter(2, OracleTypes.NUMBER);

        //执行数据库查询语句
        pstm.execute();

        //输出结果
        System.out.println(pstm.getObject(2));
       // while(rs.next())
      //  {
       //     System.out.println(rs.getString("ename"));
       // }

        //释放资源
        //rs.close();
        pstm.close();
        connection.close();
    }

    /**
     * {?= call <procedure-name>[(<arg1>,<arg2>, ...)]} 调用存储函数使用
     * {call <procedure-name>[(<arg1>,<arg2>, ...)]} 调用存储过程使用
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void javaCallFunction() throws ClassNotFoundException, SQLException {
        //加载数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");

        //得到connection链接
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.106.10:1521:orcl", "itheima", "itheima");

        //得到预编译的statement对象
        //PreparedStatement pstm = connection.prepareStatement("select * from emp where empno = ?");
        CallableStatement pstm = connection.prepareCall("{? = call F_YEARSAL(?)}");

        //给参数赋值
        pstm.setObject(2,"7782");
        pstm.registerOutParameter(1, OracleTypes.NUMBER);

        //执行数据库查询语句
        pstm.execute();

        //输出结果
        System.out.println(pstm.getObject(1));
        System.out.println("PL/SQL Function successfully!");
        // while(rs.next())
        //  {
        //     System.out.println(rs.getString("ename"));
        // }

        //释放资源
        //rs.close();
        pstm.close();
        connection.close();
    }
}
