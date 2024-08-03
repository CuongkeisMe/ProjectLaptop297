package main.repository;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.config.DBConnect;
import main.response.HoaDonChiTietResponse;


public class HoaDonChiTietRepository {
    public ArrayList<HoaDonChiTietResponse> getAll(Integer idHoaDon){
        ArrayList<HoaDonChiTietResponse> listHDCT = new ArrayList<>();
        String sql = """
                     SELECT dbo.HoaDonChiTiet.id_HDCT, dbo.HoaDon.id_HoaDon, dbo.SanPham.id_SanPham, dbo.SanPham.MaSanPham, dbo.SanPham.TenSanPham, dbo.HoaDonChiTiet.SoLuong, dbo.HoaDonChiTiet.DonGia, dbo.HoaDonChiTiet.TongTien
                     FROM   dbo.HoaDon INNER JOIN
                                  dbo.HoaDonChiTiet ON dbo.HoaDon.id_HoaDon = dbo.HoaDonChiTiet.id_HoaDon INNER JOIN
                                  dbo.SanPham ON dbo.HoaDonChiTiet.id_SanPham = dbo.SanPham.id_SanPham
                     WHERE dbo.HoaDon.id_HoaDon = ?
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idHoaDon);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                HoaDonChiTietResponse hdctResponse = HoaDonChiTietResponse.builder()
                        .idHoaDonChiTiet(rs.getInt(1))
                        .idHoaDon(rs.getInt(2))
                        .idSanPham(rs.getInt(3))
                        .maSanPham(rs.getString(4))
                        .tenSanPham(rs.getString(5))
                        .soLuong(rs.getInt(6))
                        .giaBan(rs.getFloat(7))
                        .tongTien(rs.getFloat(8))
                        .build();
                listHDCT.add(hdctResponse);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return listHDCT;
    }
    
    public Boolean addImeiDaBan(Integer idHDCT, String maImeiDaBan){
        String sql = """
                     INSERT INTO [dbo].[ImeiDaBan]
                                ([id_HDCT]
                                ,[Ma_Imei])
                          VALUES
                                (?,?)
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idHDCT);
            ps.setObject(2, maImeiDaBan);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    
}
