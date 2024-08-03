package main.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import main.config.DBConnect;
import main.entity.KhachHang;
import main.entity.NhanVien;

import main.view.chucnang.TaiKhoan;

public class TaiKhoanRepository {

    public ArrayList<main.entity.TaiKhoanEtity> getAll() {
        ArrayList<main.entity.TaiKhoanEtity> list = new ArrayList<>();
        String sql = """
                        SELECT 
                         tk.id_TaiKhoan, 
                         tk.UserName, 
                         tk.Pass, 
                         vt.LoaiVaiTro, 
                         tk.TrangThai, 
                         nv.id_NhanVien,  
                         nv.MaNhanVien,
                         nv.HoTen
                     FROM dbo.TaiKhoan tk
                     INNER JOIN dbo.VaiTro vt ON tk.id_VaiTro = vt.id_VaiTro
                     INNER JOIN dbo.NhanVien nv ON tk.id_NhanVien = nv.id_NhanVien
                     WHERE tk.TrangThai = 1;
   
               """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                main.entity.TaiKhoanEtity tk = main.entity.TaiKhoanEtity.builder()
                        .id(rs.getInt("id_TaiKhoan"))
                        .idNhanVien(rs.getInt("id_NhanVien"))
                        .userName(rs.getString("UserName"))
                        .Pass(rs.getString("Pass"))
                        .vaiTro(rs.getInt("LoaiVaiTro")) // ánh xạ với LoaiVaiTro từ bảng VaiTro
                        .TrangThai(rs.getBoolean("TrangThai"))
                        .maNhanVien(rs.getString("MaNhanVien"))
                        .tenNhanVien(rs.getString("HoTen"))
                        .build();
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//    public ArrayList<VaiTro> getAllvt() {
//        ArrayList<VaiTro> list = new ArrayList<>();
//        String sql = """
//                     SELECT [id_VaiTro]
//                                        ,[LoaiVaiTro]
//                                    FROM [dbo].[VaiTro]
//                           
//                     
//               """;
//        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                VaiTro vt = VaiTro.builder()
//                        .idVaiTro(rs.getInt(1))
//                        .vaiTro(rs.getBoolean(2))
//                        .build();
//                list.add(vt);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
public ArrayList<main.entity.TaiKhoanEtity> getAlltk() {
        ArrayList<main.entity.TaiKhoanEtity> list = new ArrayList<>();
        String sql = """
                     SELECT dbo.TaiKhoan.id_TaiKhoan, dbo.TaiKhoan.id_Nhanvien, dbo.TaiKhoan.UserName, dbo.TaiKhoan.Pass, dbo.VaiTro.LoaiVaiTro, dbo.TaiKhoan.TrangThai
                     FROM   dbo.TaiKhoan INNER JOIN
                                  dbo.VaiTro ON dbo.TaiKhoan.id_VaiTro = dbo.VaiTro.id_VaiTro
                           where dbo.TaiKhoan.TrangThai=0
               """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                main.entity.TaiKhoanEtity tk = main.entity.TaiKhoanEtity.builder()
                        .id(rs.getInt(1))
                        .idNhanVien(rs.getInt(2))
                        .userName(rs.getString(3))
                        .Pass(rs.getString(4))
                        .vaiTro(rs.getInt(5))
                        .TrangThai(rs.getBoolean(6))
                        .build();
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean addtk(main.entity.TaiKhoanEtity tk) {
        String sql = """
                        BEGIN TRANSACTION;
                             
                             
                             INSERT INTO [dbo].[VaiTro] ([LoaiVaiTro])
                             VALUES (?);
                             
                             
                             DECLARE @NewVaiTroID INT;
                             SET @NewVaiTroID = SCOPE_IDENTITY();
                             
                             
                             INSERT INTO [dbo].[TaiKhoan] ( [UserName], [Pass], [id_VaiTro], [TrangThai])
                             VALUES ( ?, ?, @NewVaiTroID, 1);
                             
                             COMMIT TRANSACTION;
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tk.getVaiTro());

            ps.setObject(2, tk.getUserName());
            ps.setObject(3, tk.getPass());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public Boolean deletetk(Integer id) {
        String sql = """
                     UPDATE [dbo].[TaiKhoan]
                        SET [TrangThai] = 0
                      WHERE id_TaiKhoan=?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public Boolean updatetk(main.entity.TaiKhoanEtity tk, Integer id) {
        String sql = """
                     UPDATE [dbo].[TaiKhoan]
                        SET 
                           [id_VaiTro] = ?
                           ,[UserName] = ?
                           ,[Pass] = ?
                           ,[TrangThai] = ?
                      WHERE id_TaiKhoan=?
                     """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tk.getVaiTro());
            ps.setObject(2, tk.getUserName());
            ps.setObject(3, tk.getPass());
            ps.setObject(4, tk.getTrangThai());
            ps.setObject(5, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public ArrayList<main.entity.TaiKhoanEtity> Timtk(String UserName, String password, Boolean vaiTro, Boolean trangThai) {
        ArrayList<main.entity.TaiKhoanEtity> list = new ArrayList<>();
        String sql = """
                     SELECT dbo.TaiKhoan.id_TaiKhoan, dbo.TaiKhoan.id_Nhanvien, dbo.TaiKhoan.UserName, dbo.TaiKhoan.Pass, dbo.VaiTro.LoaiVaiTro, dbo.TaiKhoan.TrangThai
                                                             FROM   dbo.TaiKhoan INNER JOIN
                                                                          dbo.VaiTro ON dbo.TaiKhoan.id_VaiTro = dbo.VaiTro.id_VaiTro
                                                                   where dbo.TaiKhoan.TrangThai=0
                                                                                        AND dbo.TaiKhoan.UserName LIKE ?
                                                                                         AND dbo.TaiKhoan.Pass LIKE ?
                                                                                         AND dbo.VaiTro.LoaiVaiTro LIKE ?
                                                                                         AND dbo.TaiKhoan.TrangThai LIKE ?
                                        """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            ps.setObject(1, UserName);
            ps.setObject(2, password);
            ps.setObject(3, vaiTro);
            ps.setObject(4, trangThai);

            while (rs.next()) {
                main.entity.TaiKhoanEtity tk = main.entity.TaiKhoanEtity.builder()
                        .id(rs.getInt(1))
                        .idNhanVien(rs.getInt(2))
                        .userName(rs.getString(3))
                        .Pass(rs.getString(4))
                        .vaiTro(rs.getInt(5))
                        .TrangThai(rs.getBoolean(6))
                        .build();
list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Boolean convertVaiTroToBoolean(String vaiTroString) {
        // Thay đổi theo cách bạn phân loại vai trò
        if ("Admin".equalsIgnoreCase(vaiTroString)) {
            return true;
        } else if ("Nhân Viên".equalsIgnoreCase(vaiTroString)) {
            return false;
        } else {
            return null; // Hoặc giá trị mặc định khác
        }
    }

}
