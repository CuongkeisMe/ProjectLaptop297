/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
/**
 *
 * @author Admin
 */
public class ThongKe {

    private String maSP;
    private String tenSP;
    private float giaSP;
    private int tongSP;
    private int daBan;
    private float doanhThu;
    private String maKH;
    private String tenKH;
    private int soLuongMH;
    private float tongTienTT;
    private String maHD;
    private String maNV;
    private String tenNV;
    private Date ngayTT;
    private float tongTien;

}
