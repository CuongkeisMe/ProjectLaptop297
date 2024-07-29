package main.repository;

import java.util.ArrayList;
import main.entity.BanHang;
import main.config.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.response.SanPhamResponse;

public class BanHangSPRepositories {
    public ArrayList<SanPhamResponse> getAll(){
        ArrayList<SanPhamResponse> listSP = new ArrayList<>();
        String sql = """
                     SELECT dbo.SanPham.id_SanPham, dbo.SanPham.MaSanPham, dbo.SanPham.TenSanPham, dbo.SanPham.HinhAnh, dbo.CPU.TenCPU, dbo.GPU.TenGPU, dbo.OCung.LoaiOCung, dbo.Ram.DungLuongRam, dbo.ManHinh.KichThuoc, dbo.Pin.DungLuongPin, 
                                  dbo.SanPham.SoLuong, dbo.SanPham.GiaNhap, dbo.SanPham.GiaBan, dbo.SanPham.TrangThai
                     FROM   dbo.CPU INNER JOIN
                                  dbo.SanPham ON dbo.CPU.id_CPU = dbo.SanPham.id_CPU INNER JOIN
                                  dbo.GPU ON dbo.SanPham.id_GPU = dbo.GPU.id_GPU INNER JOIN                                  
                                  dbo.ManHinh ON dbo.SanPham.id_ManHinh = dbo.ManHinh.id_ManHinh INNER JOIN
                                  dbo.OCung ON dbo.SanPham.id_OCung = dbo.OCung.id_OCung INNER JOIN
                                  dbo.Pin ON dbo.SanPham.id_Pin = dbo.Pin.id_Pin INNER JOIN
                                  dbo.Ram ON dbo.SanPham.id_Ram = dbo.Ram.id_Ram
                     WHERE dbo.SanPham.TrangThai = 1
                     order by [id_SanPham] desc
                     """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                SanPhamResponse spResponse = SanPhamResponse.builder()
                        .IdSanPham(rs.getInt(1))
                        .MaSanPham(rs.getString(2))
                        .TenSanPham(rs.getString(3))
                        .HinhAnh(rs.getString(4))
                        .TenCPU(rs.getString(5))
                        .TenGPU(rs.getString(6))
                        .LoaiOCung(rs.getString(7))
                        .DungLuongRam(rs.getString(8))
                        .KichThuoc(rs.getString(9))
                        .DungLuongPin(rs.getString(10))
                        .SoLuong(rs.getInt(11))
                        .GiaNhap(rs.getInt(12))
                        .GiaBan(rs.getInt(13))
                        .build();
                listSP.add(spResponse);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return listSP;
    }
    
}
