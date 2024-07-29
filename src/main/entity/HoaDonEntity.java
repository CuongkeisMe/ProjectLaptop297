/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.entity;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Windows
 */
public class HoaDonEntity {
    private String TenKhachHang;
    private String TenNhanVien;
    private String MaHoaDon;
    private String MaVoucher;
    private String NgayThanhToan;
    private float TongTien;
    private float TienVoucher;
    private float ThanhTien;
    private String PhuongThucThanhToan;
    private String GhiChu;

    public HoaDonEntity() {
    }

    public HoaDonEntity(String TenKhachHang, String TenNhanVien, String MaHoaDon, String MaVoucher,String NgayThanhToan, float TongTien, float TienVoucher, float ThanhTien, String PhuongThucThanhToan, String GhiChu) {
        this.TenKhachHang = TenKhachHang;
        this.TenNhanVien = TenNhanVien;
        this.MaHoaDon = MaHoaDon;
        this.MaVoucher = MaVoucher;
        this.NgayThanhToan = NgayThanhToan;
        this.TongTien = TongTien;
        this.TienVoucher = TienVoucher;
        this.ThanhTien = ThanhTien;
        this.PhuongThucThanhToan = PhuongThucThanhToan;
        this.GhiChu = GhiChu;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String TenKhachHang) {
        this.TenKhachHang = TenKhachHang;
    }

    public String getTenNhanVien() {
        return TenNhanVien;
    }

    public void setTenNhanVien(String TenNhanVien) {
        this.TenNhanVien = TenNhanVien;
    }

    public String getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(String MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getMaVoucher() {
        return MaVoucher;
    }

    public void setMaVoucher(String MaVoucher) {
        this.MaVoucher = MaVoucher;
    }

    public String getNgayThanhToan() {
        return NgayThanhToan;
    }

    public void setNgayThanhToan(String NgayThanhToan) {
        this.NgayThanhToan = NgayThanhToan;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float TongTien) {
        this.TongTien = TongTien;
    }

    public float getTienVoucher() {
        return TienVoucher;
    }

    public void setTienVoucher(float TienVoucher) {
        this.TienVoucher = TienVoucher;
    }

    public float getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(float ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public String getPhuongThucThanhToan() {
        return PhuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String PhuongThucThanhToan) {
        this.PhuongThucThanhToan = PhuongThucThanhToan;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    public Object[] toTable(){
      return new  Object[]{
        MaHoaDon,TenKhachHang,MaVoucher,TenNhanVien,NgayThanhToan,TongTien,TienVoucher,ThanhTien,PhuongThucThanhToan,GhiChu
        };  
    }
    public Object[] loadCboPt(){
        return new Object[]{
          PhuongThucThanhToan  
        };
    }
   public List<String> cboPt() {
        return Collections.singletonList(PhuongThucThanhToan);
    }
    
}
