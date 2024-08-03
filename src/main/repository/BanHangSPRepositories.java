package main.repository;

import java.util.ArrayList;
import main.config.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import main.response.BanHangResponse;

public class BanHangSPRepositories {
    public ArrayList<BanHangResponse> getAll() {
        ArrayList<BanHangResponse> listSP = new ArrayList<>();
        String sql = """
                     SELECT dbo.SanPham.id_SanPham, dbo.SanPham.MaSanPham, dbo.SanPham.TenSanPham,  
                            dbo.CPU.TenCPU, dbo.GPU.TenGPU, dbo.OCung.LoaiOCung, dbo.Ram.DungLuongRam, 
                            dbo.ManHinh.KichThuoc, dbo.Pin.DungLuongPin, dbo.SanPham.SoLuong, 
                            dbo.SanPham.GiaBan, dbo.SanPham.TrangThai
                     FROM dbo.CPU 
                     INNER JOIN dbo.SanPham ON dbo.CPU.id_CPU = dbo.SanPham.id_CPU 
                     INNER JOIN dbo.GPU ON dbo.SanPham.id_GPU = dbo.GPU.id_GPU 
                     INNER JOIN dbo.ManHinh ON dbo.SanPham.id_ManHinh = dbo.ManHinh.id_ManHinh 
                     INNER JOIN dbo.OCung ON dbo.SanPham.id_OCung = dbo.OCung.id_OCung 
                     INNER JOIN dbo.Pin ON dbo.SanPham.id_Pin = dbo.Pin.id_Pin 
                     INNER JOIN dbo.Ram ON dbo.SanPham.id_Ram = dbo.Ram.id_Ram
                     WHERE dbo.SanPham.TrangThai = 1
                     ORDER BY dbo.SanPham.id_SanPham DESC
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BanHangResponse bhResponse = BanHangResponse.builder()
                        .idSanPham(rs.getInt(1))
                        .maSanPham(rs.getString(2))
                        .tenSanPham(rs.getString(3))
                        .tenCPU(rs.getString(4))
                        .tenGPU(rs.getString(5))
                        .loaiOCung(rs.getString(6))
                        .dungluongRam(rs.getString(7))
                        .kichThuoc(rs.getString(8))
                        .dungluongPin(rs.getString(9))
                        .soLuong(rs.getInt(10))
                        .giaBan(rs.getFloat(11))
                        .build();
                listSP.add(bhResponse);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return listSP;
    }
    
    public int getSoLuongByMa(String maSP) {
        int soLuong = 0;
        String sql = """
            SELECT COUNT(Imei.Ma_Imei) 
            FROM dbo.SanPham 
            INNER JOIN dbo.Imei ON dbo.Imei.id_SanPham = dbo.SanPham.id_SanPham
            WHERE dbo.SanPham.MaSanPham = ?
        """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return soLuong;
    }
    
    public Boolean updateQuantity(Integer soLuong, String maSP) {
        String sql = """
                     update SanPham set SoLuong = ?
                     where MaSanPham = ?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, soLuong);
            ps.setObject(2, maSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    
    public Boolean addGioHang(Integer idHoaDon, BanHangResponse bhResponse, int soLuong){
        String sql = """
                     INSERT INTO [dbo].[HoaDonChiTiet]
                                ([id_HoaDon]
                                ,[id_SanPham]
                                ,[SoLuong]
                                ,[DonGia]
                                ,[TongTien]
                                ,[TrangThai])
                          VALUES
                                (?,?,?,?,?,1)
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idHoaDon);
            ps.setObject(2, bhResponse.getIdSanPham());
            ps.setObject(3, soLuong);
            ps.setObject(4, bhResponse.getGiaBan());
            ps.setObject(5, soLuong * bhResponse.getGiaBan());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    
}
