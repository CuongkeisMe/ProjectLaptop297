package main.entity;

public class ToanCuc {

    private static int vaiTro = -1;
    private static int id; // Biến toàn cục để lưu ID tài khoản
    private static int idNhanVien; // Biến toàn cục để lưu ID nhân viên
    private static String maNhanVien; // Biến toàn cục để lưu mã nhân viên
    private static String tenNhanVien; // Biến toàn cục để lưu tên nhân viên

    // Phương thức lấy vai trò
    public static int getVaiTro() {
        return vaiTro;
    }

    // Phương thức lưu vai trò
    public static void setVaiTro(int vaiTro) {
        ToanCuc.vaiTro = vaiTro;
    }

    // Phương thức lấy ID tài khoản
    public static int getId() {
        return id;
    }

    // Phương thức lưu ID tài khoản
    public static void setId(int id) {
        ToanCuc.id = id;
    }

    // Phương thức lấy ID nhân viên
    public static int getIdNhanVien() {
        return idNhanVien;
    }

    // Phương thức lưu ID nhân viên
    public static void setIdNhanVien(int idNhanVien) {
        ToanCuc.idNhanVien = idNhanVien;
    }

    // Phương thức lấy mã nhân viên
    public static String getMaNhanVien() {
        return maNhanVien;
    }

    // Phương thức lưu mã nhân viên
    public static void setMaNhanVien(String maNhanVien) {
        ToanCuc.maNhanVien = maNhanVien;
    }

    // Phương thức lấy tên nhân viên
    public static String getTenNhanVien() {
        return tenNhanVien;
    }

    // Phương thức lưu tên nhân viên
    public static void setTenNhanVien(String tenNhanVien) {
        ToanCuc.tenNhanVien = tenNhanVien;
    }
}

