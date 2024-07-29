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

    public ArrayList<main.entity.TaiKhoan> getAll() {
        ArrayList<main.entity.TaiKhoan> list = new ArrayList<>();
        String sql = """
                     SELECT dbo.TaiKhoan.id_TaiKhoan, dbo.TaiKhoan.id_Nhanvien, dbo.TaiKhoan.UserName, dbo.TaiKhoan.Pass, dbo.VaiTro.LoaiVaiTro, dbo.TaiKhoan.TrangThai
                     FROM   dbo.TaiKhoan INNER JOIN
                                  dbo.VaiTro ON dbo.TaiKhoan.id_VaiTro = dbo.VaiTro.id_VaiTro
                           where dbo.TaiKhoan.TrangThai=1
                     
               """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                main.entity.TaiKhoan tk = main.entity.TaiKhoan.builder()
                        .id(rs.getInt(1))
                        .idNhanVien(rs.getInt(2))
                        .userName(rs.getString(3))
                        .Pass(rs.getString(4))
                        .vaiTro(rs.getBoolean(5))
                        .TrangThai(rs.getBoolean(6))
                        .build();
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<main.entity.TaiKhoan> getAlltk() {
        ArrayList<main.entity.TaiKhoan> list = new ArrayList<>();
        String sql = """
                     SELECT dbo.TaiKhoan.id_TaiKhoan, dbo.TaiKhoan.id_Nhanvien, dbo.TaiKhoan.UserName, dbo.TaiKhoan.Pass, dbo.VaiTro.LoaiVaiTro, dbo.TaiKhoan.TrangThai
                     FROM   dbo.TaiKhoan INNER JOIN
                                  dbo.VaiTro ON dbo.TaiKhoan.id_VaiTro = dbo.VaiTro.id_VaiTro
                           where dbo.TaiKhoan.TrangThai=0
               """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                main.entity.TaiKhoan tk = main.entity.TaiKhoan.builder()
                        .id(rs.getInt(1))
                        .idNhanVien(rs.getInt(2))
                        .userName(rs.getString(3))
                        .Pass(rs.getString(4))
                        .vaiTro(rs.getBoolean(5))
                        .TrangThai(rs.getBoolean(6))
                        .build();
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Boolean addtk(main.entity.TaiKhoan tk) {
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

    public Boolean updatetk(main.entity.TaiKhoan tk, Integer id) {
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

    public ArrayList<main.entity.TaiKhoan> Timtk(String UserName, String password, Boolean vaiTro, Boolean trangThai) {
        ArrayList<main.entity.TaiKhoan> list = new ArrayList<>();
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
                main.entity.TaiKhoan tk = main.entity.TaiKhoan.builder()
                        .id(rs.getInt(1))
                        .idNhanVien(rs.getInt(2))
                        .userName(rs.getString(3))
                        .Pass(rs.getString(4))
                        .vaiTro(rs.getBoolean(5))
                        .TrangThai(rs.getBoolean(6))
                        .build();
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
