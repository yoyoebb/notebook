package com.ebb.jdbc;

import com.ebb.util.LoggerUtils;

import java.sql.*;
import java.util.Objects;

/**
 * 1. 最基本的JDBC流程
 *    1.1 注册Driver
 *    1.2 获取连接
 *    1.3 执行SQL
 *    1.4 处理结果
 * 2. Statement的特点及用法
 * 3. Prepared Statement的特点及用法
 * 4. compare normal mode, batch mode, prepared mode三种模式下的大量DML语句同时执行的效率
 * 5. Callable Statement的特点及用法
 *
 * 用完Connection、Statement、ResultSet时，需要及时将它们关闭，因为它们都是大对象，在数据库会占用很多资源。
 * 它们都有close方法，而且Connection.close()会自动关闭它的所有statement，Statment.close会自动关闭它打开的ResultSet。
 * 从Java SE 7开始，可以利用 try-with-resources 语句来简化编码：
 *
 * 1个Connection可以打开多个Statement( DatabaseMetaData.getMaxStatement() 返回最多可以同时打开的Statement数量)
 * 但1个Statement只能同时打开1个ResultSet
 */
public class ShowJDBC {
    /**
     *  1.1 注册Driver
     *  根据数据库的类型，注册JDBC驱动到方法区，有两种方法：
     *  a. 硬编码 Class.forName(dirverName)    (不推荐)
     *  b. 设置系统参数 jdbc.driers (支持设置多个驱动，以':'隔开)
     *     b.1 在java命令参数中指定
     *         java -Djdbc.drivers=org.postgresql.Driver
     *     b.2 在程序中通过System类指定
     *         System.setProperty("jdbc.drivers", "org.postgresql.Driver") ;
     */
    public static void registerDriver(String driverName) throws ClassNotFoundException {
        Class.forName(driverName);
    }

    /**
     *  1.2 获取连接
     *  通过DriverManager获取数据库连接，需要提供url, username和password
     *  Connection 是很宝贵的资源，用完要记得及时释放。(现实中一般都是通过连接池管理，不需要手工释放)
     */
    public static Connection getConnection(String url, String userName, String password) throws SQLException {
        return DriverManager.getConnection(url,userName,password);
    }


    /**
     *  2. Statement的特点和用法
     *  Statement 可以执行DDL、DML(insert/update/delete)、Query，它的执行方法主要分为：
     *      boolean   execute(String sql)        - 通用的执行方法，返回boolean类型
     *      int       executeUpdate(String sql)  - 针对DDL、DML，返回影响的记录行数(DML有效)
     *      ResultSet executeQuery(String sql)   - 针对Query，返回结果集
     *
     *  使用Statement，数据库不会对SQL预编译，所以:
     *      a. 对于模版sql，性能上会有损耗
     *      b. 没有校验机制，如果在SQL中对外部参数做了字符串拼接，无法防止SQL注入攻击
     */
    public void showStatement(Connection conn,
                              String sql){
        try (   //try-with-resource 代码中声明的resource，在try代码块结束后，或者发生异常时，会自动调用
                //取代了以前的finally关闭资源的冗余代码
                Statement stmt = conn.createStatement()
        ){
            LoggerUtils.LOGGER.debug("Connection Object : {}", Objects.toString(conn.toString()));
            LoggerUtils.LOGGER.debug("Statement Object : {}", Objects.toString(stmt.toString()));

            // executeUpdate用于执行insert/update/delete语句，result为sql影响的行数
            // create/truncate/drop等ddl语句不会返回行数
            int result = stmt.executeUpdate(sql);

            LoggerUtils.LOGGER.debug("sql:[{}]。 result:[{} rows changed]", sql, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *  3. PreparedStatement的特点和用法
     *  PreparedStatement 同样可以执行DDL、DML(insert/update/delete)、Query
     *
     *  通过Connection 对模版sql(host variables以'?'的形式站位)进行预编译，并返回PreparedStatement
     *      PreparedStatement pstmt = conn.preparedStatement(String sql);
     *
     *  执行前需要对预定义参数设置，接口中定义了大量的setXXX()方法
     *      pstmt.setType(index, value);     // index从1开始
     *
     *  执行方法类似于Statement,但无需sql参数
     *      boolean   execute()
     *      int       executeUpdate()
     *      ResultSet executeQuery()
     *
     *  预编译实际发生在database端，所以是否支持还要看driver&database的默认支持情况
     */
    public void showPreparedStatement(Connection conn,
                              String sql){
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            LoggerUtils.LOGGER.debug("Connection Object : {}", Objects.toString(conn.toString()));
            LoggerUtils.LOGGER.debug("Statement Object : {}", Objects.toString(pstmt.toString()));

            // 替换参数, index从1开始
            pstmt.setInt(1,1);
            pstmt.setString(2,"name1");

            int result = pstmt.executeUpdate();

            LoggerUtils.LOGGER.debug("sql:[{}]。 result:[{} rows changed]", sql, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 4. 比较 normal, batch, prepared statement功能 for 大量insert/update/delete语句
     * 根据结果:
     *    1. batch mode 的速度最快 （347 ms)
     *    2. 当sql结构相同只是参数不同时，prepared statement比 normal要快 (1374 ms vs 2139 ms)
     *       但根据prepared statement的特点，如果这些SQL结构差异较大时，两者应该差不多，normal可能更快 (这种场景一般很少出现)
     */
    public void compare_Normal_Batch_Prepared(Connection conn){
        // Normal mode
        try (
                Statement stmt = conn.createStatement();
        ){

            long start = System.currentTimeMillis();

            for(int i=0; i < 10000; i++){
                StringBuffer sb = new StringBuffer("insert into test(id,name) values(").
                                  append(i).append(",'name").append(i).append("')");
                stmt.executeUpdate(sb.toString());
            }

            LoggerUtils.LOGGER.debug("Normal Mode : total time: {}", System.currentTimeMillis() - start);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Batch mode
        try(
            Statement stmt = conn.createStatement()
        ){
            long start = System.currentTimeMillis();

            for(int i=10000; i < 20000; i++){
                StringBuffer sb = new StringBuffer("insert into test(id,name) values(").
                        append(i).append(",'name").append(i).append("')");
                stmt.addBatch(sb.toString());
            }
            stmt.executeBatch();

            LoggerUtils.LOGGER.debug("Batch Mode : total time: {}", System.currentTimeMillis() - start);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Prepared mode, 另外Prepared Statement同样支持对参数batch后一次执行
        try(PreparedStatement pstmt = conn.prepareCall("insert into test(id,name) values(?,?)")
        ){
            long start = System.currentTimeMillis();

            for (int i=20000; i<30000; i++){
                pstmt.setInt(1,i);
                pstmt.setString(2,"name"+i);
                pstmt.executeUpdate();
            }

            LoggerUtils.LOGGER.debug("Prepared Mode : end time: {}", System.currentTimeMillis() - start);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
