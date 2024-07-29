package main.repository;

import java.util.ArrayList;
import main.entity.HoaDonEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.config.DBConnect;
import main.entity.HoaDonTro;

/**
 *
 * @author Windows
 */
public class HoaDonRepository {
    public ArrayList<HoaDonEntity> getAll() {
    ArrayList<HoaDonEntity> list = new ArrayList<>();
    try {
        Connection con = DBConnect.getConnection();
        String sql = "	SELECT \n" +
                    "    HoaDon.MaHoaDon as 'MaHoaDon',\n" +
                    "    KhachHang.HoTen as 'kh',\n" +
                    "    Voucher.MaVoucher as 'MaVoucher',\n" +
                    "    NhanVien.HoTen as 'nv',\n" +
                    "    FORMAT(HoaDon.NgayThanhToan, 'dd-MM-yyyy') as 'NgayThanhToan',\n" +
                    "    HoaDon.TongTien as 'TongTien',\n" +
                    "    HoaDon.TienVoucher as 'TienVoucher',\n" +
                    "    HoaDon.ThanhTien as 'ThanhTien',\n" +
                    "    HoaDon.PhuongThucThanhToan as 'PhuongThuc',\n" +
                    "    HoaDon.GhiChu as 'GhiChu'\n" +
                    "FROM HoaDon\n" +
                    "JOIN KhachHang ON KhachHang.id_KhachHang = HoaDon.id_KhachHang\n" +
                    "JOIN Voucher ON Voucher.id_Voucher = HoaDon.id_Voucher\n" +
                    "JOIN NhanVien ON NhanVien.id_NhanVien = HoaDon.id_NhanVien\n" +
                    "WHERE HoaDon.TrangThai = 1;";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            HoaDonEntity hd = new HoaDonEntity();
            hd.setMaHoaDon(rs.getString("MaHoaDon"));
            hd.setTenKhachHang(rs.getString("kh"));
            hd.setMaVoucher(rs.getString("MaVoucher"));
            hd.setTenNhanVien(rs.getString("nv")); // Corrected to retrieve 'nv' from ResultSet
            hd.setNgayThanhToan(rs.getString("NgayThanhToan")); // Retrieve as a string
            hd.setTongTien(rs.getFloat("TongTien"));
            hd.setTienVoucher(rs.getFloat("TienVoucher"));
            hd.setThanhTien(rs.getFloat("ThanhTien"));
            hd.setPhuongThucThanhToan(rs.getString("PhuongThuc"));
            hd.setGhiChu(rs.getString("GhiChu"));
            list.add(hd);
            
        }
    } catch (Exception e) {
        e.printStackTrace(); // Added for debugging purposes
        return null;
    }
        return list;
    }

    public boolean deleteHoaDon(String MaHoaDon) {
        try {
            Connection con = DBConnect.getConnection();
            String sql = "UPDATE HoaDon SET TrangThai = 0 WHERE MaHoaDon = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, MaHoaDon);
            int rowsUpdated = ps.executeUpdate();
            ps.close(); 
            con.close();
            return rowsUpdated > 0; 
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        }
    }
    
    public ArrayList<HoaDonTro> getAllHoaDon() {
        ArrayList<HoaDonTro> list = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String sql = """
                             select MaHoaDon,NgayThanhToan,nv.MaNhanVien,hd.TrangThai
                             from HoaDon hd join NhanVien
                             nv on hd.id_NhanVien=nv.id_NhanVien 
                             
                         """;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTro hd = new HoaDonTro().builder()
                        .maHoaDon(rs.getString(1))
                        .ngayTao(rs.getDate(2))
                        .maNhanVien(rs.getString(3))
                        .tinhTrang(rs.getBoolean(4))
                        .build();

                list.add(hd);

            }
        } catch (Exception e) {
            e.printStackTrace(); // Added for debugging purposes
            return null;
        }
        return list;
    }

}
