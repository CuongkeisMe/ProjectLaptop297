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
public class ThongKeSPRepositories {
    public ArrayList<ThongKe> getAll(){
        ArrayList<ThongKe> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         SanPham.MaSanPham AS 'MaSP',
                         SanPham.TenSanPham AS 'TenSP',
                         SanPham.GiaBan AS 'GiaSP',
                         SanPham.SoLuong AS 'TongSoLuongSP',
                         ISNULL(SUM(HoaDonChiTiet.SoLuong), 0) AS 'SoSPDaBan',
                         ISNULL(SUM(HoaDonChiTiet.TongTien), 0) AS 'DoanhThu'
                     FROM 
                         SanPham
                     LEFT JOIN 
                         HoaDonChiTiet ON SanPham.id_SanPham = HoaDonChiTiet.id_SanPham
                     GROUP BY 
                         SanPham.MaSanPham, SanPham.TenSanPham, SanPham.GiaBan, SanPham.SoLuong;
                     """;
        try(Connection con = DBConnect.getConnection();PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            ThongKe tk = ThongKe.builder()
                    .maSP(rs.getString(1))
                    .tenSP(rs.getString(2))
                    .giaSP(rs.getFloat(3))
                    .tongSP(rs.getInt(4))
                    .daBan(rs.getInt(5))
                    .doanhThu(rs.getFloat(6))
                    .build();
            list.add(tk);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
