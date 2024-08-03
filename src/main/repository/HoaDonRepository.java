package main.repository;

import java.util.ArrayList;
import main.entity.HoaDonEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import main.config.DBConnect;
import main.entity.HoaDonTro;
import main.entity.ToanCuc;

/**
 *
 * @author Windows
 */
public class HoaDonRepository {

    public ArrayList<HoaDonEntity> getAll() {
        ArrayList<HoaDonEntity> list = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            String sql = "	SELECT \n"
                    + "    HoaDon.MaHoaDon as 'MaHoaDon',\n"
                    + "    KhachHang.HoTen as 'kh',\n"
                    + "    Voucher.MaVoucher as 'MaVoucher',\n"
                    + "    NhanVien.HoTen as 'nv',\n"
                    + "    FORMAT(HoaDon.NgayThanhToan, 'dd-MM-yyyy') as 'NgayThanhToan',\n"
                    + "    HoaDon.TongTien as 'TongTien',\n"
                    + "    HoaDon.TienVoucher as 'TienVoucher',\n"
                    + "    HoaDon.ThanhTien as 'ThanhTien',\n"
                    + "    HoaDon.PhuongThucThanhToan as 'PhuongThuc',\n"
                    + "    HoaDon.GhiChu as 'GhiChu'\n"
                    + "FROM HoaDon\n"
                    + "JOIN KhachHang ON KhachHang.id_KhachHang = HoaDon.id_KhachHang\n"
                    + "JOIN Voucher ON Voucher.id_Voucher = HoaDon.id_Voucher\n"
                    + "JOIN NhanVien ON NhanVien.id_NhanVien = HoaDon.id_NhanVien\n"
                    + "WHERE HoaDon.TrangThai = 1;";
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
                     select hd.id_HoaDon, MaHoaDon, NgayThanhToan, nv.MaNhanVien, hd.TrangThai
                     from HoaDon hd
                     left join NhanVien nv on hd.id_NhanVien = nv.id_NhanVien
                     where hd.TrangThai = 0
                     """;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonTro hd = new HoaDonTro().builder()
                        .idHoaDon(rs.getInt(1)) // Assuming id_HoaDon is an integer
                        .maHoaDon(rs.getString(2))
                        .ngayTao(rs.getDate(3))
                        .maNhanVien(rs.getString(4))
                        .tinhTrang(rs.getInt(5))
                        .build();

                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public Boolean themhoatro() {
        int ma = ToanCuc.getIdNhanVien();
        System.out.println("Mã nhân viên đang sử dụng: " + ma);
        String sql = """
                     INSERT INTO [dbo].[HoaDon]
                               (                                                                                             
                                 [NgayThanhToan]
                                ,[id_NhanVien]
                                ,[TongTien]
                                ,[TienVoucher]
                                ,[ThanhTien]                     
                                ,[PhuongThucThanhToan]
                                ,[GhiChu]
                                ,[TrangThai])
                         values (?,?,0,0,0,'','',0)
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, new Date());
            ps.setObject(2, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    
}
