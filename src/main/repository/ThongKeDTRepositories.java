/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.repository;

import main.config.DBConnect;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.entity.ThongKe;

/**
 *
 * @author Admin
 */
public class ThongKeDTRepositories {

    public ArrayList<ThongKe> getAll() {
        ArrayList<ThongKe> list = new ArrayList<>();
        String sql = """
                     		SELECT 
                         HoaDon.MaHoaDon AS MaHD,
                         NhanVien.MaNhanVien AS MaNV,
                         NhanVien.HoTen AS TenNV,
                         HoaDon.NgayThanhToan AS NgayThanhToan,
                         HoaDon.TongTien AS TongTien
                     FROM 
                         HoaDon
                     JOIN 
                         NhanVien ON HoaDon.id_NhanVien = NhanVien.id_NhanVien;
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps =con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                ThongKe tk = ThongKe.builder()
                        .maHD(rs.getString(1))
                        .maNV(rs.getString(2))
                        .tenNV(rs.getString(3))
                        .ngayTT(rs.getDate(4))
                        .tongTien(rs.getFloat(5))
                        .build();
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
