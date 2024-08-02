package main.entity;

import lombok.Builder;

@Builder

public class TaiKhoanEtity {

    private Integer id;
    private Integer idNhanVien;
    private String userName;
    private String Pass;
    private int vaiTro;
    private Boolean TrangThai;
    private String maNhanVien;
    private String tenNhanVien;

    public TaiKhoanEtity() {
    }

    public TaiKhoanEtity(Integer id, Integer idNhanVien, String userName, String Pass, int vaiTro, Boolean TrangThai, String maNhanVien, String tenNhanVien) {
        this.id = id;
        this.idNhanVien = idNhanVien;
        this.userName = userName;
        this.Pass = Pass;
        this.vaiTro = vaiTro;
        this.TrangThai = TrangThai;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public int getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(int vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }
}
